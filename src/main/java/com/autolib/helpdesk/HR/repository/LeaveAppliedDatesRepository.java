package com.autolib.helpdesk.HR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.HR.entity.LeaveAppliedDates;

public interface LeaveAppliedDatesRepository extends JpaRepository<LeaveAppliedDates, Integer> {

//	@Query(value = "SELECT IFNULL(SUM(lop),0.00) AS lop FROM leave_applied_dates "
//			+ "WHERE DATE_FORMAT(leave_date,'%M') = ?1 AND YEAR(leave_date) = ?2", nativeQuery = true)
//	double getLOPForMonthYear(String salaryMonth, int salaryYear);
	
	@Query(value = "SELECT SUM(lop) FROM "
			+ "((SELECT IFNULL(IFNULL(COUNT(*),0) * IFNULL(sd.lop_per_day,0),0) AS lop FROM attendance a"
			+ " LEFT JOIN salary_details sd ON (sd.employee_id = a.employee_id)"
			+ " WHERE DATE_FORMAT(a.working_date,'%M') = ?1 AND YEAR(a.working_date) = ?2 AND a.employee_id = ?3"
			+ " AND a.working_status IN ('NONE','L') "
			+ " AND a.working_date NOT IN (SELECT leave_date FROM leave_applied_dates "
			+ " WHERE agent_email_id = ?4 ))"
			+ " UNION ALL (SELECT IFNULL(SUM(lop),0.00) AS lop FROM leave_applied_dates "
			+ " WHERE DATE_FORMAT(leave_date,'%M') = ?1 AND YEAR(leave_date) = ?2 ) ) AS _lop", nativeQuery = true)
	double getLOPForMonthYear(String salaryMonth, int salaryYear, String empId, String emailId);

}
