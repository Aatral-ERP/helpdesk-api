package com.autolib.helpdesk.Tickets.model;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.InstituteContact;

public class TicketRequest {

	public TicketRequest() {
		// TODO Auto-generated constructor stub
	}

	private Ticket ticket;
	private InstituteContact instituteContact;
	private Agent agent;

	public InstituteContact getInstituteContact() {
		return instituteContact;
	}

	public void setInstituteContact(InstituteContact instituteContact) {
		this.instituteContact = instituteContact;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "TicketRequest [ticket=" + ticket + ", instituteContact=" + instituteContact + ", agent=" + agent + "]";
	}

}
