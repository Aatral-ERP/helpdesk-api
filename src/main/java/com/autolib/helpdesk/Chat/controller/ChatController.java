package com.autolib.helpdesk.Chat.controller;

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

import com.autolib.helpdesk.Chat.repository.MessageRepository;
import com.autolib.helpdesk.Chat.service.ChatService;
import com.autolib.helpdesk.common.GlobalAccessUtil;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@CrossOrigin("*")
@Controller
@RestController
@RequestMapping("chat")
public class ChatController {
	private final Logger log = LogManager.getLogger(this.getClass());
	@Autowired
	MessageRepository msgRepo;
	@Autowired
	ChatService chatService;
	
	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("getChatSettings")
	public ResponseEntity<String> getChatSettings(@RequestBody Map<String, Object> req ,@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("getChatSettings  req starts::" + req);		
		    jwtUtil.isValidToken(token);
		    Map<String, Object> respMap = new HashMap<>();
			try {
				respMap = chatService.getChatSettings();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		log.info("getChatSettings  req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}

	@PostMapping("saveChatSettings")
	public ResponseEntity<String> saveChatSettings(@RequestBody Map<String, Object> req,@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("saveChatSettings  req starts::" + req);
		
		jwtUtil.isValidToken(token);
	    Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = chatService.saveChatSettings(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("saveChatSettings  req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}

	@PostMapping("getMessages")
	public ResponseEntity<String> getMessages(@RequestBody Map<String, Object> req,@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("getMessages  req starts::" + req);
		
		jwtUtil.isValidToken(token);
	    Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = chatService.getMessages(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		log.info("getMessages  req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}

	@PostMapping("getRecentMessageMembers")
	public ResponseEntity<String> getRecentMessageMembers(@RequestBody Map<String, Object> req,@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("getRecentMessageMembers  req starts::" + req);
		
		jwtUtil.isValidToken(token);
	    Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = chatService.getRecentMessageMembers(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("getRecentMessageMembers req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}
	
	@PostMapping("getRecentMessageUser")
	public ResponseEntity<String> getRecentMessageUser(@RequestBody Map<String, Object> req,@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("getRecentMessageUser  req starts::" + req);
		
		jwtUtil.isValidToken(token);
	    Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = chatService.getRecentMessageUser(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("getRecentMessageUser req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}
	
	@GetMapping("getUnreadMessageCount/{chatEmployeeId}")
	public ResponseEntity<String> getUnreadMessageCount(@RequestBody @PathVariable("chatEmployeeId") String chatEmployeeId,
			@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("getUnreadMessageCount  req starts::" + chatEmployeeId);
		
		jwtUtil.isValidToken(token);
	    Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = chatService.getUnreadMessageCount(chatEmployeeId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("getUnreadMessageCount req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}
	
	@PostMapping("getUserMessages")
	public ResponseEntity<String> getUserAgentMessages(@RequestBody Map<String, Object> req,@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("getUserAgentMessages  req starts::" + req);
		
		jwtUtil.isValidToken(token);
	    Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = chatService.getUserAgentMessages(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		log.info("getMessages  req ends::" + respMap);
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(respMap), HttpStatus.OK);
	}


}
