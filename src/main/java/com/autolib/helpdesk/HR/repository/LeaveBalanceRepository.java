package com.autolib.helpdesk.HR.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.HR.entity.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, String> {

	LeaveBalance findByEmailId(String agentEmailId);

	@Query(value = "SELECT lad.leave_date,la.leave_type,la.agent_email_id,la.status FROM leave_applied_dates lad "
			+ " JOIN leave_applied la on (lad.leave_applied_id = la.id) "
			+ " WHERE la.status = 'Approved' AND YEAR(lad.leave_date) = YEAR(NOW()) AND la.agent_email_id = ?1 "
			+ " GROUP BY leave_type,leave_date", nativeQuery = true)
	List<Map<String, Object>> getAgentLeaveCounts(String emailId);

}
