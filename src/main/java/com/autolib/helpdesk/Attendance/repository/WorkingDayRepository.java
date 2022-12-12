/**
 * 
 */
package com.autolib.helpdesk.Attendance.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Attendance.model.WorkingDay;

/**
 * @author Kannadasan
 *
 */
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer> {

	WorkingDay findByWorkingDate(Date date);

	List<WorkingDay> findByWorkingDateIn(List<Date> date);

	List<WorkingDay> findByWorkingDateBetween(Date from, Date to);

}
