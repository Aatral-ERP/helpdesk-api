package com.autolib.helpdesk.Teams.model.Tasks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tasks", indexes = { @Index(name = "team_id_idx", columnList = "team_id"),
		@Index(name = "insitute_id_idx", columnList = "insitute_id"),
		@Index(name = "subject_idx", columnList = "subject"), @Index(name = "assignee_idx", columnList = "assignee"),
		@Index(name = "reporter_idx", columnList = "reporter"),
		@Index(name = "created_by_idx", columnList = "created_by"), @Index(name = "label_idx", columnList = "label"),
		@Index(name = "due_date_time_idx", columnList = "due_date_time"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "priority_idx", columnList = "priority"), @Index(name = "status_idx", columnList = "status") })
public class Task {

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public Task(int taskId, int teamId, int instituteId, String subject, String status, String priority,
			String assignee, String reporter, Date dueDateTime, Date lastupdatedatetime) {
		super();
		this.taskId = taskId;
		this.teamId = teamId;
		this.instituteId = instituteId;
		this.subject = subject;
		this.status = status;
		this.priority = priority;
		this.assignee = assignee;
		this.reporter = reporter;
		this.dueDateTime = dueDateTime;
		this.lastupdatedatetime = lastupdatedatetime;
	}

	public Task(int taskId, int teamId, String subject, String status, String assignee, String reporter,
			Date dueDateTime, Date createddatetime) {
		super();
		this.taskId = taskId;
		this.teamId = teamId;
		this.subject = subject;
		this.status = status;
		this.assignee = assignee;
		this.reporter = reporter;
		this.dueDateTime = dueDateTime;
		this.createddatetime = createddatetime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int taskId;

	@Column(name = "team_id", nullable = false)
	private int teamId;

	@Column(name = "insitute_id", nullable = false)
	private int instituteId;

	@Column
	private String subject;

	@Column
	private String subtasks;

	@Lob
	@Column
	private String description;

	@Column
	private String status;

	@Column
	private String priority;

	@Column
	private String assignee;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "due_date_time")
	private Date dueDateTime;

	@Column
	private String reporter;

	@Column
	private String label;

	@Lob
	@Column
	private String watchers;

	@Lob
	@Column
	private String files;

	@Column(name = "created_by")
	private String createdBy;

	@Column
	private String lastUpdatedBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

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

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(String subtasks) {
		this.subtasks = subtasks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getDueDateTime() {
		return dueDateTime;
	}

	public void setDueDateTime(Date dueDateTime) {
		this.dueDateTime = dueDateTime;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	public String getWatchers() {
		return watchers;
	}

	public void setWatchers(String watchers) {
		this.watchers = watchers;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", teamId=" + teamId + ", instituteId=" + instituteId + ", subject=" + subject
				+ ", subtasks=" + subtasks + ", description=" + description + ", status=" + status + ", priority="
				+ priority + ", assignee=" + assignee + ", dueDateTime=" + dueDateTime + ", reporter=" + reporter
				+ ", label=" + label + ", watchers=" + watchers + ", files=" + files + ", createdBy=" + createdBy
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", createddatetime=" + createddatetime + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
