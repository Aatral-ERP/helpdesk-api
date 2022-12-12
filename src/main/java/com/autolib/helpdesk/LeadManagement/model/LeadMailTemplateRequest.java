package com.autolib.helpdesk.LeadManagement.model;

public class LeadMailTemplateRequest extends LeadMailTemplate {

	private LeadMailTemplate leadMailTemplate;

	private String directoryName;

	public LeadMailTemplate getLeadMailTemplate() {
		return leadMailTemplate;
	}

	public void setLeadMailTemplate(LeadMailTemplate leadMailTemplate) {
		this.leadMailTemplate = leadMailTemplate;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

}
