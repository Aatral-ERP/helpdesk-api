package com.autolib.helpdesk.HR.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

	public LeaveBalance() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column
	private String emailId;

	@Column(name = "annual_leave", nullable = false)
	public Double annualLeave = 0.00;

	@Column(name = "sick_leave", nullable = false)
	public Double sickLeave = 0.00;

	@Column(name = "casual_leave", nullable = false)
	public Double casualLeave = 0.00;

	@Column(name = "other_leave", nullable = false)
	public Double otherLeave = 0.00;

	@Column(name = "permissions", nullable = false)
	public Double permissions = 0.00;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Double getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(Double annualLeave) {
		this.annualLeave = annualLeave;
	}

	public Double getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(Double sickLeave) {
		this.sickLeave = sickLeave;
	}

	public Double getCasualLeave() {
		return casualLeave;
	}

	public void setCasualLeave(Double casualLeave) {
		this.casualLeave = casualLeave;
	}

	public Double getOtherLeave() {
		return otherLeave;
	}

	public void setOtherLeave(Double otherLeave) {
		this.otherLeave = otherLeave;
	}

	public Double getPermissions() {
		return permissions;
	}

	public void setPermissions(Double permissions) {
		this.permissions = permissions;
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

}
