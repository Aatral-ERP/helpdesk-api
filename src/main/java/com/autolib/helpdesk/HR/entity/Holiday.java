package com.autolib.helpdesk.HR.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="holidays")
public class Holiday implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="leave_type")
	private String leaveType;
	
	@Column(name = "holiday_date")
	@Temporal(TemporalType.DATE)
	private Date holidayDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	@Override
	public String toString() {
		return "Holiday [id=" + id + ", reason=" + reason + ", leaveType=" + leaveType + ", holidayDate=" + holidayDate
				+ "]";
	}
	
	

}
