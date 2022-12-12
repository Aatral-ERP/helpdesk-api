/**
 * 
 */
package com.autolib.helpdesk.Attendance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.common.EnumUtils.WorkingStatus;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "attendance", uniqueConstraints = { @UniqueConstraint(name = "uc_working_date_employee", columnNames = {
		"working_date", "employee_id" }) }, indexes = { @Index(name = "working_date_idx", columnList = "working_date"),
				@Index(name = "employee_id_idx", columnList = "employee_id"),
				@Index(name = "working_status_idx", columnList = "working_status"), })
public class Attendance {

	/**
	 * 
	 */
	public Attendance() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "working_date", nullable = false)
	private Date workingDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "start_time")
	private Date startTime;

	@Temporal(TemporalType.TIME)
	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "geo_coordinates")
	private String geoCoordinates;

	@Column(name = "working_status", length = 16)
	@Enumerated(EnumType.STRING)
	private WorkingStatus workingStatus;

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

	public Date getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getGeoCoordinates() {
		return geoCoordinates;
	}

	public void setGeoCoordinates(String geoCoordinates) {
		this.geoCoordinates = geoCoordinates;
	}

	public WorkingStatus getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(WorkingStatus workingStatus) {
		this.workingStatus = workingStatus;
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
