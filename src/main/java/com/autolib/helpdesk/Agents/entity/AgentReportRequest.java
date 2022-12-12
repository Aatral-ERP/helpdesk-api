package com.autolib.helpdesk.Agents.entity;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Tickets.model.Ticket;

public class AgentReportRequest {

	private Ticket ticket; 
	
    private List<Agent> agents ;
	
	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	private Agent agent ;
	
	private Date fromDate;

	private Date toDate;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
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
		return "AgentReportRequest [ticket=" + ticket + ", agents=" + agents + ", agent=" + agent + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}
	
	
}
