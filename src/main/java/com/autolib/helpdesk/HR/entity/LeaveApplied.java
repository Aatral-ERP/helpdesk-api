package com.autolib.helpdesk.HR.entity;

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
@Table(name = "leave_applied", indexes = { @Index(name = "agent_email_id_idx", columnList = "agent_email_id") })
public class LeaveApplied {

	public LeaveApplied() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "agent_email_id", nullable = false)
	private String agentEmailId;

	@Column
	private String leaveType;

	@Column
	private String status;

	@Column
	private String approvedRejectedBy;

	@Column
	private Date approvedRejectedDate;

	@Column(name = "no_of_days", nullable = false)
	private double noOfDays = 0.00;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date leaveFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date leaveTo;

	@Lob
	@Column
	private String reason;

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

	public String getAgentEmailId() {
		return agentEmailId;
	}

	public void setAgentEmailId(String agentEmailId) {
		this.agentEmailId = agentEmailId;
	}

	public double getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(double nofDays) {
		this.noOfDays = nofDays;
	}

	public Date getLeaveFrom() {
		return leaveFrom;
	}

	public void setLeaveFrom(Date leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	public Date getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(Date leaveTo) {
		this.leaveTo = leaveTo;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getApprovedRejectedBy() {
		return approvedRejectedBy;
	}

	public void setApprovedRejectedBy(String approvedRejectedBy) {
		this.approvedRejectedBy = approvedRejectedBy;
	}

	public Date getApprovedRejectedDate() {
		return approvedRejectedDate;
	}

	public void setApprovedRejectedDate(Date approvedRejectedDate) {
		this.approvedRejectedDate = approvedRejectedDate;
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

	@Override
	public String toString() {
		return "LeaveApplied [id=" + id + ", agentEmailId=" + agentEmailId + ", leaveType=" + leaveType + ", status="
				+ status + ", approvedRejectedBy=" + approvedRejectedBy + ", approvedRejectedDate="
				+ approvedRejectedDate + ", noOfDays=" + noOfDays + ", leaveFrom=" + leaveFrom + ", leaveTo=" + leaveTo
				+ ", reason=" + reason + ", createddatetime=" + createddatetime + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
