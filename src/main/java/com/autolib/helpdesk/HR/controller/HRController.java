package com.autolib.helpdesk.HR.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.HR.service.HRService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@RestController
@RequestMapping("hr")
@CrossOrigin
public class HRController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	HRService hrService;

	@Autowired
	JwtTokenUtil jwtUtil;
	
	@PostMapping("get-hr-dashboard-details")
	public ResponseEntity<?> getHrDashboardDetails(@RequestHeader (value ="Authorization") String token)throws Exception
	{
		logger.info("getHrDashboardDetails Starts::::::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp=new HashMap<String, Object>();
		try {
			resp=hrService.getHrDashboardDetails();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		logger.info("getHrDashboardDetails Ends::::::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);		
	}
	
	@PostMapping("get-attendance-count")
	public ResponseEntity<?>getAttendanceCount(@RequestHeader (value="Authorization") String token)
	{
		logger.info("getAttendanceCount Starts::::::");
		jwtUtil.isValidToken(token);
        Map<String,Object> resp =new HashMap <String, Object>();
        try
        {
        	resp=hrService.getAttendanceCount();
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        logger.info("getHrDashboardDetails Ends::::::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
