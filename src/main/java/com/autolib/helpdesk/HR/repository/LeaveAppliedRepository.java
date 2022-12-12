package com.autolib.helpdesk.HR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.HR.entity.LeaveApplied;

public interface LeaveAppliedRepository extends JpaRepository<LeaveApplied, Integer> {

	List<LeaveApplied> findByAgentEmailId(String emailId);

	List<LeaveApplied> findByAgentEmailIdAndStatus(String emailId, String string);

}
