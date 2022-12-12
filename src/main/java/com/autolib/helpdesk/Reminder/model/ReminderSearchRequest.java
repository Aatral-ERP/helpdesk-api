package com.autolib.helpdesk.Reminder.model;

import java.util.Date;
import java.util.List;

public class ReminderSearchRequest {

	private String agentEmailId;
	private String subject;
	private String description;
	private String tag;
	private String status;
	private String recurringType;
	private String recurringId;

	private Date eventDateTimeFrom;
	private Date eventDateTimeTo;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(String recurringType) {
		this.recurringType = recurringType;
	}

	public String getRecurringId() {
		return recurringId;
	}

	public void setRecurringId(String recurringId) {
		this.recurringId = recurringId;
	}

	public Date getEventDateTimeFrom() {
		return eventDateTimeFrom;
	}

	public void setEventDateTimeFrom(Date eventDateTimeFrom) {
		this.eventDateTimeFrom = eventDateTimeFrom;
	}

	public Date getEventDateTimeTo() {
		return eventDateTimeTo;
	}

	public void setEventDateTimeTo(Date eventDateTimeTo) {
		this.eventDateTimeTo = eventDateTimeTo;
	}

	@Override
	public String toString() {
		return "ReminderSearchRequest [agentEmailId=" + agentEmailId + ", subject=" + subject + ", description="
				+ description + ", tag=" + tag + ", status=" + status + ", recurringType=" + recurringType
				+ ", recurringId=" + recurringId + ", eventDateTimeFrom=" + eventDateTimeFrom + ", eventDateTimeTo="
				+ eventDateTimeTo + "]";
	}

}
