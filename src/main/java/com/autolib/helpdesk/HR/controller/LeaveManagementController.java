package com.autolib.helpdesk.HR.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.HR.entity.Holiday;
import com.autolib.helpdesk.HR.entity.LeaveApplied;
import com.autolib.helpdesk.HR.entity.LeaveAppliedSearchRequest;
import com.autolib.helpdesk.HR.entity.LeaveMaster;
import com.autolib.helpdesk.HR.service.LeaveManagementService;


@RestController
@Controller
@RequestMapping("leave-management")
@CrossOrigin("*")
public class LeaveManagementController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired

	private LeaveManagementService lmService;

	@PostMapping("save-leave-master")
	public ResponseEntity<?> saveLeaveMaster(@RequestHeader(value = "Authorization") String token,
			@RequestBody LeaveMaster leaveMaster) {
		logger.info("saveLeaveMaster Starts::::::" + leaveMaster);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.saveLeaveMaster(leaveMaster);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("saveLeaveMaster ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-leave-master")
	public ResponseEntity<?> getLeaveMaster(@RequestHeader(value = "Authorization") String token,
			@RequestBody LeaveMaster leaveMaster) {
		logger.info("getLeaveMaster Starts::::::" + leaveMaster);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.getLeaveMaster(leaveMaster);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("getLeaveMaster ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-leave-master")
	public ResponseEntity<?> deleteLeaveMaster(@RequestHeader(value = "Authorization") String token,
			@RequestBody LeaveMaster leaveMaster) {
		logger.info("deleteLeaveMaster Starts::::::" + leaveMaster);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.deleteLeaveMaster(leaveMaster);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("deleteLeaveMaster ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-all-leave-masters")
	public ResponseEntity<?> getAllLeaveMasters(@RequestHeader(value = "Authorization") String token) {
		logger.info("getAllLeaveMasters Starts::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.getAllLeaveMasters();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("getAllLeaveMasters ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-leave-applied")
	public ResponseEntity<?> saveLeaveApplied(@RequestHeader(value = "Authorization") String token,
			@RequestBody LeaveApplied leaveApplied) {
		logger.info("saveLeaveApplied Starts::::::" + leaveApplied);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.saveLeaveApplied(leaveApplied);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("saveLeaveApplied ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-my-all-leave-applied")
	public ResponseEntity<?> getMyAllLeaveApplied(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) {
		logger.info("getMyAllLeaveApplied Starts::::::" + agent);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.getMyAllLeaveApplied(agent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("getMyAllLeaveApplied ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("search-leave-applied")
	public ResponseEntity<?> searchLeaveApplied(@RequestHeader(value = "Authorization") String token,
			@RequestBody LeaveAppliedSearchRequest request) {
		logger.info("searchLeaveApplied Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.searchLeaveApplied(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("searchLeaveApplied ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-leave-balances")
	public ResponseEntity<?> getAllLeaveBalances(@RequestHeader(value = "Authorization") String token) {
		logger.info("getAllLeaveBalance Starts::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.getAllLeaveBalances();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("getAllLeaveBalances ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("save-holiday")
   	public ResponseEntity<?> saveHoliday(@RequestHeader(value = "Authorization") String token ,@RequestBody Holiday holiday) {
		logger.info("saveHoliday Starts::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.saveHoliday(holiday);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("saveHoliday ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("delete-holiday")
   	public ResponseEntity<?> deleteHoliday(@RequestHeader(value = "Authorization") String token ,@RequestBody Holiday holiday) {
	
		logger.info("deleteHoliday Starts::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.deleteHoliday(holiday);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("deleteHoliday ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("view-holidays")
   	public ResponseEntity<?> viewHolidays(@RequestHeader(value = "Authorization") String token) {
	
		logger.info("view-holidays Starts::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = lmService.viewHolidays();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("viewHolidays ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
