package com.autolib.helpdesk.Reminder.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Reminder.model.ReminderRequest;
import com.autolib.helpdesk.Reminder.model.ReminderSearchRequest;
import com.autolib.helpdesk.Reminder.service.ReminderService;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("reminders")
public class ReminderController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	ReminderService reminderService;

	@PostMapping("add-reminders")
	public ResponseEntity<?> addReminder(@RequestHeader(value = "Authorization") String token,
			@RequestBody ReminderRequest request) {

		logger.info("addReminder starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = reminderService.addReminder(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("addReminder ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("edit-reminders")
	public ResponseEntity<?> editReminders(@RequestHeader(value = "Authorization") String token,
			@RequestBody ReminderRequest request) {

		logger.info("editReminder starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = reminderService.editReminders(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("editReminder ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-reminders/{recurringId}")
	public ResponseEntity<?> getReminders(@RequestHeader(value = "Authorization") String token,
			@PathVariable("recurringId") String recurringId) {

		logger.info("getReminders starts:::" + recurringId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = reminderService.getReminders(recurringId);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getReminders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-reminders")
	public ResponseEntity<?> deleteReminders(@RequestHeader(value = "Authorization") String token,
			@RequestBody ReminderRequest request) {

		logger.info("deleteReminders starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = reminderService.deleteReminders(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("deleteReminders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("search-reminders")
	public ResponseEntity<?> searchReminders(@RequestHeader(value = "Authorization") String token,
			@RequestBody ReminderSearchRequest request) {

		logger.info("searchLeads starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = reminderService.searchReminders(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("searchLeads ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
