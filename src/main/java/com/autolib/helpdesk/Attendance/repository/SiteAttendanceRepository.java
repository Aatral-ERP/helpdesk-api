/**
 * 
 */
package com.autolib.helpdesk.Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Attendance.model.SiteAttendance;

/**
 * @author Kannadasan
 *
 */
public interface SiteAttendanceRepository extends JpaRepository<SiteAttendance, Integer> {

}
