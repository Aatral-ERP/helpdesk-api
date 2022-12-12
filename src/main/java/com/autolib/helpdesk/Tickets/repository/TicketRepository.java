package com.autolib.helpdesk.Tickets.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.common.EnumUtils.TicketStatus;

public interface TicketRepository extends CrudRepository<Ticket, String>, JpaSpecificationExecutor<Ticket> {

	Ticket findByTicketId(int ticketid);

	List<Ticket> findByInstitute(Institute institute);

	@Query(value = "select * from(( SELECT COUNT(DISTINCT(institute_id)) AS institutesCount FROM institute_products) as institutesCount, "
			+ "( select count(*) as unassginedCount from tickets where status = 'Raised') as raisedCount,"
			+ "( select count(*) as closedCount from tickets where status = 'Closed' and assigned_to = ?1) as closedCount, "
			+ "( select count(*) as pendingCount from tickets where status = 'Assigned' and assigned_to = ?1) as pendingCount, "
			+ "( select count(*) as assignedToMe from tickets where assigned_to = ?1 and status != 'Closed') as assignedToMe,"
			+ "( select count(*) as markedAsCompleted from tickets where status = 'Marked_As_Completed' AND assigned_by = ?1) as markedAsCompleted) ", nativeQuery = true)
	Map<String, Object> findHomeStats(String emailId);

	List<Ticket> findByAssignedToAndStatus(String emailId, TicketStatus status);

	List<Ticket> findByAssignedByAndStatusNotIn(String emailId, List<TicketStatus> status);

	List<Ticket> findByAssignedToAndStatusNotIn(String emailId, List<TicketStatus> status);

	@Query(value = "SELECT * FROM tickets WHERE assigned_to = ?1 "
			+ " AND TIMESTAMPDIFF(DAY,closed_date_time,CURRENT_TIMESTAMP) < 2 ORDER BY closed_date_time DESC ;", nativeQuery = true)
	List<Ticket> getAssignedToMeClosedTickets(String emailId);

	@Query(value = "SELECT * FROM tickets WHERE (created_by = ?1 OR assigned_by = ?1 ) "
			+ " AND TIMESTAMPDIFF(DAY,closed_date_time,CURRENT_TIMESTAMP) < 2"
			+ " ORDER BY closed_date_time DESC;", nativeQuery = true)
	List<Ticket> getAssignedByMeClosedTickets(String emailId);

	List<Ticket> findByAssignedTo(String emailId, Pageable pageable);

	List<Ticket> findByAssignedTo(String emailId);

	List<Ticket> findByStatus(String status);

	@Query(value = "select distinct t.serviceType from Ticket t")
	List<String> findAllDistinctServiceType();

	@Query(value = "SELECT * FROM tickets "
			+ "WHERE STATUS='Assigned' AND TIMESTAMPDIFF(HOUR,due_date_time,NOW()) BETWEEN -7 AND -6;", nativeQuery = true)
	List<Ticket> findDueTicketsIn1Hour();

	@Query(value = "SELECT * FROM tickets "
			+ "WHERE STATUS='Assigned' AND TIMESTAMPDIFF(HOUR,due_date_time,NOW()) BETWEEN -5 AND -4;", nativeQuery = true)
	List<Ticket> findExperiedDueTicketsAfter1Hour();

	@Query(value = "SELECT STATUS as `name`,COUNT(*) as `value` FROM tickets "
			+ "WHERE STATUS != 'Closed' AND assigned_to= ?1 GROUP BY STATUS ", nativeQuery = true)
	List<Map<String, Object>> findAgentPendingTicketsStatusCounts(String emailId);

	@Query(value = "SELECT priority as `name`,COUNT(*) as `value` FROM tickets WHERE STATUS != 'Closed' AND assigned_to= ?1 GROUP BY priority ", nativeQuery = true)
	List<Map<String, Object>> findAgentPendingTicketsPriorityCounts(String emailId);

	@Query(value = "SELECT * FROM ( (SELECT 'Open Tickets' AS label,COUNT(*) AS cnt FROM tickets "
			+ " WHERE STATUS != 'Closed' AND due_date_time > NOW() AND assigned_to= ?1 ) UNION ALL "
			+ " (SELECT 'Open Tickets past due' AS label,COUNT(*) AS cnt FROM tickets "
			+ " WHERE STATUS != 'Closed' AND due_date_time < NOW() AND assigned_to= ?1 ) UNION ALL "
			+ " (SELECT 'Closed Today' AS label,COUNT(*) AS cnt FROM tickets"
			+ " WHERE STATUS = 'Closed' AND assigned_to= ?1 AND lastupdatedatetime = CURDATE() ) UNION ALL "
			+ " (SELECT 'Closed This Week' AS label,COUNT(*) AS cnt FROM tickets"
			+ " WHERE STATUS = 'Closed' AND assigned_to= ?1 AND WEEK(lastupdatedatetime) = WEEK(CURDATE())"
			+ " AND YEAR(lastupdatedatetime) = YEAR(CURDATE()) )   ) AS _t ", nativeQuery = true)
	List<Map<String, Object>> findAgentPeriodicallyClosedCounts(String emailId);

	@Query(value = "SELECT _ratings.rating,IFNULL(cnt,0) AS no_of_ratings,0 as percent FROM (SELECT 1 AS rating UNION ALL "
			+ "SELECT 2 AS rating UNION ALL SELECT 3 AS rating UNION ALL SELECT 4 AS rating UNION ALL "
			+ "SELECT 5 AS rating ) AS _ratings LEFT JOIN "
			+ "(SELECT ROUND(rating) AS rating,COUNT(*) AS cnt FROM ticket_ratings WHERE ticket_id IN "
			+ "(SELECT ticket_id FROM tickets WHERE assigned_to = ?1 AND STATUS='Closed') GROUP BY ROUND(rating)) AS _data "
			+ "ON (_data.rating = _ratings.rating)", nativeQuery = true)
	List<Map<String, Object>> findAgentTicketRatingCounts(String emailId);

}
