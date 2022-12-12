package com.autolib.helpdesk.Tickets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.Institute;

public class TicketReportRequest {

	public TicketReportRequest() {
		// TODO Auto-generated constructor stub
	}

	private String requestVersion = "";

	private Ticket ticket;
	private int ticketId;
	private List<Institute> institutes;
	private List<Agent> agents;
	private List<Agent> createdAgents;
	private List<Agent> reportingToAgents;

	private Date fromDate;
	private Date toDate;

	private Date fromDueDate;
	private Date toDueDate;

	private Date fromCreatedDate;
	private Date toCreatedDate;

	private Date fromLastModifiedDate;
	private Date toLastModifiedDate;

	private List<String> serviceUnder = new ArrayList<>();
	private List<String> category = new ArrayList<>();
	private List<String> priority = new ArrayList<>();
	private List<String> ticketStatus = new ArrayList<>();

	public Date getFromDueDate() {
		return fromDueDate;
	}

	public void setFromDueDate(Date fromDueDate) {
		this.fromDueDate = fromDueDate;
	}

	public Date getToDueDate() {
		return toDueDate;
	}

	public void setToDueDate(Date toDueDate) {
		this.toDueDate = toDueDate;
	}

	public Date getFromCreatedDate() {
		return fromCreatedDate;
	}

	public void setFromCreatedDate(Date fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	public Date getToCreatedDate() {
		return toCreatedDate;
	}

	public void setToCreatedDate(Date toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}

	public Date getFromLastModifiedDate() {
		return fromLastModifiedDate;
	}

	public void setFromLastModifiedDate(Date fromLastModifiedDate) {
		this.fromLastModifiedDate = fromLastModifiedDate;
	}

	public Date getToLastModifiedDate() {
		return toLastModifiedDate;
	}

	public void setToLastModifiedDate(Date toLastModifiedDate) {
		this.toLastModifiedDate = toLastModifiedDate;
	}

	public String getRequestVersion() {
		return requestVersion;
	}

	public void setRequestVersion(String requestVersion) {
		this.requestVersion = requestVersion;
	}

	public List<String> getServiceUnder() {
		return serviceUnder;
	}

	public void setServiceUnder(List<String> serviceUnder) {
		this.serviceUnder = serviceUnder;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<String> getPriority() {
		return priority;
	}

	public void setPriority(List<String> priority) {
		this.priority = priority;
	}

	public List<String> getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(List<String> ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
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

	public List<Agent> getCreatedAgents() {
		return createdAgents;
	}

	public void setCreatedAgents(List<Agent> createdAgents) {
		this.createdAgents = createdAgents;
	}

	public List<Agent> getReportingToAgents() {
		return reportingToAgents;
	}

	public void setReportingToAgents(List<Agent> reportingToAgents) {
		this.reportingToAgents = reportingToAgents;
	}

	@Override
	public String toString() {
		return "TicketReportRequest [ticket=" + ticket + ", institutes=" + institutes + ", agents=" + agents
				+ ", createdAgents=" + createdAgents + ", reportingToAgents=" + reportingToAgents + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}

}
