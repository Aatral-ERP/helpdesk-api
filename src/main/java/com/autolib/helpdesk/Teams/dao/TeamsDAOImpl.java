package com.autolib.helpdesk.Teams.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.PushNotification;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Teams.model.TeamEmailSetting;
import com.autolib.helpdesk.Teams.model.TeamMembers;
import com.autolib.helpdesk.Teams.model.TeamPushNotifySetting;
import com.autolib.helpdesk.Teams.model.TeamRequest;
import com.autolib.helpdesk.Teams.model.TeamSetting;
import com.autolib.helpdesk.Teams.model.Teams;
import com.autolib.helpdesk.Teams.repository.TeamEmailSettingRepository;
import com.autolib.helpdesk.Teams.repository.TeamMembersRepository;
import com.autolib.helpdesk.Teams.repository.TeamPushNotifySettingRepository;
import com.autolib.helpdesk.Teams.repository.TeamSettingsRepository;
import com.autolib.helpdesk.Teams.repository.TeamsRepository;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.URLutil;
import com.autolib.helpdesk.common.Util;

@Repository
public class TeamsDAOImpl implements TeamsDAO {

	@Autowired
	private TeamsRepository teamRepo;

	@Autowired
	private TeamMembersRepository teamMemberRepo;

	@Autowired
	private TeamSettingsRepository teamSettingRepo;

	@Autowired
	private TeamEmailSettingRepository emailSettingRepo;

	@Autowired
	private TeamPushNotifySettingRepository pushNotifySettingRepo;

	@Autowired
	private AgentRepository agentRepo;

	@Autowired
	EmailSender emailSender;
	
	@Autowired
	PushNotification pushNotify;

	@Value("${al.agent.web.url}")
	private String agentWebURL;

