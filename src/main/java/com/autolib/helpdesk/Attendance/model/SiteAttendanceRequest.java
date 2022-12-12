/**
 * 
 */
package com.autolib.helpdesk.Attendance.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.Institute;

/**
 * @author Kannadasan
 *
 */
public class SiteAttendanceRequest {

	/**
	 * 
	 */
	public SiteAttendanceRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<SiteAttendance> siteAttendances;
	private Date fromDate;
	private Date toDate;
	private List<Agent> agents;
	private List<Institute> institutes;
	private int setFrom;
	private int noOfRecords;

	public List<SiteAttendance> getSiteAttendances() {
		return siteAttendances;
	}

	public void setSiteAttendances(List<SiteAttendance> siteAttendances) {
		this.siteAttendances = siteAttendances;
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

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public int getSetFrom() {
		return setFrom;
	}

	public void setSetFrom(int setFrom) {
		this.setFrom = setFrom;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	@Override
	public String toString() {
		return "SiteAttendanceRequest [siteAttendances=" + siteAttendances + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", agents=" + agents + ", institutes=" + institutes + ", setFrom=" + setFrom
				+ ", noOfRecords=" + noOfRecords + "]";
	}

}
