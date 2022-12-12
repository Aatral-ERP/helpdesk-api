package com.autolib.helpdesk.Teams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Teams.model.Teams;

public interface TeamsRepository extends JpaRepository<Teams, Integer> {

	Teams findById(int id);

	@Query(value = "select t from Teams t where t.id in (select tm.teamId from TeamMembers tm where tm.memberEmailId = ?1)")
	List<Teams> getAllMyTeams(String emailId);

	Teams findByName(String name);

}