	@Transactional
	@Override
	public Map<String, Object> saveTeam(TeamRequest teamReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			if (teamReq.getTeams().getId() > 0) {
				teamReq.setTeams(teamRepo.save(teamReq.getTeams()));
				resp.putAll(Util.SuccessResponse());
			} else {

				Teams teamTemp = teamRepo.findByName(teamReq.getTeams().getName());

				if (teamTemp != null) {
					resp.putAll(Util.invalidMessage("Team Name Already Exist"));
				} else if (teamReq.getTeams().getLeadEmail() == null || teamReq.getTeams().getLeadEmail().isEmpty()) {
					resp.putAll(Util.invalidMessage("Lead Cannot be empty"));
				} else {
					// Setting Default workflows

					teamReq.getTeams().setWorkflows("To Do;Done;");

					teamReq.setTeams(teamRepo.save(teamReq.getTeams()));
					teamReq.setAgent(agentRepo.findByEmailId(teamReq.getTeams().getLeadEmail()));

					// Adding Creator as an Administrator

					TeamMembers tempMember = new TeamMembers();
					tempMember.setMemberEmailId(teamReq.getTeams().getLeadEmail());
					tempMember.setMemberRole("Administrator");
					tempMember.setTeamId(teamReq.getTeams().getId());

					teamReq.setTeamMember(tempMember);

					addTeamMembers(teamReq);

					// Save Default Team Settings

					TeamSetting setting = new TeamSetting();
					setting.setTeamId(teamReq.getTeams().getId());
					setting.setLastUpdatedBy("--system-generated--");
					setting.setAdminCanCreateTasks(true);
					setting.setAdminCanModifyOthersTasks(true);
					setting.setMembersCanCreateTasks(true);
					setting.setMembersCanViewOthersTasks(false);
					setting.setMembersCanCommentOthersTasks(false);
					setting.setViewerCanCommentTasks(true);

					teamReq.setTeamSetting(setting);

					saveTeamSettings(teamReq);

					// prepareDefaultEmailSettings

					prepareDefaultEmailSettings(teamReq);

					// prepareDefaultPushNotifySettings

					prepareDefaultPushNotifySettings(teamReq);

					pushNotify.sendTeamsWelcomeNotification(teamReq.getTeams(), tempMember);

					resp.putAll(Util.SuccessResponse());
				}
			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Team", teamReq.getTeams());

		return resp;
	}

	@Override
	public Map<String, Object> getTeam(int id) {
		Map<String, Object> resp = new HashMap<>();
		Teams team = new Teams();
		try {

			team = teamRepo.findById(id);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Team", team);

		return resp;
	}

	@Override
	public Map<String, Object> getAllTeams() {
		Map<String, Object> resp = new HashMap<>();
		List<Teams> teams = new ArrayList<>();
		try {

			teams = teamRepo.findAll();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Teams", teams);

		return resp;
	}

	@Override
	public Map<String, Object> deleteTeam(TeamRequest teamReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			TeamMembers memberTemp = teamMemberRepo.findByTeamIdAndMemberEmailId(teamReq.getTeams().getId(),
					teamReq.getAgent().getEmailId());

			if (memberTemp == null) {
				resp.putAll(Util.invalidMessage("You are not a member of this Team"));
			} else if (memberTemp.getMemberRole().equalsIgnoreCase("administrator")) {
				resp.putAll(Util.invalidMessage("You are not an Administrator of this Team"));
			} else {
				teamRepo.delete(teamReq.getTeams());

				teamMemberRepo.deleteByTeamId(teamReq.getTeams().getId());

				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	public Map<String, Object> getAllMyTeams(Agent agent) {
		Map<String, Object> resp = new HashMap<>();
		List<Teams> teams = new ArrayList<>();

		try {

			teams = teamRepo.getAllMyTeams(agent.getEmailId());
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		resp.put("Teams", teams);
		return resp;
	}

	@Transactional
	@Override
	public Map<String, Object> addTeamMembers(TeamRequest teamReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			teamReq.setTeams(teamRepo.findById(teamReq.getTeamMember().getTeamId()));

			teamReq.setAgent(agentRepo.findByEmailId(teamReq.getTeamMember().getMemberEmailId()));

			if (teamReq.getTeams() == null) {
				resp.putAll(Util.invalidMessage("No such Team found"));
			} else if (teamReq.getTeams().getStatus().equalsIgnoreCase("archived")) {
				resp.putAll(Util.invalidMessage("Team is Archived"));
			} else if (teamReq.getAgent() == null) {
				resp.putAll(Util.invalidMessage("No such Agent found"));
			} else if (!teamReq.getAgent().getWorkingStatus().equalsIgnoreCase("working")) {
				resp.putAll(Util.invalidMessage("Agent is not in Working status"));
			} else if (teamReq.getAgent().isBlocked()) {
				resp.putAll(Util.invalidMessage("Agent is Blocked"));
			} else {

				TeamMembers memberTemp = teamMemberRepo.findByTeamIdAndMemberEmailId(teamReq.getTeams().getId(),
						teamReq.getAgent().getEmailId());

				if (teamReq.getTeamMember().getId() == 0 && memberTemp != null) {
					resp.putAll(Util.invalidMessage("Agent is already a member of this team."));
				} else {

					teamReq.setTeamMember(teamMemberRepo.save(teamReq.getTeamMember()));

					sendTeamMemberWelcomeMail(teamReq);

					pushNotify.sendTeamsWelcomeNotification(teamReq.getTeams(), teamReq.getTeamMember());

					resp.putAll(Util.SuccessResponse());
				}

			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamMember", teamReq.getTeamMember());

		return resp;
	}

	void sendTeamMemberWelcomeMail(TeamRequest req) {
		try {
			EmailModel emailModel = new EmailModel("Tickets");

			emailModel.setMailTo(req.getTeamMember().getMemberEmailId());
			emailModel.setMailSub("Welcome to the team '" + req.getTeams().getName() + "' ");

			StringBuffer sb = new StringBuffer();
			sb.append("Hi, <br><br>");
			sb.append("<h2>Welcome to the team '" + req.getTeams().getName() + "'</h2>");

			sb.append("you have been added as '" + req.getTeamMember().getMemberRole() + "' of the team '"
					+ req.getTeams().getName() + "' <br><br>");

			sb.append("view the team using below link <br>");

			sb.append(" <a target='_blank' href='" + agentWebURL + URLutil.viewTeamURL + req.getTeams().getId()
					+ "'> View Team </a><br><br>");

			sb.append("Thanks");

			emailModel.setMailText(sb.toString());

			emailSender.sendmail(emailModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> deleteTeamMembers(TeamRequest teamReq) {
		Map<String, Object> resp = new HashMap<>();
		try {
			Teams team = teamRepo.findById(teamReq.getTeams().getId());

			TeamMembers memberTemp = teamMemberRepo.findByTeamIdAndMemberEmailId(teamReq.getTeams().getId(),
					teamReq.getTeamMember().getMemberEmailId());

			if (team == null) {
				resp.putAll(Util.invalidMessage("No team found!"));
			} else if (!team.getLeadEmail().equalsIgnoreCase(teamReq.getAgent().getEmailId())) {
				resp.putAll(Util.invalidMessage("You are not Lead of this Team"));
			} else if (memberTemp == null) {
				resp.putAll(Util.invalidMessage("Member not found in this team"));
			} else {
				teamMemberRepo.delete(teamReq.getTeamMember());
				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamMember", teamReq.getTeamMember());

		return resp;
	}

	@Override
	public Map<String, Object> getTeamMembers(int teamId) {
		Map<String, Object> resp = new HashMap<>();
		List<TeamMembers> teamMembers = new ArrayList<>();
		try {

			teamMembers = teamMemberRepo.findByTeamId(teamId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamMembers", teamMembers);

		return resp;
	}

	@Override
	public Map<String, Object> saveTeamSettings(TeamRequest teamReq) {
		System.out.println("Inside saveTeamSettings");
		Map<String, Object> resp = new HashMap<>();
		try {

			teamReq.setTeams(teamRepo.findById(teamReq.getTeams().getId()));
			if (teamReq.getTeams() == null) {
				System.out.println("Inside 1" + teamReq.getTeams());
				resp.putAll(Util.invalidMessage("No such Team found"));
			} else if (teamReq.getTeams().getStatus().equalsIgnoreCase("archived")) {
				System.out.println("Inside 2" + teamReq.getTeams());
				resp.putAll(Util.invalidMessage("Team is Archived"));
			} else if (!teamReq.getTeams().getLeadEmail().equalsIgnoreCase(teamReq.getAgent().getEmailId())) {
				System.out.println("Inside 3" + teamReq.getTeams());
				resp.putAll(
						Util.invalidMessage("You are not Lead of this team '" + teamReq.getTeams().getName() + "'"));
			} else {
				System.out.println("Inside else" + teamReq.getTeams());
				teamReq.setTeamSetting(teamSettingRepo.save(teamReq.getTeamSetting()));
				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamSetting", teamReq.getTeamSetting());

		return resp;
	}

	@Override
	public Map<String, Object> getTeamSettings(int teamId) {
		Map<String, Object> resp = new HashMap<>();
		TeamSetting setting = new TeamSetting();
		try {

			setting = teamSettingRepo.findByTeamId(teamId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamSetting", setting);

		return resp;
	}

	void prepareDefaultEmailSettings(TeamRequest req) {

		try {
			List<TeamEmailSetting> teamEmailSettings = new ArrayList<>();
			TeamEmailSetting taskCreated = new TeamEmailSetting();
			TeamEmailSetting taskUpdated = new TeamEmailSetting();
			TeamEmailSetting taskAssigned = new TeamEmailSetting();
			TeamEmailSetting taskDone = new TeamEmailSetting();
			TeamEmailSetting taskDeleted = new TeamEmailSetting();
			TeamEmailSetting taskMoved = new TeamEmailSetting();
			TeamEmailSetting taskCommented = new TeamEmailSetting();
//			TeamEmailSetting taskCommentEdited = new TeamEmailSetting();

			// setting team Id
			taskCreated.setTeamId(req.getTeams().getId());
			taskUpdated.setTeamId(req.getTeams().getId());
			taskAssigned.setTeamId(req.getTeams().getId());
			taskDone.setTeamId(req.getTeams().getId());
			taskDeleted.setTeamId(req.getTeams().getId());
			taskMoved.setTeamId(req.getTeams().getId());
			taskCommented.setTeamId(req.getTeams().getId());
//			taskCommentEdited.setTeamId(req.getTeams().getId());

			// setting Action
			taskCreated.setAction("Task Created");
			taskUpdated.setAction("Task Updated");
			taskAssigned.setAction("Task Assigned");
			taskDone.setAction("Task Done");
			taskDeleted.setAction("Task Deleted");
			taskMoved.setAction("Task Moved");
			taskCommented.setAction("Task Commented");
//			taskCommentEdited.setAction("Task Comment Edited");

			// setting lead
			taskCreated.setLead(false);
			taskUpdated.setLead(false);
			taskAssigned.setLead(false);
			taskDone.setLead(true);
			taskDeleted.setLead(false);
			taskMoved.setLead(false);
			taskCommented.setLead(false);
//			taskCommentEdited.setLead(false);

			// setting reporter
			taskCreated.setReporter(true);
			taskUpdated.setReporter(true);
			taskAssigned.setReporter(true);
			taskDone.setReporter(true);
			taskDeleted.setReporter(true);
			taskMoved.setReporter(true);
			taskCommented.setReporter(true);
//			taskCommentEdited.setReporter(true);

			// setting Assignee
			taskCreated.setAssignee(true);
			taskUpdated.setAssignee(true);
			taskAssigned.setAssignee(true);
			taskDone.setAssignee(true);
			taskDeleted.setAssignee(true);
			taskMoved.setAssignee(true);
			taskCommented.setAssignee(true);
//			taskCommentEdited.setAssignee(true);

			// setting Intsitute
			taskCreated.setInstitute(true);
			taskUpdated.setInstitute(false);
			taskAssigned.setInstitute(true);
			taskDone.setInstitute(false);
			taskDeleted.setInstitute(true);
			taskMoved.setInstitute(true);
			taskCommented.setInstitute(false);
//			taskCommentEdited.setInstitute(false);

			// setting Watchers
			taskCreated.setWatchers(true);
			taskUpdated.setWatchers(true);
			taskAssigned.setWatchers(true);
			taskDone.setWatchers(true);
			taskDeleted.setWatchers(true);
			taskMoved.setWatchers(true);
			taskCommented.setWatchers(true);
//			taskCommentEdited.setWatchers(true);

			// setting Viewer
			taskCreated.setViewers(false);
			taskUpdated.setViewers(false);
			taskAssigned.setViewers(false);
			taskDone.setViewers(false);
			taskDeleted.setViewers(false);
			taskMoved.setViewers(false);
			taskCommented.setViewers(false);
//			taskCommentEdited.setViewers(false);

			// setting last updated by
			taskCreated.setLastUpdatedBy("--system-generated--");
			taskUpdated.setLastUpdatedBy("--system-generated--");
			taskAssigned.setLastUpdatedBy("--system-generated--");
			taskDone.setLastUpdatedBy("--system-generated--");
			taskDeleted.setLastUpdatedBy("--system-generated--");
			taskMoved.setLastUpdatedBy("--system-generated--");
			taskCommented.setLastUpdatedBy("--system-generated--");
//			taskCommentEdited.setLastUpdatedBy("--system-generated--");

			teamEmailSettings.add(taskCreated);
			teamEmailSettings.add(taskUpdated);
			teamEmailSettings.add(taskAssigned);
			teamEmailSettings.add(taskMoved);
			teamEmailSettings.add(taskDone);
			teamEmailSettings.add(taskDeleted);
			teamEmailSettings.add(taskCommented);
//			teamEmailSettings.add(taskCommentEdited);

			req.setTeamEmailSettings(teamEmailSettings);

			saveTeamEmailSettings(req);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void prepareDefaultPushNotifySettings(TeamRequest req) {

		try {
			List<TeamPushNotifySetting> pushNotifySettings = new ArrayList<>();
			TeamPushNotifySetting taskCreated = new TeamPushNotifySetting();
			TeamPushNotifySetting taskUpdated = new TeamPushNotifySetting();
			TeamPushNotifySetting taskAssigned = new TeamPushNotifySetting();
			TeamPushNotifySetting taskDone = new TeamPushNotifySetting();
			TeamPushNotifySetting taskDeleted = new TeamPushNotifySetting();
			TeamPushNotifySetting taskMoved = new TeamPushNotifySetting();
			TeamPushNotifySetting taskCommented = new TeamPushNotifySetting();
			TeamPushNotifySetting taskCommentEdited = new TeamPushNotifySetting();

			// setting team Id
			taskCreated.setTeamId(req.getTeams().getId());
			taskUpdated.setTeamId(req.getTeams().getId());
			taskAssigned.setTeamId(req.getTeams().getId());
			taskDone.setTeamId(req.getTeams().getId());
			taskDeleted.setTeamId(req.getTeams().getId());
			taskMoved.setTeamId(req.getTeams().getId());
			taskCommented.setTeamId(req.getTeams().getId());
			taskCommentEdited.setTeamId(req.getTeams().getId());

			// setting Action
			taskCreated.setAction("Task Created");
			taskUpdated.setAction("Task Updated");
			taskAssigned.setAction("Task Assigned");
			taskDone.setAction("Task Done");
			taskDeleted.setAction("Task Deleted");
			taskMoved.setAction("Task Moved");
			taskCommented.setAction("Task Commented");
			taskCommentEdited.setAction("Task Comment Edited");

			// setting lead
			taskCreated.setLead(false);
			taskUpdated.setLead(false);
			taskAssigned.setLead(false);
			taskDone.setLead(true);
			taskDeleted.setLead(false);
			taskMoved.setLead(false);
			taskCommented.setLead(false);
			taskCommentEdited.setLead(false);

			// setting reporter
			taskCreated.setReporter(true);
			taskUpdated.setReporter(true);
			taskAssigned.setReporter(true);
			taskDone.setReporter(true);
			taskDeleted.setReporter(true);
			taskMoved.setReporter(true);
			taskCommented.setReporter(true);
			taskCommentEdited.setReporter(true);

			// setting Assignee
			taskCreated.setAssignee(true);
			taskUpdated.setAssignee(true);
			taskAssigned.setAssignee(true);
			taskDone.setAssignee(true);
			taskDeleted.setAssignee(true);
			taskMoved.setAssignee(true);
			taskCommented.setAssignee(true);
			taskCommentEdited.setAssignee(true);

			// setting Intsitute
			taskCreated.setInstitute(true);
			taskUpdated.setInstitute(false);
			taskAssigned.setInstitute(true);
			taskDone.setInstitute(false);
			taskDeleted.setInstitute(true);
			taskMoved.setInstitute(true);
			taskCommented.setInstitute(false);
			taskCommentEdited.setInstitute(false);

			// setting Watchers
			taskCreated.setWatchers(true);
			taskUpdated.setWatchers(true);
			taskAssigned.setWatchers(true);
			taskDone.setWatchers(true);
			taskDeleted.setWatchers(true);
			taskMoved.setWatchers(true);
			taskCommented.setWatchers(true);
			taskCommentEdited.setWatchers(true);

			// setting Viewer
			taskCreated.setWatchers(false);
			taskUpdated.setWatchers(false);
			taskAssigned.setWatchers(false);
			taskDone.setWatchers(false);
			taskDeleted.setWatchers(false);
			taskMoved.setWatchers(false);
			taskCommented.setWatchers(false);
			taskCommentEdited.setWatchers(false);

			// setting last updated by
			taskCreated.setLastUpdatedBy("--system-generated--");
			taskUpdated.setLastUpdatedBy("--system-generated--");
			taskAssigned.setLastUpdatedBy("--system-generated--");
			taskDone.setLastUpdatedBy("--system-generated--");
			taskDeleted.setLastUpdatedBy("--system-generated--");
			taskMoved.setLastUpdatedBy("--system-generated--");
			taskCommented.setLastUpdatedBy("--system-generated--");
			taskCommentEdited.setLastUpdatedBy("--system-generated--");

			pushNotifySettings.add(taskCreated);
			pushNotifySettings.add(taskUpdated);
			pushNotifySettings.add(taskAssigned);
			pushNotifySettings.add(taskMoved);
			pushNotifySettings.add(taskDone);
			pushNotifySettings.add(taskDeleted);
			pushNotifySettings.add(taskCommented);
			pushNotifySettings.add(taskCommentEdited);

			req.setTeamPushNotifySettings(pushNotifySettings);

			saveTeamPushNotifySettings(req);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Map<String, Object> saveTeamEmailSettings(TeamRequest teamReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			teamReq.setTeamEmailSettings(emailSettingRepo.saveAll(teamReq.getTeamEmailSettings()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamEmailSettings", teamReq.getTeamEmailSettings());
		return resp;
	}

	@Override
	public Map<String, Object> getTeamEmailSettings(int teamId) {
		Map<String, Object> resp = new HashMap<>();
		List<TeamEmailSetting> teamEmailSettings = new ArrayList<>();
		try {

			teamEmailSettings = emailSettingRepo.findByTeamId(teamId);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamEmailSettings", teamEmailSettings);
		return resp;
	}

	@Override
	public Map<String, Object> saveTeamPushNotifySettings(TeamRequest teamReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			teamReq.setTeamPushNotifySettings(pushNotifySettingRepo.saveAll(teamReq.getTeamPushNotifySettings()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamPushNotifySettings", teamReq.getTeamPushNotifySettings());
		return resp;
	}

	@Override
	public Map<String, Object> getTeamTeamPushNotifySettings(int teamId) {
		Map<String, Object> resp = new HashMap<>();
		List<TeamPushNotifySetting> pushNotifySettings = new ArrayList<>();
		try {

			pushNotifySettings = pushNotifySettingRepo.findByTeamId(teamId);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("TeamPushNotifySettings", pushNotifySettings);
		return resp;
	}

}
