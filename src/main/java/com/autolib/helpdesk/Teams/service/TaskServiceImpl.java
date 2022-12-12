package com.autolib.helpdesk.Teams.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Teams.dao.TaskDAO;
import com.autolib.helpdesk.Teams.model.Tasks.TaskComments;
import com.autolib.helpdesk.Teams.model.Tasks.TaskRequest;
import com.autolib.helpdesk.Teams.model.Tasks.TaskSearchRequest;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Override
	public Map<String, Object> createTask(TaskRequest taskReq) {
		return taskDAO.createTask(taskReq);
	}
	
	@Override
	public Map<String, Object> deleteTask(TaskRequest taskReq) {
		return taskDAO.deleteTask(taskReq);
	}

	@Override
	public Map<String, Object> getTeamTask(TaskRequest taskReq) {
		return taskDAO.getTeamTask(taskReq);
	}

	@Override
	public Map<String, Object> getTask(int taskId) {
		return taskDAO.getTask(taskId);
	}

	@Override
	public Map<String, Object> getTaskHistory(int taskId) {
		return taskDAO.getTaskHistory(taskId);
	}

	@Override
	public Map<String, Object> updateTask(TaskRequest taskReq) {
		return taskDAO.updateTask(taskReq);
	}

	@Override
	public Map<String, Object> saveTaskComment(TaskComments comment) {
		return taskDAO.saveTaskComment(comment);
	}

	@Override
	public Map<String, Object> getAllTaskComments(int taskId) {
		return taskDAO.getAllTaskComments(taskId);
	}

	@Override
	public Map<String, Object> getTaskSearchNeeded(TaskSearchRequest request) {
		return taskDAO.getTaskSearchNeeded(request);
	}
	
	@Override
	public Map<String, Object> searchTeamTask(TaskSearchRequest request) {
		return taskDAO.searchTeamTask(request);
	}
	
	@Override
	public Map<String, Object> getMyCalendarTasksEvents(TaskSearchRequest request) {
		return taskDAO.getMyCalendarTasksEvents(request);
	}

}
