package com.autolib.helpdesk.Attendance.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Attendance.model.Attendance;
import com.autolib.helpdesk.Attendance.model.AttendanceRequest;
import com.autolib.helpdesk.Attendance.model.SiteAttendance;
import com.autolib.helpdesk.Attendance.model.SiteAttendanceRequest;
import com.autolib.helpdesk.Attendance.model.WorkingDayRequest;
import com.autolib.helpdesk.Attendance.service.AttendanceService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("attendance")
public class AttendanceController {
	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	AttendanceService attendanceService;

	@Autowired
	JwtTokenUtil jwtUtil;
	
	@PostMapping("add-working-day")
	public ResponseEntity<?> addWorkingDay(@RequestHeader(value = "Authorization") String token,
			@RequestBody WorkingDayRequest wd) {

		logger.info("addWorkingDay starts:::" + wd);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.addWorkingDay(wd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("addWorkingDay ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-working-day")
	public ResponseEntity<?> getWorkingDay(@RequestHeader(value = "Authorization") String token,
			@RequestBody WorkingDayRequest wd) {

		logger.info("getWorkingDay starts:::" + wd);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.getWorkingDay(wd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getWorkingDay ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-working-day")
	public ResponseEntity<?> deleteWorkingDay(@RequestHeader(value = "Authorization") String token,
			@RequestBody WorkingDayRequest wd) {

		logger.info("getWorkingDay starts:::" + wd);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.deleteWorkingDay(wd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getWorkingDay ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("mark-attendance")
	public ResponseEntity<?> markAttendance(@RequestHeader(value = "Authorization") String token,
			@RequestBody Attendance att) {
		logger.info("markAttendance starts:::" + att);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.markAttendance(att);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("markAttendance ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("prepare-attendance")
	public ResponseEntity<?> prepareAttendance(@RequestHeader(value = "Authorization") String token,
			@RequestBody WorkingDayRequest req) {
		logger.info("markAttendance starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.prepareAttendance(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("markAttendance ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-all-attendance")
	public ResponseEntity<?> saveAllAttendance(@RequestHeader(value = "Authorization") String token,
			@RequestBody AttendanceRequest att) {
		logger.info("saveAllAttendance starts:::" + att);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.saveAllAttendance(att);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveAllAttendance ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-attendance")
	public ResponseEntity<?> getAllAttendance(@RequestHeader(value = "Authorization") String token,
			@RequestBody AttendanceRequest att) {
		logger.info("getAllAttendance starts:::" + att);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.getAllAttendance(att);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAllAttendance ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("mark-site-attendance")
	public ResponseEntity<?> markSiteAttendance(@RequestHeader(value = "Authorization") String token,
			@RequestBody SiteAttendance sa) {
		logger.info("markSiteAttendance starts:::" + sa);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.markSiteAttendance(sa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("markSiteAttendance ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-site-attendance")
	public ResponseEntity<?> getAllSiteAttendance(@RequestHeader(value = "Authorization") String token,
			@RequestBody SiteAttendanceRequest sa) {
		logger.info("getAllSiteAttendance starts:::" + sa);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = attendanceService.getAllSiteAttendance(sa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAllSiteAttendance ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
