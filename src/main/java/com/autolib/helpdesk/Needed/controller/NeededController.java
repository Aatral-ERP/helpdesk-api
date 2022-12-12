package com.autolib.helpdesk.Needed.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Needed.service.NeededService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("needed")
public class NeededController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	private NeededService neededService;

	@PostMapping("load-needed")
	public ResponseEntity<?> getNeeded(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> req) throws Exception {
		logger.info("getNeeded Starts::::::::::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = neededService.getNeeded(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getNeeded Ends::::::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
