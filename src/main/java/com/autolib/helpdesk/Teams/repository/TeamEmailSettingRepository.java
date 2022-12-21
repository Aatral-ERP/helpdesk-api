package com.autolib.helpdesk.Teams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Teams.model.TeamEmailSetting;

public interface TeamEmailSettingRepository extends JpaRepository<TeamEmailSetting, Integer> {

	List<TeamEmailSetting> findByTeamId(int teamId);

	TeamEmailSetting findByTeamIdAndAction(int teamId, String action);

}
