package com.autolib.helpdesk.LeadManagement.repository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.LeadManagement.model.Lead;
import com.autolib.helpdesk.LeadManagement.model.LeadMeeting;

public class LeadActivityReportResponse {
	
	public LeadActivityReportResponse(LeadMeeting leadMeeting,Lead lead,Agent agent){
		
		this.lead=lead;
		this.leadMeeting=leadMeeting;
		this.agent=agent;
		
	}
	
	private Lead lead;
	
	private Agent agent;
	
	private LeadMeeting leadMeeting;

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public LeadMeeting getLeadMeeting() {
		return leadMeeting;
	}

	public void setLeadMeeting(LeadMeeting leadMeeting) {
		this.leadMeeting = leadMeeting;
	}

	@Override
	public String toString() {
		return "LeadActivityReportResponse [lead=" + lead + ", agent=" + agent + ", leadMeeting=" + leadMeeting + "]";
	}
	
	
	

}
