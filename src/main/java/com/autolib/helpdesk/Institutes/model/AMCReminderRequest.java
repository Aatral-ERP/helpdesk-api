/**
 * 
 */
package com.autolib.helpdesk.Institutes.model;

import java.util.Date;
import java.util.List;

/**
 * @author Kannadasan
 *
 */
public class AMCReminderRequest {

	/**
	 * 
	 */
	public AMCReminderRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<Institute> institutes;
	private Date fromDate;
	private Date toDate;

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
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
