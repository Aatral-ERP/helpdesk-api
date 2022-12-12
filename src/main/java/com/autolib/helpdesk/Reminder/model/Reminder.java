package com.autolib.helpdesk.Reminder.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "reminders")
public class Reminder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String agentEmailId;

	@Column
	private String subject;

	@Lob
	@Column
	private String description;

	@Column
	private String tag;

	@Column
	private String status;

	@Column
	private String recurringType;

	@Column
	private String recurringId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "event_date_time", nullable = false, updatable = false)
	private Date eventDateTime;

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

	public String getAgentEmailId() {
		return agentEmailId;
	}

	public void setAgentEmailId(String agentEmailId) {
		this.agentEmailId = agentEmailId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(String recurringType) {
		this.recurringType = recurringType;
	}

	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecurringId() {
		return recurringId;
	}

	public void setRecurringId(String recurringId) {
		this.recurringId = recurringId;
	}

	@Override
	public String toString() {
		return "Reminder [id=" + id + ", agentEmailId=" + agentEmailId + ", subject=" + subject + ", description="
				+ description + ", tag=" + tag + ", status=" + status + ", recurringType=" + recurringType
				+ ", recurringId=" + recurringId + ", eventDateTime=" + eventDateTime + ", createddatetime="
				+ createddatetime + "]";
	}

}
