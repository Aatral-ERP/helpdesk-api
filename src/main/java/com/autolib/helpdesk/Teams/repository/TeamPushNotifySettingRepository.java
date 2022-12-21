package com.autolib.helpdesk.Teams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Teams.model.TeamPushNotifySetting;

public interface TeamPushNotifySettingRepository extends JpaRepository<TeamPushNotifySetting, Integer> {

	List<TeamPushNotifySetting> findByTeamId(int teamId);

	TeamPushNotifySetting findByTeamIdAndAction(int teamId, String string);

}
