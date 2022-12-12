package com.autolib.helpdesk.Attendance.dao;

import java.util.Map;

import com.autolib.helpdesk.Attendance.model.Attendance;
import com.autolib.helpdesk.Attendance.model.AttendanceRequest;
import com.autolib.helpdesk.Attendance.model.SiteAttendance;
import com.autolib.helpdesk.Attendance.model.SiteAttendanceRequest;
import com.autolib.helpdesk.Attendance.model.WorkingDayRequest;

public interface AttendanceDAO {

	Map<String, Object> addWorkingDay(WorkingDayRequest workingDay);

	Map<String, Object> getWorkingDay(WorkingDayRequest workingDay);

	Map<String, Object> deleteWorkingDay(WorkingDayRequest workingDay);

	Map<String, Object> markAttendance(Attendance att);

	Map<String, Object> prepareAttendance(WorkingDayRequest workingDay);

	Map<String, Object> saveAllAttendance(AttendanceRequest att);

	Map<String, Object> getAllAttendance(AttendanceRequest att);

	Map<String, Object> markSiteAttendance(SiteAttendance sa);

	Map<String, Object> getAllSiteAttendance(SiteAttendanceRequest att);

}
