package com.autolib.helpdesk.Sales.model;

import java.util.Date;

public class ProjectImplemantationRequest {
	
	private int date;
	
	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
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

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	private Date fromDate;
	
	private Date toDate;
	
	private String mailId;

	@Override
	public String toString() {
		return "ProjectImplemantationRequest [date=" + date + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", mailId=" + mailId + "]";
	}
	
	
}
