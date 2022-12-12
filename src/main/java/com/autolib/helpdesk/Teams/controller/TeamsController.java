package com.autolib.helpdesk.Teams.controller;

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

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Teams.model.TeamRequest;
import com.autolib.helpdesk.Teams.service.TeamsService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("teams")
public class TeamsController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	private TeamsService teamsService;

	@PostMapping("save-team")
	public ResponseEntity<?> saveTeam(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("saveTeam starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.saveTeam(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("saveTeam ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-team")
	public ResponseEntity<?> deleteTeam(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("deleteTeam starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.deleteTeam(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("deleteTeam ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-team/{id}")
	public ResponseEntity<?> getTeam(@RequestHeader(value = "Authorization") String token, @PathVariable("id") int id) {

		logger.info("getTeam starts:::" + id);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getTeam(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getTeam ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-all-teams")
	public ResponseEntity<?> getAllTeams(@RequestHeader(value = "Authorization") String token) {

		logger.info("getAllTeams starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getAllTeams();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getAllTeams ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-my-teams")
	public ResponseEntity<?> getAllMyTeams(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) {

		logger.info("getAllMyTeams starts:::" + agent);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getAllMyTeams(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getAllMyTeams ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-team-members")
	public ResponseEntity<?> addTeamMembers(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("addTeamMembers starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.addTeamMembers(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("addTeamMembers ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-team-members")
	public ResponseEntity<?> deleteTeamMembers(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("deleteTeamMembers starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.deleteTeamMembers(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("deleteTeamMembers ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-team-members/{teamId}")
	public ResponseEntity<?> getTeamMembers(@RequestHeader(value = "Authorization") String token,
			@PathVariable("teamId") int teamId) {

		logger.info("getTeamMembers starts:::" + teamId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getTeamMembers(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getTeamMembers ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-team-setting")
	public ResponseEntity<?> saveTeamSettings(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("saveTeamSettings starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.saveTeamSettings(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("saveTeamSettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-team-setting/{teamId}")
	public ResponseEntity<?> getTeamSettings(@RequestHeader(value = "Authorization") String token,
			@PathVariable("teamId") int teamId) {

		logger.info("getTeamSettings starts:::" + teamId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getTeamSettings(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getTeamSettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-team-email-setting")
	public ResponseEntity<?> saveTeamEmailSettings(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("saveTeamEmailSettings starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.saveTeamEmailSettings(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("saveTeamEmailSettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-team-email-setting/{teamId}")
	public ResponseEntity<?> getTeamEmailSettings(@RequestHeader(value = "Authorization") String token,
			@PathVariable("teamId") int teamId) {

		logger.info("getTeamEmailSettings starts:::" + teamId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getTeamEmailSettings(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getTeamEmailSettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-team-push-notify-setting")
	public ResponseEntity<?> saveTeamPushNotifySettings(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("saveTeamPushNotifySettings starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.saveTeamPushNotifySettings(teamReq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("saveTeamPushNotifySettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-team-push-notify-setting/{teamId}")
	public ResponseEntity<?> getTeamTeamPushNotifySettings(@RequestHeader(value = "Authorization") String token,
			@PathVariable("teamId") int teamId) {

		logger.info("getTeamTeamPushNotifySettings starts:::" + teamId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = teamsService.getTeamTeamPushNotifySettings(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getTeamTeamPushNotifySettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
