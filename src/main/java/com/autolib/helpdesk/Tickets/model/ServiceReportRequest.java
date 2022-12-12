package com.autolib.helpdesk.Tickets.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Institutes.model.Institute;

public class ServiceReportRequest {

	public ServiceReportRequest() {
		
	}
	
	private List<Ticket> ticket;
	private List<Institute> institutes;
	
	public List<Ticket> getTicket() {
		return ticket;
	}
	public void setTicket(List<Ticket> ticket) {
		this.ticket = ticket;
	}

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
	@Override
	public String toString() {
		return "ServiceReportRequest [ticket=" + ticket + ", institutes=" + institutes + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}
	
}
