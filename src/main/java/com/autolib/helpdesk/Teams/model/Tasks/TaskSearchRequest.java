package com.autolib.helpdesk.Teams.model.Tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskSearchRequest {

	public TaskSearchRequest() {
		// TODO Auto-generated constructor stub
	}

	private int taskId = 0;
	private int teamId = 0;
	private String subject = "";

	private String instituteId = "";
	private List<String> status = new ArrayList<>();
	private List<String> assignee = new ArrayList<>();
	private List<String> reporter = new ArrayList<>();
	private List<String> watchers = new ArrayList<>();
	private List<String> priority = new ArrayList<>();
	private List<String> labels = new ArrayList<>();
	private List<String> createdBy = new ArrayList<>();
	private List<String> lastUpdatedBy = new ArrayList<>();

	private Date dueDateTimeFrom = null;
	private Date dueDateTimeTo = null;

	private Date createddatetimeFrom = null;
	private Date createddatetimeTo = null;

	private Date lastupdatedatetimeFrom = null;
	private Date lastupdatedatetimeTo = null;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getAssignee() {
		return assignee;
	}

	public void setAssignee(List<String> assignee) {
		this.assignee = assignee;
	}

	public List<String> getReporter() {
		return reporter;
	}

	public void setReporter(List<String> reporter) {
		this.reporter = reporter;
	}

	public List<String> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<String> watchers) {
		this.watchers = watchers;
	}

	public List<String> getPriority() {
		return priority;
	}

	public void setPriority(List<String> priority) {
		this.priority = priority;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(List<String> createdBy) {
		this.createdBy = createdBy;
	}

	public List<String> getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(List<String> lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getDueDateTimeFrom() {
		return dueDateTimeFrom;
	}

	public void setDueDateTimeFrom(Date dueDateTimeFrom) {
		this.dueDateTimeFrom = dueDateTimeFrom;
	}

	public Date getDueDateTimeTo() {
		return dueDateTimeTo;
	}

	public void setDueDateTimeTo(Date dueDateTimeTo) {
		this.dueDateTimeTo = dueDateTimeTo;
	}

	public Date getCreateddatetimeFrom() {
		return createddatetimeFrom;
	}

	public void setCreateddatetimeFrom(Date createddatetimeFrom) {
		this.createddatetimeFrom = createddatetimeFrom;
	}

	public Date getCreateddatetimeTo() {
		return createddatetimeTo;
	}

	public void setCreateddatetimeTo(Date createddatetimeTo) {
		this.createddatetimeTo = createddatetimeTo;
	}

	public Date getLastupdatedatetimeFrom() {
		return lastupdatedatetimeFrom;
	}

	public void setLastupdatedatetimeFrom(Date lastupdatedatetimeFrom) {
		this.lastupdatedatetimeFrom = lastupdatedatetimeFrom;
	}

	public Date getLastupdatedatetimeTo() {
		return lastupdatedatetimeTo;
	}

	public void setLastupdatedatetimeTo(Date lastupdatedatetimeTo) {
		this.lastupdatedatetimeTo = lastupdatedatetimeTo;
	}

	@Override
	public String toString() {
		return "TaskSearchRequest [taskId=" + taskId + ", teamId=" + teamId + ", subject=" + subject + ", instituteId="
				+ instituteId + ", status=" + status + ", assignee=" + assignee + ", reporter=" + reporter
				+ ", watchers=" + watchers + ", priority=" + priority + ", labels=" + labels + ", createdBy="
				+ createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", dueDateTimeFrom=" + dueDateTimeFrom
				+ ", dueDateTimeTo=" + dueDateTimeTo + ", createddatetimeFrom=" + createddatetimeFrom
				+ ", createddatetimeTo=" + createddatetimeTo + ", lastupdatedatetimeFrom=" + lastupdatedatetimeFrom
				+ ", lastupdatedatetimeTo=" + lastupdatedatetimeTo + "]";
	}

}
