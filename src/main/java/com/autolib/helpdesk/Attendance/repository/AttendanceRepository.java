/**
 * 
 */
package com.autolib.helpdesk.Attendance.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Attendance.model.Attendance;

/**
 * @author Kannadasan
 *
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

	@Query(value = "SELECT * FROM(\r\n" + "( SELECT COUNT(*) AS work_days FROM working_days) AS work_days,\r\n"
			+ "( SELECT COUNT(*) AS staffs FROM agents) AS staffs,\r\n"
			+ "( SELECT COUNT(*) AS products FROM products) AS products,\r\n"
			+ "( SELECT COUNT(*) AS new_hires FROM agents WHERE createddatetime BETWEEN CURDATE() - INTERVAL 90 DAY AND NOW()) AS new_hires,\r\n"
			+ "( SELECT COUNT(*) AS present_staff FROM attendance WHERE working_date=CURDATE() AND working_status='W') AS present_staffs)", nativeQuery = true)
	Map<String, Object> findHrStats();

	@Query(value = "SELECT DATE_FORMAT(working_date , '%b %e') AS working_date,COUNT(*) AS coming_staffs FROM attendance  WHERE working_status='W' GROUP BY working_date DESC LIMIT 30", nativeQuery = true)
	List<Map<String, Object>> findAttCountStats();

	List<Attendance> findByWorkingDate(Date date);

	List<Attendance> findByWorkingDateIn(List<Date> date);

	List<Attendance> findByWorkingDateBetween(Date from, Date to);

	List<Attendance> findByWorkingDateBetweenAndEmployeeIdIn(Date fromDate, Date toDate, List<String> employeeId);

	Attendance findByEmployeeIdAndWorkingDate(String empId, Date date);

}
