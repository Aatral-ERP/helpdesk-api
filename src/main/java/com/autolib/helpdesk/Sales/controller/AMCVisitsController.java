package com.autolib.helpdesk.Sales.controller;

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

import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisitRequest;
import com.autolib.helpdesk.Sales.service.AMCVisitService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("amc-visit")
public class AMCVisitsController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	AMCVisitService amcVisitService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("save-amc-visits")
	public ResponseEntity<?> saveAMCVisits(@RequestHeader(value = "Authorization") String token,
			@RequestBody AMCVisitRequest request) {
		jwtUtil.isValidToken(token);
		logger.info("saveAMCVisit starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = amcVisitService.saveAMCVisit(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveAMCVisit ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-amc-visit")
	public ResponseEntity<?> deleteAMCVisit(@RequestHeader(value = "Authorization") String token,
			@RequestBody AMCVisitRequest request) {
		jwtUtil.isValidToken(token);
		logger.info("deleteAMCVisit starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = amcVisitService.deleteAMCVisit(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteAMCVisit ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-amc-visit/{dealId}")
	public ResponseEntity<?> getAMCVisits(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {
		jwtUtil.isValidToken(token);
		logger.info("getPaymentReminder starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = amcVisitService.getAMCVisits(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAMCVisits ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
