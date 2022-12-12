package com.autolib.helpdesk.HR.entity;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

public class LeaveAppliedSearchRequest {

	public LeaveAppliedSearchRequest() {
		// TODO Auto-generated constructor stub
	}

	private String leaveType;
	private String status;
	private String reason;
	private String noOfDays;
	private Date leaveFrom;
	private Date leaveTo;
	private List<Agent> appliedBy;
	private Date appliedDateFrom;
	private Date appliedDateTo;
	private List<Agent> approvedBy;
	private Date approvedDateFrom;
	private Date approvedDateTo;

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

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
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

	public Date getAppliedDateFrom() {
		return appliedDateFrom;
	}

	public void setAppliedDateFrom(Date appliedDateFrom) {
		this.appliedDateFrom = appliedDateFrom;
	}

	public Date getAppliedDateTo() {
		return appliedDateTo;
	}

	public void setAppliedDateTo(Date appliedDateTo) {
		this.appliedDateTo = appliedDateTo;
	}

	public List<Agent> getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(List<Agent> appliedBy) {
		this.appliedBy = appliedBy;
	}

	public List<Agent> getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(List<Agent> approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDateFrom() {
		return approvedDateFrom;
	}

	public void setApprovedDateFrom(Date approvedDateFrom) {
		this.approvedDateFrom = approvedDateFrom;
	}

	public Date getApprovedDateTo() {
		return approvedDateTo;
	}

	public void setApprovedDateTo(Date approvedDateTo) {
		this.approvedDateTo = approvedDateTo;
	}

	@Override
	public String toString() {
		return "LeaveAppliedSearchRequest [leaveType=" + leaveType + ", status=" + status + ", reason=" + reason
				+ ", noOfDays=" + noOfDays + ", leaveFrom=" + leaveFrom + ", leaveTo=" + leaveTo + ", appliedBy="
				+ appliedBy + ", appliedDateFrom=" + appliedDateFrom + ", appliedDateTo=" + appliedDateTo
				+ ", approvedBy=" + approvedBy + ", approvedDateFrom=" + approvedDateFrom + ", approvedDateTo="
				+ approvedDateTo + "]";
	}

}
