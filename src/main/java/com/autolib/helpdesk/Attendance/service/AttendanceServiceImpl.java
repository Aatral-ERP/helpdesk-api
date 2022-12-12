package com.autolib.helpdesk.Attendance.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Attendance.dao.AttendanceDAO;
import com.autolib.helpdesk.Attendance.model.Attendance;
import com.autolib.helpdesk.Attendance.model.AttendanceRequest;
import com.autolib.helpdesk.Attendance.model.SiteAttendance;
import com.autolib.helpdesk.Attendance.model.SiteAttendanceRequest;
import com.autolib.helpdesk.Attendance.model.WorkingDayRequest;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceDAO attendanceDAO;

	@Override
	public Map<String, Object> addWorkingDay(WorkingDayRequest workingDay) {
		return attendanceDAO.addWorkingDay(workingDay);
	}

	@Override
	public Map<String, Object> getWorkingDay(WorkingDayRequest workingDay) {
		return attendanceDAO.getWorkingDay(workingDay);
	}

	@Override
	public Map<String, Object> deleteWorkingDay(WorkingDayRequest workingDay) {
		return attendanceDAO.deleteWorkingDay(workingDay);
	}

	@Override
	public Map<String, Object> markAttendance(Attendance att) {
		return attendanceDAO.markAttendance(att);
	}

	@Override
	public Map<String, Object> prepareAttendance(WorkingDayRequest workingDay) {
		return attendanceDAO.prepareAttendance(workingDay);
	}

	@Override
	public Map<String, Object> saveAllAttendance(AttendanceRequest att) {
		return attendanceDAO.saveAllAttendance(att);
	}

	@Override
	public Map<String, Object> getAllAttendance(AttendanceRequest att) {
		return attendanceDAO.getAllAttendance(att);
	}

	@Override
	public Map<String, Object> markSiteAttendance(SiteAttendance sa) {
		return attendanceDAO.markSiteAttendance(sa);
	}

	@Override
	public Map<String, Object> getAllSiteAttendance(SiteAttendanceRequest att) {
		return attendanceDAO.getAllSiteAttendance(att);
	}

}
