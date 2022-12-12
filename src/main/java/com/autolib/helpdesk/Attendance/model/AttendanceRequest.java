/**
 * 
 */
package com.autolib.helpdesk.Attendance.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

/**
 * @author Kannadasan
 *
 */
public class AttendanceRequest {

	/**
	 * 
	 */
	public AttendanceRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<Agent> agents;
	private List<Attendance> attendances;
	private Date fromDate;
	private Date toDate;

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "AttendanceRequest [agents=" + agents + ", attendances=" + attendances + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}


	

}
