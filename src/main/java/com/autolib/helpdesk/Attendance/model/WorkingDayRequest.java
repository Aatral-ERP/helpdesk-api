/**
 * 
 */
package com.autolib.helpdesk.Attendance.model;

import java.util.Date;
import java.util.List;

/**
 * @author Kannadasan
 *
 */
public class WorkingDayRequest {

	/**
	 * 
	 */
	public WorkingDayRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<WorkingDay> workingDays;
	private Date fromDate;
	private Date toDate;

	public List<WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
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

}
