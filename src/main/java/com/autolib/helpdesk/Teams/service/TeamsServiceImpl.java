package com.autolib.helpdesk.Teams.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Teams.dao.TeamsDAO;
import com.autolib.helpdesk.Teams.model.TeamRequest;

@Service
public class TeamsServiceImpl implements TeamsService {

	@Autowired
	private TeamsDAO teamsDAO;

	@Override
	public Map<String, Object> saveTeam(TeamRequest teamReq) {
		return teamsDAO.saveTeam(teamReq);
	}

	@Override
	public Map<String, Object> getTeam(int id) {
		return teamsDAO.getTeam(id);
	}

	@Override
	public Map<String, Object> getAllTeams() {
		return teamsDAO.getAllTeams();
	}

	@Override
	public Map<String, Object> deleteTeam(TeamRequest teamReq) {
		return teamsDAO.deleteTeam(teamReq);
	}

	@Override
	public Map<String, Object> getAllMyTeams(Agent agent) {
		return teamsDAO.getAllMyTeams(agent);
	}

	@Override
	public Map<String, Object> addTeamMembers(TeamRequest teamReq) {
		return teamsDAO.addTeamMembers(teamReq);
	}

	@Override
	public Map<String, Object> deleteTeamMembers(TeamRequest teamReq) {
		return teamsDAO.deleteTeamMembers(teamReq);
	}

	@Override
	public Map<String, Object> getTeamMembers(int teamId) {
		return teamsDAO.getTeamMembers(teamId);
	}

	@Override
	public Map<String, Object> saveTeamSettings(TeamRequest teamReq) {
		return teamsDAO.saveTeamSettings(teamReq);
	}

	@Override
	public Map<String, Object> getTeamSettings(int teamId) {
		return teamsDAO.getTeamSettings(teamId);
	}

	@Override
	public Map<String, Object> saveTeamEmailSettings(TeamRequest teamReq) {
		return teamsDAO.saveTeamEmailSettings(teamReq);
	}

	@Override
	public Map<String, Object> getTeamEmailSettings(int teamId) {
		return teamsDAO.getTeamEmailSettings(teamId);
	}

	@Override
	public Map<String, Object> saveTeamPushNotifySettings(TeamRequest teamReq) {
		return teamsDAO.saveTeamPushNotifySettings(teamReq);
	}

	@Override
	public Map<String, Object> getTeamTeamPushNotifySettings(int teamId) {
		return teamsDAO.getTeamTeamPushNotifySettings(teamId);
	}

}
