package com.autolib.helpdesk.Teams.model.Tasks;

import com.autolib.helpdesk.Teams.model.TeamMembers;

public class TaskRequest {

	public TaskRequest() {
		// TODO Auto-generated constructor stub
	}

	private Task task;

	private TeamMembers teamMember;

	private TaskHistory taskHistory;

	private String directoryName;

	public TeamMembers getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMembers teamMember) {
		this.teamMember = teamMember;
	}

	public TaskHistory getTaskHistory() {
		return taskHistory;
	}

	public void setTaskHistory(TaskHistory taskHistory) {
		this.taskHistory = taskHistory;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	@Override
	public String toString() {
		return "TaskRequest [task=" + task + ", teamMember=" + teamMember + ", taskHistory=" + taskHistory
				+ ", directoryName=" + directoryName + "]";
	}

}
