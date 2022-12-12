/**
 * 
 */
package com.autolib.helpdesk.Attendance.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "site_attendance", indexes = { @Index(name = "employee_id_idx", columnList = "employee_id"),
		@Index(name = "institute_id_idx", columnList = "institute_id"),
		@Index(name = "start_time_idx", columnList = "start_time"), })
public class SiteAttendance {

	/**
	 * 
	 */
	public SiteAttendance() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String startlatitude;

	@Column
	private String startlongitude;

	@Column
	private String endlatitude;

	@Column
	private String endlongitude;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Agent agent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;

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

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
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

	public String getStartlatitude() {
		return startlatitude;
	}

	public void setStartlatitude(String startlatitude) {
		this.startlatitude = startlatitude;
	}

	public String getStartlongitude() {
		return startlongitude;
	}

	public void setStartlongitude(String startlongitude) {
		this.startlongitude = startlongitude;
	}

	public String getEndlatitude() {
		return endlatitude;
	}

	public void setEndlatitude(String endlatitude) {
		this.endlatitude = endlatitude;
	}

	public String getEndlongitude() {
		return endlongitude;
	}

	public void setEndlongitude(String endlongitude) {
		this.endlongitude = endlongitude;
	}

	@Override
	public String toString() {
		return "SiteAttendance [id=" + id + ", startlatitude=" + startlatitude + ", startlongitude=" + startlongitude
				+ ", endlatitude=" + endlatitude + ", endlongitude=" + endlongitude + ", institute=" + institute
				+ ", agent=" + agent + ", startTime=" + startTime + ", endTime=" + endTime + ", createddatetime="
				+ createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
