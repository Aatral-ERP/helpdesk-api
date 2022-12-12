package com.autolib.helpdesk.HR.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "leave_applied_dates", uniqueConstraints = {
		@UniqueConstraint(name = "uc_leave_id_date", columnNames = { "leave_applied_id", "leave_date" }) })
public class LeaveAppliedDates {

	public LeaveAppliedDates() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "leave_applied_id", nullable = false)
	private int leaveAppliedId;

	@Column
	private String agentEmailId;

	@Temporal(TemporalType.DATE)
	@Column(name = "leave_date", nullable = false)
	private Date leaveDate;

	@Column(nullable = false)
	private double lop = 0.00;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeaveAppliedId() {
		return leaveAppliedId;
	}

	public void setLeaveAppliedId(int leaveAppliedId) {
		this.leaveAppliedId = leaveAppliedId;
	}

	public String getAgentEmailId() {
		return agentEmailId;
	}

	public void setAgentEmailId(String agentEmailId) {
		this.agentEmailId = agentEmailId;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public double getLop() {
		return lop;
	}

	public void setLop(double lop) {
		this.lop = lop;
	}

	@Override
	public String toString() {
		return "LeaveAppliedDates [id=" + id + ", leaveAppliedId=" + leaveAppliedId + ", agentEmailId=" + agentEmailId
				+ ", leaveDate=" + leaveDate + ", lop=" + lop + "]";
	}

}
