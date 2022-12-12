package com.autolib.helpdesk.Teams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Teams.model.TeamMembers;

public interface TeamMembersRepository extends JpaRepository<TeamMembers, Integer> {

	TeamMembers findByTeamIdAndMemberEmailId(int id, String emailId);

	void deleteByTeamId(int id);

	List<TeamMembers> findByTeamId(int teamId);

	List<TeamMembers> findByTeamIdAndMemberRole(int teamId, String string);

	@Query(value = "UPDATE team_members tm SET "
			+ "	open_tasks = (SELECT IFNULL(COUNT(*),0) AS open_tasks FROM tasks t WHERE t.team_id = tm.team_id AND t.assignee = tm.member_email_id AND t.status != 'Done'),"
			+ "	closed_tasks = (SELECT IFNULL(COUNT(*),0) AS closed_tasks FROM tasks t WHERE t.team_id = tm.team_id AND t.assignee = tm.member_email_id AND t.status = 'Done')"
			+ "	WHERE tm.team_id = ?1", nativeQuery = true)
	void updateTeamMembersTasksCount(int teamId);

}
