package com.autolib.helpdesk.Teams.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Agents.entity.PushNotification;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.Teams.model.TeamEmailSetting;
import com.autolib.helpdesk.Teams.model.TeamMembers;
import com.autolib.helpdesk.Teams.model.TeamPushNotifySetting;
import com.autolib.helpdesk.Teams.model.Teams;
import com.autolib.helpdesk.Teams.model.Tasks.Task;
import com.autolib.helpdesk.Teams.model.Tasks.TaskComments;
import com.autolib.helpdesk.Teams.model.Tasks.TaskHistory;
import com.autolib.helpdesk.Teams.model.Tasks.TaskRequest;
import com.autolib.helpdesk.Teams.model.Tasks.TaskSearchRequest;
import com.autolib.helpdesk.Teams.repository.TeamEmailSettingRepository;
import com.autolib.helpdesk.Teams.repository.TeamMembersRepository;
import com.autolib.helpdesk.Teams.repository.TeamPushNotifySettingRepository;
import com.autolib.helpdesk.Teams.repository.TeamsRepository;
import com.autolib.helpdesk.Teams.repository.Tasks.TaskCommentRepository;
import com.autolib.helpdesk.Teams.repository.Tasks.TaskHistoryRepository;
import com.autolib.helpdesk.Teams.repository.Tasks.TaskRepository;
import com.autolib.helpdesk.common.DirectoryUtil;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.URLutil;
import com.autolib.helpdesk.common.Util;

@Repository
public class TaskDAOImpl implements TaskDAO {

	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private TeamsRepository teamRepo;

	@Autowired
	private TeamMembersRepository memberRepo;

	@Autowired
	private InstituteRepository instRepo;

	@Autowired
	private TaskHistoryRepository historyRepo;

	@Autowired
	private TaskCommentRepository commentRepo;

	@Autowired
	private TeamEmailSettingRepository emailSettingRepo;

	@Autowired
	private TeamPushNotifySettingRepository pushNotifyRepo;

	@Autowired
	PushNotification pushNotify;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private EntityManager em;

	@Value("${al.ticket.content-path}")
	private String contentPath;
	@Value("${al.agent.web.url}")
	private String agentWebURL;

