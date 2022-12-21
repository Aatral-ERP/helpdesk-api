package com.autolib.helpdesk.Teams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Teams.model.TeamSetting;

public interface TeamSettingsRepository extends JpaRepository<TeamSetting, Integer> {

	TeamSetting findByTeamId(int teamId);

}
