package com.autolib.helpdesk.HR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.HR.entity.LeaveMaster;

public interface LeaveMasterRepository extends JpaRepository<LeaveMaster, Integer> {

	LeaveMaster findById(int id);

	@Query(value = "select lm from LeaveMaster lm where lm.id = (select a.leaveMasterId from Agent a where a.emailId = ?1)")
	LeaveMaster findByAgentEmailId(String emailId);

}
