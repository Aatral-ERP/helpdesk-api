package com.autolib.helpdesk.LeadManagement.model;

import java.util.Date;

public class LeadDashboardRequest {

	private Date fromDate;
	private Date toDate;

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

}