	@Transactional
	@Override
	public Map<String, Object> createTask(TaskRequest taskReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			taskReq.setTask(taskRepo.save(taskReq.getTask()));

			TaskHistory history = new TaskHistory();
			history.setTaskId(taskReq.getTask().getTaskId());
			history.setField("Created Task");
			history.setHistoryFrom("");
			history.setHistoryTo("Task Id : #" + taskReq.getTask().getTaskId());
			history.setUpdatedBy(taskReq.getTask().getCreatedBy());

			taskReq.setTaskHistory(historyRepo.save(history));

			if (taskReq.getTask().getFiles() != null && taskReq.getTask().getFiles().length() > 0) {

				List<String> filenames = Arrays.asList(taskReq.getTask().getFiles().split(";"));

				filenames.parallelStream().filter(filename -> filename.length() > 0).forEach(filename -> {
					Path source = Paths.get(contentPath + DirectoryUtil.taskRootDirectory + taskReq.getDirectoryName()
							+ "/" + filename);
					Path destination = Paths.get(contentPath + DirectoryUtil.taskRootDirectory
							+ taskReq.getTask().getTaskId() + "/" + filename);

					File directory = destination.toFile();
					System.out.println(directory.getAbsolutePath());
					if (!directory.exists()) {
						directory.mkdirs();
					}
					try {
						Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			}

			sendCreateTaskEmail(taskReq);

			sendCreateTaskPushNotification(taskReq);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Task", taskReq.getTask());
		return resp;
	}

	@Override
	public Map<String, Object> deleteTask(TaskRequest taskReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			taskRepo.delete(taskReq.getTask());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Task", taskReq.getTask());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTeamTask(TaskRequest taskReq) {
		Map<String, Object> resp = new HashMap<>();
		List<Task> tasks = new ArrayList<>();
		try {

			String filterQuery = "";

			System.out.println(taskReq.getTeamMember());

			if (taskReq.getTeamMember().getMemberRole().equalsIgnoreCase("member"))
				filterQuery = filterQuery + " and t.assignee = '" + taskReq.getTeamMember().getMemberEmailId() + "' ";

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Teams.model.Tasks.Task(t.taskId,t.teamId,t.instituteId,t.subject,t.status,t.priority,"
							+ "t.assignee,t.reporter,t.dueDateTime,t.lastupdatedatetime) from Task t where t.status != 'Done' and t.teamId = :teamId "
							+ filterQuery,
					Task.class);
			query.setParameter("teamId", taskReq.getTask().getTeamId());

			tasks.addAll(query.getResultList());

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -7);

			System.out.println(calendar.getTime());

			Query queryDone = em.createQuery(
					"select new com.autolib.helpdesk.Teams.model.Tasks.Task(t.taskId,t.teamId,t.instituteId,t.subject,t.status,t.priority,"
							+ "t.assignee,t.reporter,t.dueDateTime,t.lastupdatedatetime) from Task t where t.status = 'Done' and t.lastupdatedatetime > :startDate"
							+ " and t.teamId = :teamId " + filterQuery,
					Task.class);
			queryDone.setParameter("teamId", taskReq.getTask().getTeamId());
			queryDone.setParameter("startDate", calendar.getTime());

			tasks.addAll(queryDone.getResultList());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Tasks", tasks);
		return resp;
	}

	@Override
	public Map<String, Object> getTask(int taskId) {
		Map<String, Object> resp = new HashMap<>();
		Task task = new Task();
		try {

			task = taskRepo.findByTaskId(taskId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Task", task);
		return resp;
	}

	@Override
	public Map<String, Object> getTaskHistory(int taskId) {
		Map<String, Object> resp = new HashMap<>();
		List<TaskHistory> histroy = new ArrayList<>();
		try {

			histroy = historyRepo.findByTaskId(taskId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("TaskHistory", histroy);
		return resp;
	}

	@Modifying
	@Override
	public Map<String, Object> updateTask(TaskRequest taskReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			if (taskReq.getTaskHistory() != null)
				taskReq.setTaskHistory(historyRepo.save(taskReq.getTaskHistory()));

			taskReq.setTask(taskRepo.save(taskReq.getTask()));

			sendUpdateTaskEmail(taskReq);

			sendUpdateTaskPushNotification(taskReq);

			memberRepo.updateTeamMembersTasksCount(taskReq.getTask().getTeamId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Task", taskReq.getTask());
		resp.put("TaskHistory", taskReq.getTaskHistory());
		return resp;
	}

	@Override
	public Map<String, Object> saveTaskComment(TaskComments comment) {
		Map<String, Object> resp = new HashMap<>();
		try {

			comment = commentRepo.save(comment);

			sendTaskCommentEmail(comment);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("TaskComments", comment);
		return resp;
	}

	@Override
	public Map<String, Object> getAllTaskComments(int taskId) {
		Map<String, Object> resp = new HashMap<>();
		List<TaskComments> comments = new ArrayList<>();
		try {

			comments = commentRepo.findByTaskId(taskId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("TaskComments", comments);
		return resp;
	}

	@Override
	public Map<String, Object> getTaskSearchNeeded(TaskSearchRequest request) {
		Map<String, Object> resp = new HashMap<>();
		List<String> labels = new ArrayList<>();
		List<String> instituteIds = new ArrayList<>();
		Set<String> watchers = new HashSet<>();
		try {

			labels = taskRepo.findDistinctLabelByTeam(request.getTeamId());

			instituteIds = taskRepo.findDistinctInstituteIdsByTeam(request.getTeamId());

			List<String> _watchers = taskRepo.findDistinctWatchersByTeam(request.getTeamId());

			_watchers.forEach(_watcher -> {
				watchers.addAll(Arrays.asList(_watcher.split(";")));
			});
			watchers.remove(null);
			watchers.remove("");

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("labels", labels);
		resp.put("instituteIds", instituteIds);
		resp.put("watchers", watchers);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchTeamTask(TaskSearchRequest request) {
		Map<String, Object> resp = new HashMap<>();
		List<Task> tasks = new ArrayList<>();
		try {

			String filterQuery = "";

			if (request.getSubject() != null && !request.getSubject().isEmpty()) {
				filterQuery = filterQuery + " and t.subject like '" + request.getSubject() + "'";
			}

			if (request.getInstituteId() != null && !request.getInstituteId().isEmpty()
					&& request.getInstituteId() != "0") {
				filterQuery = filterQuery + " and t.instituteId = '" + request.getInstituteId() + "'";
			}

			if (request.getTaskId() > 0) {
				filterQuery = filterQuery + " and t.taskId = '" + request.getTaskId() + "'";
			}

			if (request.getStatus() != null && request.getStatus().size() > 0) {
				String _status = "'0'";
				for (String status : request.getStatus()) {
					_status = _status + ",'" + status + "'";
				}
				filterQuery = filterQuery + " and t.status in (" + _status + ") ";
			}

			if (request.getPriority() != null && request.getPriority().size() > 0) {
				String _priority = "'0'";
				for (String priority : request.getPriority()) {
					_priority = _priority + ",'" + priority + "'";
				}
				filterQuery = filterQuery + " and t.priority in (" + _priority + ") ";
			}

			if (request.getAssignee() != null && request.getAssignee().size() > 0) {
				String _assignee = "'0'";
				for (String assignee : request.getAssignee()) {
					_assignee = _assignee + ",'" + assignee + "'";
				}
				filterQuery = filterQuery + " and t.assignee in (" + _assignee + ") ";
			}

			if (request.getReporter() != null && request.getReporter().size() > 0) {
				String _reporter = "'0'";
				for (String reporter : request.getReporter()) {
					_reporter = _reporter + ",'" + reporter + "'";
				}
				filterQuery = filterQuery + " and t.reporter in (" + _reporter + ") ";
			}

			if (request.getLabels() != null && request.getLabels().size() > 0) {
				String _labels = "'0'";
				for (String labels : request.getLabels()) {
					_labels = _labels + ",'" + labels + "'";
				}
				filterQuery = filterQuery + " and t.label in (" + _labels + ") ";
			}

			if (request.getCreatedBy() != null && request.getCreatedBy().size() > 0) {
				String _created_by = "'0'";
				for (String created_by : request.getCreatedBy()) {
					_created_by = _created_by + ",'" + created_by + "'";
				}
				filterQuery = filterQuery + " and t.createdBy in (" + _created_by + ") ";
			}

			if (request.getLastUpdatedBy() != null && request.getLastUpdatedBy().size() > 0) {
				String _last_updated_by = "'0'";
				for (String last_updated_by : request.getLastUpdatedBy()) {
					_last_updated_by = _last_updated_by + ",'" + last_updated_by + "'";
				}
				filterQuery = filterQuery + " and t.lastUpdatedBy in (" + _last_updated_by + ") ";
			}

			if (request.getDueDateTimeFrom() != null && request.getDueDateTimeTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and t.dueDateTime between '" + sdf.format(request.getDueDateTimeFrom())
						+ "' and '" + sdf.format(request.getDueDateTimeTo()) + " 23:59:59'";
			}

			if (request.getCreateddatetimeFrom() != null && request.getCreateddatetimeTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and t.createddatetime between '"
						+ sdf.format(request.getCreateddatetimeFrom()) + "' and '"
						+ sdf.format(request.getCreateddatetimeTo()) + " 23:59:59'";
			}

			if (request.getLastupdatedatetimeFrom() != null && request.getLastupdatedatetimeTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and t.lastupdatedatetime between '"
						+ sdf.format(request.getLastupdatedatetimeFrom()) + "' and '"
						+ sdf.format(request.getLastupdatedatetimeTo()) + " 23:59:59'";
			}

			Query query = em.createQuery("select t from Task t where 2 > 1 " + filterQuery, Task.class);

			tasks = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Tasks", tasks);
		return resp;
	}

	void sendCreateTaskEmail(TaskRequest request) {
		try {

			TeamEmailSetting emailSetting = emailSettingRepo.findByTeamIdAndAction(request.getTask().getTeamId(),
					"Task Created");

			Teams team = teamRepo.findById(request.getTask().getTeamId());

			Institute inst = instRepo.findByInstituteId(String.valueOf(request.getTask().getInstituteId()));

			EmailModel emailModel = new EmailModel("Tickets");

			Set<String> emailIds = new HashSet<>();

			if (emailSetting.isAssignee() && request.getTask().getAssignee() != null
					&& !request.getTask().getAssignee().isEmpty()) {
				if (Util.validateEmailID(request.getTask().getAssignee())) {
					emailIds.add(request.getTask().getAssignee());
				}
			}

			if (emailSetting.isReporter() && request.getTask().getReporter() != null
					&& !request.getTask().getReporter().isEmpty()) {
				if (Util.validateEmailID(request.getTask().getReporter())) {
					emailIds.add(request.getTask().getReporter());
				}
			}

			if (emailSetting.isLead()) {
				if (Util.validateEmailID(team.getLeadEmail())) {
					emailIds.add(team.getLeadEmail());
				}
			}

			if (emailSetting.isInstitute() && request.getTask().getInstituteId() > 0) {

				if (inst.getEmailId() != null && !inst.getEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getEmailId())) {
						emailIds.add(inst.getEmailId());
					}
				}
				if (inst.getAlternateEmailId() != null && !inst.getAlternateEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getAlternateEmailId())) {
						emailIds.add(inst.getAlternateEmailId());
					}
				}
			}

			if (emailSetting.isWatchers() && request.getTask().getWatchers() != null
					&& request.getTask().getWatchers().length() > 0) {
				List<String> _emails = Arrays.asList(request.getTask().getWatchers().split(";"));
				for (String _email : _emails) {
					if (Util.validateEmailID(_email)) {
						emailIds.add(_email);
					}
				}
			}

			if (emailSetting.isViewers()) {
				List<TeamMembers> teamMembers = memberRepo.findByTeamIdAndMemberRole(request.getTask().getTeamId(),
						"Viewer");
				teamMembers.forEach(memb -> {
					if (Util.validateEmailID(memb.getMemberEmailId())) {
						emailIds.add(memb.getMemberEmailId());
					}
				});
			}

			if (emailIds.size() > 0) {

				String[] mailList = emailIds.stream().toArray(String[]::new);

				emailModel.setMailTo(mailList[mailList.length - 1]);
				emailModel.setMailList(mailList);
				emailModel.setMailSub("New Task Created Id : #" + request.getTask().getTaskId() + " - "
						+ request.getTask().getSubject());

				StringBuffer sb = new StringBuffer();

				sb.append("Hi,<br><br>");
				sb.append("New Task created.");
				sb.append("<h4>" + request.getTask().getSubject() + "</h4>");
				sb.append("<p>" + request.getTask().getDescription() + "</p>");
				sb.append("<table>");

				sb.append("<tr>");
				sb.append("<td>Team</td>");
				sb.append("<td> : </td>");
				sb.append("<td>" + team.getName() + "</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Assignee</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getAssignee() != null)
					sb.append("<td>" + request.getTask().getAssignee() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Report</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getReporter() != null)
					sb.append("<td>" + request.getTask().getReporter() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Institute</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getInstituteId() > 0)
					sb.append("<td>" + inst.getInstituteName() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Status</td>");
				sb.append("<td> : </td>");
				sb.append("<td>" + request.getTask().getStatus() + "</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Priority</td>");
				sb.append("<td> : </td>");
				sb.append("<td>" + request.getTask().getPriority() + "</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Due Date</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getDueDateTime() != null)
					sb.append("<td>" + Util.sdfFormatter(request.getTask().getDueDateTime(), "dd/MM/yyyy hh:mm a")
							+ "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Label/Category</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getLabel() != null)
					sb.append("<td>" + request.getTask().getLabel() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Created By</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getCreatedBy() != null)
					sb.append("<td>" + request.getTask().getCreatedBy() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Attachments</td>");
				sb.append("<td> : </td>");
				if (request.getTask().getFiles() != null)
					sb.append("<td>" + request.getTask().getFiles() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");
				sb.append("<table>");

				sb.append("<br><br>");
				sb.append("View Task using below link<br>");
				sb.append("<a href='" + URLutil.getViewTeamTaskURL(agentWebURL, request.getTask().getTaskId())
						+ "' target='_blank'>" + URLutil.getViewTeamTaskURL(agentWebURL, request.getTask().getTaskId())
						+ "</a>");

				emailModel.setMailText(sb.toString());

				emailSender.sendmail(emailModel);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendCreateTaskPushNotification(TaskRequest request) {
		try {

			TeamPushNotifySetting pushNotifySetting = pushNotifyRepo
					.findByTeamIdAndAction(request.getTask().getTeamId(), "Task Created");

			Teams team = teamRepo.findById(request.getTask().getTeamId());

			Institute inst = instRepo.findByInstituteId(String.valueOf(request.getTask().getInstituteId()));

			Set<String> emailIds = new HashSet<>();

			if (pushNotifySetting.isAssignee() && request.getTask().getAssignee() != null
					&& !request.getTask().getAssignee().isEmpty()) {
				if (Util.validateEmailID(request.getTask().getAssignee())) {
					emailIds.add(request.getTask().getAssignee());
				}
			}

			if (pushNotifySetting.isReporter() && request.getTask().getReporter() != null
					&& !request.getTask().getReporter().isEmpty()) {
				if (Util.validateEmailID(request.getTask().getReporter())) {
					emailIds.add(request.getTask().getReporter());
				}
			}

			if (pushNotifySetting.isLead()) {
				if (Util.validateEmailID(team.getLeadEmail())) {
					emailIds.add(team.getLeadEmail());
				}
			}

			if (pushNotifySetting.isInstitute() && request.getTask().getInstituteId() > 0) {

				if (inst.getEmailId() != null && !inst.getEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getEmailId())) {
						emailIds.add(inst.getEmailId());
					}
				}
				if (inst.getAlternateEmailId() != null && !inst.getAlternateEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getAlternateEmailId())) {
						emailIds.add(inst.getAlternateEmailId());
					}
				}
			}

			if (pushNotifySetting.isWatchers() && request.getTask().getWatchers() != null
					&& request.getTask().getWatchers().length() > 0) {
				List<String> _emails = Arrays.asList(request.getTask().getWatchers().split(";"));
				for (String _email : _emails) {
					if (Util.validateEmailID(_email)) {
						emailIds.add(_email);
					}
				}
			}

			if (pushNotifySetting.isViewers()) {
				List<TeamMembers> teamMembers = memberRepo.findByTeamIdAndMemberRole(request.getTask().getTeamId(),
						"Viewer");
				teamMembers.forEach(memb -> {
					if (Util.validateEmailID(memb.getMemberEmailId())) {
						emailIds.add(memb.getMemberEmailId());
					}
				});
			}

			if (emailIds.size() > 0) {

				pushNotify.sendTicketAssignedPushNotify(request, emailIds);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendUpdateTaskEmail(TaskRequest request) {
		try {

			EmailModel emailModel = new EmailModel("Tickets");

			TeamEmailSetting emailSetting = null;

			if (request.getTaskHistory() != null) {
				if (request.getTaskHistory().getField().equalsIgnoreCase("Added Watcher")) {
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Added Watcher : "
							+ request.getTaskHistory().getHistoryTo());
				} else if (request.getTaskHistory().getField().equalsIgnoreCase("Removed Watcher")) {
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Removed Watcher : "
							+ request.getTaskHistory().getHistoryTo());
				} else if (request.getTaskHistory().getField().equalsIgnoreCase("Changed Priority")) {
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Changed Priority : From "
							+ request.getTaskHistory().getHistoryFrom() + " to "
							+ request.getTaskHistory().getHistoryTo());
				} else if (request.getTaskHistory().getField().equalsIgnoreCase("Changed Status")) {
					if (request.getTaskHistory().getHistoryTo().equalsIgnoreCase("Done")) {
						emailSetting = emailSettingRepo.findByTeamIdAndAction(request.getTask().getTeamId(),
								"Task Done");
						emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Changed Status : From "
								+ request.getTaskHistory().getHistoryFrom() + " to "
								+ request.getTaskHistory().getHistoryTo());
					} else {
						emailSetting = emailSettingRepo.findByTeamIdAndAction(request.getTask().getTeamId(),
								"Task Moved");
						emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Changed Status : From "
								+ request.getTaskHistory().getHistoryFrom() + " to "
								+ request.getTaskHistory().getHistoryTo());
					}

				} else if (request.getTaskHistory().getField().equalsIgnoreCase("Changed Assignee")) {
					emailSetting = emailSettingRepo.findByTeamIdAndAction(request.getTask().getTeamId(),
							"Task Assigned");
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Changed Assignee : From "
							+ request.getTaskHistory().getHistoryFrom() + " to "
							+ request.getTaskHistory().getHistoryTo());
				} else if (request.getTaskHistory().getField().equalsIgnoreCase("Changed Reporter")) {
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Changed Reporter : From "
							+ request.getTaskHistory().getHistoryFrom() + " to "
							+ request.getTaskHistory().getHistoryTo());
				} else if (request.getTaskHistory().getField().equalsIgnoreCase("Changed Due DateTime")) {
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Changed Due DateTime : From "
							+ request.getTaskHistory().getHistoryFrom() + " to "
							+ request.getTaskHistory().getHistoryTo());
				} else {
					emailModel.setMailSub("TaskId #" + request.getTask().getTaskId() + " - Updated recently");
				}

				Teams team = teamRepo.findById(request.getTask().getTeamId());

				Institute inst = instRepo.findByInstituteId(String.valueOf(request.getTask().getInstituteId()));

				if (emailSetting == null)
					emailSetting = emailSettingRepo.findByTeamIdAndAction(request.getTask().getTeamId(),
							"Task Updated");

				Set<String> emailIds = new HashSet<>();

				if (emailSetting.isAssignee() && request.getTask().getAssignee() != null
						&& !request.getTask().getAssignee().isEmpty()) {
					if (Util.validateEmailID(request.getTask().getAssignee())) {
						emailIds.add(request.getTask().getAssignee());
					}
				}

				if (emailSetting.isReporter() && request.getTask().getReporter() != null
						&& !request.getTask().getReporter().isEmpty()) {
					if (Util.validateEmailID(request.getTask().getReporter())) {
						emailIds.add(request.getTask().getReporter());
					}
				}

				if (emailSetting.isLead()) {
					if (Util.validateEmailID(team.getLeadEmail())) {
						emailIds.add(team.getLeadEmail());
					}
				}

				if (emailSetting.isInstitute() && request.getTask().getInstituteId() > 0) {

					if (inst.getEmailId() != null && !inst.getEmailId().isEmpty()) {
						if (Util.validateEmailID(inst.getEmailId())) {
							emailIds.add(inst.getEmailId());
						}
					}
					if (inst.getAlternateEmailId() != null && !inst.getAlternateEmailId().isEmpty()) {
						if (Util.validateEmailID(inst.getAlternateEmailId())) {
							emailIds.add(inst.getAlternateEmailId());
						}
					}
				}

				if (emailSetting.isWatchers() && request.getTask().getWatchers() != null
						&& request.getTask().getWatchers().length() > 0) {
					List<String> _emails = Arrays.asList(request.getTask().getWatchers().split(";"));
					for (String _email : _emails) {
						if (Util.validateEmailID(_email)) {
							emailIds.add(_email);
						}
					}
				}

				if (emailSetting.isViewers()) {
					List<TeamMembers> teamMembers = memberRepo.findByTeamIdAndMemberRole(request.getTask().getTeamId(),
							"Viewer");
					teamMembers.forEach(memb -> {
						if (Util.validateEmailID(memb.getMemberEmailId())) {
							emailIds.add(memb.getMemberEmailId());
						}
					});
				}

				// Removing Updated Member from Email Loop
				emailIds.remove(request.getTaskHistory().getUpdatedBy());

				if (emailIds.size() > 0) {

					String[] mailList = emailIds.stream().toArray(String[]::new);

					emailModel.setMailTo(mailList[mailList.length - 1]);
					emailModel.setMailList(mailList);

					StringBuffer sb = new StringBuffer();

					sb.append("Hi,<br><br>");
					sb.append("Task #" + request.getTask().getTaskId() + " updated.");
					sb.append("<h4>" + request.getTask().getSubject() + "</h4>");
					sb.append("<p>" + request.getTask().getDescription() + "</p>");
					sb.append("<table>");

					sb.append("<tr>");
					sb.append("<td>Team</td>");
					sb.append("<td> : </td>");
					sb.append("<td>" + team.getName() + "</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Assignee</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getAssignee() != null)
						sb.append("<td>" + request.getTask().getAssignee() + "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Report</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getReporter() != null)
						sb.append("<td>" + request.getTask().getReporter() + "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Institute</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getInstituteId() > 0)
						sb.append("<td>" + inst.getInstituteName() + "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Status</td>");
					sb.append("<td> : </td>");
					sb.append("<td>" + request.getTask().getStatus() + "</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Priority</td>");
					sb.append("<td> : </td>");
					sb.append("<td>" + request.getTask().getPriority() + "</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Due Date</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getDueDateTime() != null)
						sb.append("<td>" + Util.sdfFormatter(request.getTask().getDueDateTime(), "dd/MM/yyyy hh:mm a")
								+ "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Label/Category</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getLabel() != null)
						sb.append("<td>" + request.getTask().getLabel() + "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Created By</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getCreatedBy() != null)
						sb.append("<td>" + request.getTask().getCreatedBy() + "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>Attachments</td>");
					sb.append("<td> : </td>");
					if (request.getTask().getFiles() != null)
						sb.append("<td>" + request.getTask().getFiles() + "</td>");
					else
						sb.append("<td> - </td>");
					sb.append("</tr>");
					sb.append("<table>");

					sb.append("<br><br>");
					sb.append("View Task using below link<br>");
					sb.append("<a href='" + URLutil.getViewTeamTaskURL(agentWebURL, request.getTask().getTaskId())
							+ "' target='_blank'>"
							+ URLutil.getViewTeamTaskURL(agentWebURL, request.getTask().getTaskId()) + "</a>");

					emailModel.setMailText(sb.toString());

					emailSender.sendmail(emailModel);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendUpdateTaskPushNotification(TaskRequest request) {
		try {

			TeamPushNotifySetting pushNotifySetting = pushNotifyRepo
					.findByTeamIdAndAction(request.getTask().getTeamId(), "Task Updated");

			Teams team = teamRepo.findById(request.getTask().getTeamId());

			Institute inst = instRepo.findByInstituteId(String.valueOf(request.getTask().getInstituteId()));

			Set<String> emailIds = new HashSet<>();

			if (pushNotifySetting.isAssignee() && request.getTask().getAssignee() != null
					&& !request.getTask().getAssignee().isEmpty()) {
				if (Util.validateEmailID(request.getTask().getAssignee())) {
					emailIds.add(request.getTask().getAssignee());
				}
			}

			if (pushNotifySetting.isReporter() && request.getTask().getReporter() != null
					&& !request.getTask().getReporter().isEmpty()) {
				if (Util.validateEmailID(request.getTask().getReporter())) {
					emailIds.add(request.getTask().getReporter());
				}
			}

			if (pushNotifySetting.isLead()) {
				if (Util.validateEmailID(team.getLeadEmail())) {
					emailIds.add(team.getLeadEmail());
				}
			}

			if (pushNotifySetting.isInstitute() && request.getTask().getInstituteId() > 0) {

				if (inst.getEmailId() != null && !inst.getEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getEmailId())) {
						emailIds.add(inst.getEmailId());
					}
				}
				if (inst.getAlternateEmailId() != null && !inst.getAlternateEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getAlternateEmailId())) {
						emailIds.add(inst.getAlternateEmailId());
					}
				}
			}

			if (pushNotifySetting.isWatchers() && request.getTask().getWatchers() != null
					&& request.getTask().getWatchers().length() > 0) {
				List<String> _emails = Arrays.asList(request.getTask().getWatchers().split(";"));
				for (String _email : _emails) {
					if (Util.validateEmailID(_email)) {
						emailIds.add(_email);
					}
				}
			}

			if (pushNotifySetting.isViewers()) {
				List<TeamMembers> teamMembers = memberRepo.findByTeamIdAndMemberRole(request.getTask().getTeamId(),
						"Viewer");
				teamMembers.forEach(memb -> {
					if (Util.validateEmailID(memb.getMemberEmailId())) {
						emailIds.add(memb.getMemberEmailId());
					}
				});
			}

			if (emailIds.size() > 0) {

				pushNotify.sendUpdateTaskPushNotification(request, emailIds);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendTaskCommentEmail(TaskComments comment) {
		try {

			EmailModel emailModel = new EmailModel("Tickets");
			Task task = taskRepo.findByTaskId(comment.getTaskId());

			Teams team = teamRepo.findById(task.getTeamId());

			TeamEmailSetting emailSetting = emailSettingRepo.findByTeamIdAndAction(task.getTeamId(), "Task Commented");

			Institute inst = instRepo.findByInstituteId(String.valueOf(task.getInstituteId()));

			Set<String> emailIds = new HashSet<>();

			emailModel.setMailSub("TaskId #" + comment.getTaskId() + " - Commented by : " + comment.getCommentBy());

			if (emailSetting.isAssignee() && task.getAssignee() != null && !task.getAssignee().isEmpty()) {
				if (Util.validateEmailID(task.getAssignee())) {
					emailIds.add(task.getAssignee());
				}
			}

			if (emailSetting.isReporter() && task.getReporter() != null && !task.getReporter().isEmpty()) {
				if (Util.validateEmailID(task.getReporter())) {
					emailIds.add(task.getReporter());
				}
			}

			if (emailSetting.isLead()) {
				if (Util.validateEmailID(team.getLeadEmail())) {
					emailIds.add(team.getLeadEmail());
				}
			}

			if (emailSetting.isInstitute() && task.getInstituteId() > 0) {
				if (inst.getEmailId() != null && !inst.getEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getEmailId())) {
						emailIds.add(inst.getEmailId());
					}
				}
				if (inst.getAlternateEmailId() != null && !inst.getAlternateEmailId().isEmpty()) {
					if (Util.validateEmailID(inst.getAlternateEmailId())) {
						emailIds.add(inst.getAlternateEmailId());
					}
				}
			}

			if (emailSetting.isWatchers() && task.getWatchers() != null && task.getWatchers().length() > 0) {
				List<String> _emails = Arrays.asList(task.getWatchers().split(";"));
				for (String _email : _emails) {
					if (Util.validateEmailID(_email)) {
						emailIds.add(_email);
					}
				}
			}

			if (emailSetting.isViewers()) {
				List<TeamMembers> teamMembers = memberRepo.findByTeamIdAndMemberRole(task.getTeamId(), "Viewer");
				teamMembers.forEach(memb -> {
					if (Util.validateEmailID(memb.getMemberEmailId())) {
						emailIds.add(memb.getMemberEmailId());
					}
				});
			}

			if (emailIds.size() > 0) {

				String[] mailList = emailIds.stream().toArray(String[]::new);

				emailModel.setMailTo(mailList[mailList.length - 1]);
				emailModel.setMailList(mailList);

				StringBuffer sb = new StringBuffer();

				sb.append("<h4>" + comment.getComment().replaceAll("\n", "<br>").replaceAll("\r", "<br>") + "</h4>");
				sb.append("<hr>");
				sb.append("<h6>" + task.getSubject() + "</h6>");
				sb.append("<p>" + task.getDescription() + "</p>");
				sb.append("<table>");

				sb.append("<tr>");
				sb.append("<td>Team</td>");
				sb.append("<td> : </td>");
				sb.append("<td>" + team.getName() + "</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Assignee</td>");
				sb.append("<td> : </td>");
				if (task.getAssignee() != null)
					sb.append("<td>" + task.getAssignee() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Report</td>");
				sb.append("<td> : </td>");
				if (task.getReporter() != null)
					sb.append("<td>" + task.getReporter() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Institute</td>");
				sb.append("<td> : </td>");
				if (task.getInstituteId() > 0)
					sb.append("<td>" + inst.getInstituteName() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Status</td>");
				sb.append("<td> : </td>");
				sb.append("<td>" + task.getStatus() + "</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Priority</td>");
				sb.append("<td> : </td>");
				sb.append("<td>" + task.getPriority() + "</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Due Date</td>");
				sb.append("<td> : </td>");
				if (task.getDueDateTime() != null)
					sb.append("<td>" + Util.sdfFormatter(task.getDueDateTime(), "dd/MM/yyyy hh:mm a") + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Label/Category</td>");
				sb.append("<td> : </td>");
				if (task.getLabel() != null)
					sb.append("<td>" + task.getLabel() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Created By</td>");
				sb.append("<td> : </td>");
				if (task.getCreatedBy() != null)
					sb.append("<td>" + task.getCreatedBy() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>Attachments</td>");
				sb.append("<td> : </td>");
				if (task.getFiles() != null)
					sb.append("<td>" + task.getFiles() + "</td>");
				else
					sb.append("<td> - </td>");
				sb.append("</tr>");
				sb.append("<table>");

				sb.append("<br><br>");
				sb.append("View Task using below link<br>");
				sb.append("<a href='" + URLutil.getViewTeamTaskURL(agentWebURL, task.getTaskId()) + "' target='_blank'>"
						+ URLutil.getViewTeamTaskURL(agentWebURL, task.getTaskId()) + "</a>");

				emailModel.setMailText(sb.toString());

				emailSender.sendmail(emailModel);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMyCalendarTasksEvents(TaskSearchRequest request) {
		Map<String, Object> resp = new HashMap<>();
		List<Task> tasks = new ArrayList<>();
		try {

			String filterQuery = "";

			if (request.getCreateddatetimeFrom() != null && request.getCreateddatetimeTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and ( t.dueDateTime between '"
						+ sdf.format(request.getCreateddatetimeFrom()) + "' and '"
						+ sdf.format(request.getCreateddatetimeTo()) + " 23:59:59' or t.createddatetime between '"
						+ sdf.format(request.getCreateddatetimeFrom()) + "' and '"
						+ sdf.format(request.getCreateddatetimeTo()) + " 23:59:59' )";
			}

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Teams.model.Tasks.Task(t.taskId,t.teamId,t.subject,t.status,"
							+ "t.assignee,t.reporter,t.dueDateTime,t.createddatetime) from Task t where t.assignee = :assignee "
							+ filterQuery,
					Task.class);
			query.setParameter("assignee", request.getAssignee().get(0));

			tasks = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Tasks", tasks);
		return resp;
	}
}
