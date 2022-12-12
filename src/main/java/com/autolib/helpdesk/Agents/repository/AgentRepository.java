package com.autolib.helpdesk.Agents.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Agents.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, String> {

	Agent findByEmailId(String emailId);

	Agent findByEmployeeId(String employeeId);

//	List<Agent> findByCompany(String company);

	List<Agent> findByEmployeeIdIn(Set<String> empIds);

	@Query(value = "select new Agent(a.employeeId, a.firstName, a.lastName, a.emailId, a.isBlocked, a.workingStatus, a.photoFileName, a.designation) from Agent a where a.employeeId in ?1")
	List<Agent> findMinByEmployeeIdIn(Set<String> empIds);

	@Query(value = "select new Agent(a.employeeId, a.firstName, a.lastName, a.emailId, a.isBlocked, a.workingStatus, a.photoFileName, a.designation) from Agent a where a.employeeId = ?1")
	Agent findMinDetailsByEmployeeId(String employeeId);

	@Query(value = "select new Agent(a.employeeId, a.firstName, a.lastName, a.emailId, a.isBlocked, a.workingStatus, a.photoFileName, a.designation) from Agent a")
	List<Agent> findAllMinDetails();

	@Query(value = "SELECT DATE_FORMAT(createddatetime , '%b %e') AS `Date`,COUNT(*) AS tickets,assigned_to,DATE_FORMAT(createddatetime , '%Y-%m-%d') AS `Date1` FROM tickets WHERE assigned_to = ?1  AND createddatetime between ?2 AND ?3 GROUP BY `Date1` DESC ", nativeQuery = true)
	List<Map<String, Object>> findTicketCountStats(String assignedTo, String fromDate, String toDate);

	@Query(value = "SELECT DATE_FORMAT(createddatetime , '%b %e') AS `Date`,COUNT(*) AS tickets,assigned_to,DATE_FORMAT(createddatetime , '%Y-%m-%d') AS `Date1` FROM tickets WHERE assigned_to = ?1 GROUP BY `Date1` DESC ", nativeQuery = true)
	List<Map<String, Object>> findTicketCountStats(String assignedTo);

	@Query(value = "SELECT rating,COUNT(rating) AS `count` FROM tickets where assigned_to = ?1 AND rating!= '0' AND rating IS NOT NULL GROUP BY rating ", nativeQuery = true)
	List<Map<String, Object>> findAgentRatings(String assignedTo);

	@Query(value = "SELECT rating,COUNT(rating) AS `count` FROM tickets where assigned_to = ?1  AND rating!= '0' AND rating IS NOT NULL AND createddatetime between ?2 AND ?3 GROUP BY rating ", nativeQuery = true)
	List<Map<String, Object>> findAgentRatings(String assignedTo, String fromDate, String toDate);

	@Query(value = "SELECT COUNT(working_date) AS `count`,working_status FROM attendance WHERE employee_id=?1 GROUP BY working_status ASC ", nativeQuery = true)
	List<Map<String, Object>> findAttendance(String employeeId);

	@Query(value = "SELECT COUNT(working_date) AS `count`,working_status FROM attendance WHERE employee_id=?1 AND working_date BETWEEN ?2 AND ?3 GROUP BY working_status ASC ", nativeQuery = true)
	List<Map<String, Object>> findAttendance(String employeeId, String fromDate, String toDate);

	@Query(value = "SELECT CAST(SUM(rating)/COUNT(*)AS DECIMAL(10,1)) AS ratings FROM tickets WHERE assigned_to= ?1 AND createddatetime BETWEEN ?2 AND ?3 ", nativeQuery = true)
	String findAgentAverageRating(String assignedTo, String fromDate, String toDate);

	@Query(value = "SELECT CAST(SUM(rating)/COUNT(*)AS DECIMAL(10,1)) AS ratings FROM tickets WHERE assigned_to= ?1 ", nativeQuery = true)
	String findAgentAverageRating(String assignedTo);

	@Query(value = "SELECT COUNT(status) AS `count`,`status` as ticket_status FROM tickets WHERE assigned_to= ?1 AND createddatetime BETWEEN ?2 AND ?3 GROUP BY `status` ASC ", nativeQuery = true)
	List<Map<String, Object>> findAgentTicketStatus(String assignedTo, String fromDate, String toDate);

	@Query(value = "SELECT COUNT(status) AS `count`,`status` as ticket_status FROM tickets WHERE assigned_to= ?1 GROUP BY `status` ASC ", nativeQuery = true)
	List<Map<String, Object>> findAgentTicketStatus(String assignedTo);

	@Query(value = "SELECT COUNT(*) AS `count`, DATE_FORMAT(start_time, '%b') AS `start_time` FROM site_attendance WHERE employee_id=?1 GROUP BY DATE_FORMAT(`start_time`, '%b') ASC", nativeQuery = true)
	List<Map<String, Object>> findAgentSiteAttendance(String employeeId);

	@Query(value = "SELECT COUNT(*) AS `count`, DATE_FORMAT(start_time, '%b') AS `start_time` FROM site_attendance WHERE employee_id=?1 AND start_time BETWEEN ?2 AND ?3 GROUP BY DATE_FORMAT(`start_time`, '%b') ASC", nativeQuery = true)
	List<Map<String, Object>> findAgentSiteAttendance(String employeeId, String fromDate, String toDate);

	@Query(value = "SELECT COUNT(*) AS `count`,`priority` FROM tickets where assigned_to = ?1  GROUP BY priority ASC ", nativeQuery = true)
	List<Map<String, Object>> findAgentPriority(String assignedTo);

	@Query(value = "SELECT COUNT(*) AS `count`,`priority` FROM tickets where assigned_to = ?1  AND createddatetime between ?2 AND ?3 GROUP BY priority ASC ", nativeQuery = true)
	List<Map<String, Object>> findAgentPriority(String assignedTo, String fromDate, String toDate);

	List<Agent> findByLeaveMasterId(int id);

	@Query(value = "select new Agent(a.employeeId, a.firstName, a.lastName, a.emailId, a.isBlocked, a.workingStatus, a.photoFileName, a.designation) from Agent a where a.agentType = ?1")
	List<Agent> findByAgentType(int agentType);

	@Query(value = "select new Agent(a.employeeId, a.firstName, a.lastName, a.emailId, a.isBlocked, a.workingStatus, a.photoFileName, a.designation) from Agent a where a.agentType in (select id from RoleMaster where accounting = 'Full Access')")
	List<Agent> findAllAccountsAdmins();
	
	
	@Query(value = "select new Agent(a.employeeId, a.firstName, a.lastName, a.emailId, a.isBlocked, a.workingStatus, a.photoFileName, a.designation) from Agent a where a.agentType in (select id from RoleMaster where hr = 'Full Access')")
	List<Agent> findAllHRAdmins();

}
