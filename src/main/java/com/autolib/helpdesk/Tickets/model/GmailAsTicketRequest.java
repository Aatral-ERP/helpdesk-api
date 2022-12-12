package com.autolib.helpdesk.Tickets.model;

import java.util.Date;

import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;

public class GmailAsTicketRequest {

	public GmailAsTicketRequest() {
		// TODO Auto-generated constructor stub
	}

	private Ticket ticket;
	private GoogleMailAsTicket gmailAsTicket;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public GoogleMailAsTicket getGmailAsTicket() {
		return gmailAsTicket;
	}

	public void setGmailAsTicket(GoogleMailAsTicket gmailAsTicket) {
		this.gmailAsTicket = gmailAsTicket;
	}
	
	private Date fromDate = null;
	
	private Date toDate = null;

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
		return "GmailAsTicketRequest [ticket=" + ticket + ", gmailAsTicket=" + gmailAsTicket + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}

	

	
}
