package com.autolib.helpdesk.Teams.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Teams.model.TeamRequest;
import com.autolib.helpdesk.Teams.model.Tasks.TaskComments;
import com.autolib.helpdesk.Teams.model.Tasks.TaskRequest;
import com.autolib.helpdesk.Teams.model.Tasks.TaskSearchRequest;
import com.autolib.helpdesk.Teams.service.TaskService;
import com.autolib.helpdesk.common.DirectoryUtil;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("tasks")
public class TaskController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	private TaskService taskService;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	@PostMapping("create-temp-directory")
	public ResponseEntity<?> createTempDirectory(@RequestHeader(value = "Authorization") String token,
			@RequestBody TeamRequest teamReq) {

		logger.info("createTempDirectory starts:::" + teamReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			String randomDirectorName = String.valueOf(Util.generateRandomPassword());
			File directory = new File(contentPath + DirectoryUtil.taskTempDirectory + randomDirectorName);
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				directory.mkdir();
			}

			resp.put("directoryName", randomDirectorName);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("createTempDirectory ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("/upload-task-attachments")
	ResponseEntity<?> fileUpload(@RequestHeader(value = "Authorization") String token,
			@RequestParam("directoryName") String directoryName, @RequestParam("file") MultipartFile file) {
		logger.info("fileUpload req Starts::::::::" + file.getSize());
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);

			File directory = new File(contentPath + DirectoryUtil.taskRootDirectory + directoryName);
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File convertFile = new File(directory.getAbsoluteFile() + "/" + file.getOriginalFilename());
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse());
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("create-task")
	public ResponseEntity<?> createTask(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskRequest taskReq) {

		logger.info("createTask starts:::" + taskReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.createTask(taskReq);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("createTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-task")
	public ResponseEntity<?> deleteTask(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskRequest taskReq) {

		logger.info("deleteTask starts:::" + taskReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.deleteTask(taskReq);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("deleteTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-team-tasks")
	public ResponseEntity<?> getTeamTask(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskRequest taskReq) {

		logger.info("getTeamTask starts:::" + taskReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.getTeamTask(taskReq);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getTeamTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-task/{taskId}")
	public ResponseEntity<?> getTask(@RequestHeader(value = "Authorization") String token,
			@PathVariable("taskId") int taskId) {

		logger.info("getTask starts:::" + taskId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.getTask(taskId);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-task-history/{taskId}")
	public ResponseEntity<?> getTaskHistory(@RequestHeader(value = "Authorization") String token,
			@PathVariable("taskId") int taskId) {

		logger.info("getTask starts:::" + taskId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.getTaskHistory(taskId);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("update-task")
	public ResponseEntity<?> updateTask(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskRequest taskReq) {

		logger.info("updateTask starts:::" + taskReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.updateTask(taskReq);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("updateTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-task-comment")
	public ResponseEntity<?> saveTaskComment(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskComments comment) {

		logger.info("saveTaskComment starts:::" + comment);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.saveTaskComment(comment);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("saveTaskComment ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-all-task-comments/{taskId}")
	public ResponseEntity<?> getAllTaskComments(@RequestHeader(value = "Authorization") String token,
			@PathVariable("taskId") int taskId) {

		logger.info("getAllTaskComments starts:::" + taskId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.getAllTaskComments(taskId);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getAllTaskComments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-task-search-needed")
	public ResponseEntity<?> getTaskSearchNeeded(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskSearchRequest request) {

		logger.info("getTaskSearchNeeded starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.getTaskSearchNeeded(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getTaskSearchNeeded ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("search-team-tasks")
	public ResponseEntity<?> searchTeamTask(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskSearchRequest request) {

		logger.info("searchTeamTask starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.searchTeamTask(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("searchTeamTask ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-my-calendar-tasks-events")
	public ResponseEntity<?> getMyCalendarTasksEvents(@RequestHeader(value = "Authorization") String token,
			@RequestBody TaskSearchRequest request) {

		logger.info("getMyCalendarTasksEvents starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = taskService.getMyCalendarTasksEvents(request);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}

		logger.info("getMyCalendarTasksEvents ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
