package com.autolib.helpdesk.Teams.dao;

import java.util.Map;

import com.autolib.helpdesk.Teams.model.Tasks.TaskComments;
import com.autolib.helpdesk.Teams.model.Tasks.TaskRequest;
import com.autolib.helpdesk.Teams.model.Tasks.TaskSearchRequest;

public interface TaskDAO {

	Map<String, Object> createTask(TaskRequest taskReq);

	Map<String, Object> deleteTask(TaskRequest taskReq);

	Map<String, Object> getTeamTask(TaskRequest taskReq);

	Map<String, Object> getTask(int taskId);

	Map<String, Object> getTaskHistory(int taskId);

	Map<String, Object> updateTask(TaskRequest taskReq);

	Map<String, Object> saveTaskComment(TaskComments comment);

	Map<String, Object> getAllTaskComments(int taskId);

	Map<String, Object> getTaskSearchNeeded(TaskSearchRequest request);

	Map<String, Object> searchTeamTask(TaskSearchRequest request);

	Map<String, Object> getMyCalendarTasksEvents(TaskSearchRequest request);

}
