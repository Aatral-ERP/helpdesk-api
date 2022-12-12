package com.autolib.helpdesk.Tickets.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Tickets.model.GmailAsTicketRequest;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;
import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;
import com.autolib.helpdesk.schedulers.repository.GoogleMailAsTicketRepository;

@RequestMapping("gmail-ticket")
@RestController
@CrossOrigin("*")
public class GmailTicketController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	GoogleMailAsTicketRepository gmailticketService;

	@Autowired
	JwtTokenUtil jwtUtil;
	
	@Autowired
	JdbcTemplate jdbcTemp;
	
	@Autowired
	EntityManager entityManager;

	@PostMapping("get-all-gmails")
	public ResponseEntity<?> getAllProducts(@RequestHeader(value = "Authorization") String token ,@RequestBody GmailAsTicketRequest gmail) throws Exception {
		logger.info("get-all-gmails starts:::"+gmail);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		List<Map<String, Object>> gmails = new ArrayList<>();
		
		
		String filterQuery = "";
	
			try {
				if(gmail.getFromDate() !=null && gmail.getToDate() !=null) {
					
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    String fromDate = String.valueOf(sdf.format(gmail.getFromDate()));
				    String toDate = String.valueOf(sdf.format(gmail.getToDate()));
						
					filterQuery = filterQuery + " AND g.createddatetime BETWEEN '"+fromDate+"' AND '"+toDate+"' ";
				}
				
				
				Query query =entityManager.createQuery(" SELECT g from GoogleMailAsTicket g WHERE 2>1 "+filterQuery,GoogleMailAsTicket.class);
				resp.put("gmails", query.getResultList());
			
				
			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		
		logger.info("get-all-gmails ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
