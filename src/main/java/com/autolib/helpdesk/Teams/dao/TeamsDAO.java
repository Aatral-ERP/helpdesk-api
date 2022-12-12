package com.autolib.helpdesk.Teams.dao;

import java.util.Map;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Teams.model.TeamRequest;

public interface TeamsDAO {

	Map<String, Object> saveTeam(TeamRequest teamReq);

	Map<String, Object> getAllTeams();

	Map<String, Object> getTeam(int id);

	Map<String, Object> deleteTeam(TeamRequest teamReq);

	Map<String, Object> getAllMyTeams(Agent agent);

	Map<String, Object> addTeamMembers(TeamRequest teamReq);

	Map<String, Object> deleteTeamMembers(TeamRequest teamReq);

	Map<String, Object> getTeamMembers(int teamId);

	Map<String, Object> saveTeamSettings(TeamRequest teamReq);

	Map<String, Object> getTeamSettings(int teamId);

	Map<String, Object> saveTeamEmailSettings(TeamRequest teamReq);

	Map<String, Object> getTeamEmailSettings(int teamId);

	Map<String, Object> saveTeamPushNotifySettings(TeamRequest teamReq);

	Map<String, Object> getTeamTeamPushNotifySettings(int teamId);

}
