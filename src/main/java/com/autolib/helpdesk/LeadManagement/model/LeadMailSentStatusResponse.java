package com.autolib.helpdesk.LeadManagement.model;

import com.autolib.helpdesk.schedulers.model.LeadMailSentStatus;

public class LeadMailSentStatusResponse {

	private Lead lead;

	private LeadMailSentStatus status;

	public LeadMailSentStatusResponse(Lead lead, LeadMailSentStatus status) {
		super();
		this.lead = lead;
		this.status = status;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public LeadMailSentStatus getStatus() {
		return status;
	}

	public void setStatus(LeadMailSentStatus status) {
		this.status = status;
	}

}
