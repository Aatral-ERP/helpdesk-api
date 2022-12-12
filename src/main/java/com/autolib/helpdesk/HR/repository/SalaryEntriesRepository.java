package com.autolib.helpdesk.HR.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.HR.entity.SalaryEntries;

public interface SalaryEntriesRepository extends JpaRepository<SalaryEntries, Integer> {

	SalaryEntries findByEmployeeIdAndSalaryMonthAndSalaryYear(String employeeId, String month, int year);

	@Query(value = "SELECT DISTINCT working_status,COUNT(*) AS cnt FROM attendance "
			+ "WHERE DATE_FORMAT(working_date,'%M')= ?2 AND YEAR(working_date) = ?3 "
			+ "AND employee_id = ?1 GROUP BY working_status", nativeQuery = true)
	List<Map<String, Object>> getEmployeeWorkingAndLeaveDays(String employeeId, String month, int year);

	SalaryEntries findById(int sid);
	
	

}
