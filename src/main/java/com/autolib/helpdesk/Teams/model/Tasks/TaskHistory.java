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

@Entity
@Table(name = "task_history", indexes = { @Index(name = "task_id_idx", columnList = "task_id") })
public class TaskHistory {

	public TaskHistory() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "task_id")
	private int taskId;

	@Lob
	@Column
	private String historyFrom;

	@Lob
	@Column
	private String historyTo;

	@Column
	private String field;

	@Column
	private String updatedBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getHistoryFrom() {
		return historyFrom;
	}

	public void setHistoryFrom(String historyFrom) {
		this.historyFrom = historyFrom;
	}

	public String getHistoryTo() {
		return historyTo;
	}

	public void setHistoryTo(String historyTo) {
		this.historyTo = historyTo;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	@Override
	public String toString() {
		return "TaskHistory [id=" + id + ", taskId=" + taskId + ", historyFrom=" + historyFrom + ", historyTo="
				+ historyTo + ", field=" + field + ", updatedBy=" + updatedBy + ", createddatetime=" + createddatetime
				+ "]";
	}

}
