package com.autolib.helpdesk.Teams.model;

import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

public class TeamRequest {

	public TeamRequest() {
		// TODO Auto-generated constructor stub
	}

	private Teams teams;

	private TeamMembers teamMember;

	private TeamSetting teamSetting;

	private List<TeamEmailSetting> teamEmailSettings;

	private List<TeamPushNotifySetting> teamPushNotifySettings;

	private Agent agent;

	public List<TeamEmailSetting> getTeamEmailSettings() {
		return teamEmailSettings;
	}

	public void setTeamEmailSettings(List<TeamEmailSetting> teamEmailSettings) {
		this.teamEmailSettings = teamEmailSettings;
	}

	public List<TeamPushNotifySetting> getTeamPushNotifySettings() {
		return teamPushNotifySettings;
	}

	public void setTeamPushNotifySettings(List<TeamPushNotifySetting> teamPushNotifySettings) {
		this.teamPushNotifySettings = teamPushNotifySettings;
	}

	public TeamSetting getTeamSetting() {
		return teamSetting;
	}

	public void setTeamSetting(TeamSetting teamSetting) {
		this.teamSetting = teamSetting;
	}

	public TeamMembers getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMembers teamMember) {
		this.teamMember = teamMember;
	}

	public Teams getTeams() {
		return teams;
	}

	public void setTeams(Teams teams) {
		this.teams = teams;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
