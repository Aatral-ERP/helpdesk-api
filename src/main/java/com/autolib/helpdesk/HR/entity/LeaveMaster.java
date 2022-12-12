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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "leave_masters")
public class LeaveMaster {

	public LeaveMaster() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Column(name = "carry_forward", nullable = false)
	public boolean carryForward = false;

	@Column(name = "maximum_carry_forward", nullable = false)
	public int maximumCarryForward;

	@Column(name = "annual_leave", nullable = false)
	public int annualLeave = 0;

	@Column(name = "sick_leave", nullable = false)
	public int sickLeave = 0;

	@Column(name = "casual_leave", nullable = false)
	public int casualLeave = 0;

	@Column(name = "other_leave", nullable = false)
	public int otherLeave = 0;

	@Column(name = "permissions", nullable = false)
	public int permissions = 0;

	@Column(name = "lop_per_day", nullable = false)
	private Double lopPerDay = 0.00;

	@Column(name = "created_by")
	private String createdBy = "";

	@Column(name = "modified_by")
	private String modifiedBy = "";

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCarryForward() {
		return carryForward;
	}

	public void setCarryForward(boolean carryForward) {
		this.carryForward = carryForward;
	}

	public int getMaximumCarryForward() {
		return maximumCarryForward;
	}

	public void setMaximumCarryForward(int maximumCarryForward) {
		this.maximumCarryForward = maximumCarryForward;
	}

	public int getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(int annualLeave) {
		this.annualLeave = annualLeave;
	}

	public int getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(int sickLeave) {
		this.sickLeave = sickLeave;
	}

	public int getCasualLeave() {
		return casualLeave;
	}

	public void setCasualLeave(int casualLeave) {
		this.casualLeave = casualLeave;
	}

	public int getOtherLeave() {
		return otherLeave;
	}

	public void setOtherLeave(int otherLeave) {
		this.otherLeave = otherLeave;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public Double getLopPerDay() {
		return lopPerDay;
	}

	public void setLopPerDay(Double lopPerDay) {
		this.lopPerDay = lopPerDay;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
		return "LeaveMaster [id=" + id + ", name=" + name + ", carryForward=" + carryForward + ", maximumCarryForward="
				+ maximumCarryForward + ", annualLeave=" + annualLeave + ", sickLeave=" + sickLeave + ", casualLeave="
				+ casualLeave + ", otherLeave=" + otherLeave + ", permissions=" + permissions + ", lopPerDay="
				+ lopPerDay + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createddatetime="
				+ createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
