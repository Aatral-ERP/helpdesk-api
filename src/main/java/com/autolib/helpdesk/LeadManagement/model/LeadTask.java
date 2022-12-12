package com.autolib.helpdesk.LeadManagement.model;

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
@Table(name = "lead_tasks", indexes = { @Index(name = "lead_id_idx", columnList = "lead_id") })
public class LeadTask {

	public LeadTask() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "lead_id", nullable = false)
	private int leadId;

	@Column(name = "subject")
	private String subject;

	@Lob
	@Column
	private String description;

	@Column
	private String priority;

	@Column
	private String status;

	@Column(name = "due_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDateTime;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeadId() {
		return leadId;
	}

	public void setLeadId(int leadId) {
		this.leadId = leadId;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDueDateTime() {
		return dueDateTime;
	}

	public void setDueDateTime(Date dueDateTime) {
		this.dueDateTime = dueDateTime;
	}

	@Override
	public String toString() {
		return "LeadTask [id=" + id + ", leadId=" + leadId + ", subject=" + subject + ", description=" + description
				+ ", priority=" + priority + ", status=" + status + ", dueDateTime=" + dueDateTime
				+ ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
