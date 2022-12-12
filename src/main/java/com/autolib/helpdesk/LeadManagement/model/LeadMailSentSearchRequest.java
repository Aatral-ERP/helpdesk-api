package com.autolib.helpdesk.LeadManagement.model;

import java.util.Date;
import java.util.List;

public class LeadMailSentSearchRequest {

	private String leadId;
	private String templateId;
	private String mailId;
	private String mailCC;
	private String leadCompany;
	private String leadTitle;
	private Date sendDateFrom;
	private Date sendDateTo;
	private String leadMessage;
	private String leadSubject;

	private List<String> leadStatus;
	private List<String> leadCatogories;
	private List<String> leadStates;
	private List<String> leadSources;
	private List<String> industryTypes;
	private List<String> products;

	public List<String> getLeadSources() {
		return leadSources;
	}

	public void setLeadSources(List<String> leadSources) {
		this.leadSources = leadSources;
	}

	public List<String> getLeadCatogories() {
		return leadCatogories;
	}

	public void setLeadCatogories(List<String> leadCatogories) {
		this.leadCatogories = leadCatogories;
	}

	public List<String> getLeadStates() {
		return leadStates;
	}

	public void setLeadStates(List<String> leadStates) {
		this.leadStates = leadStates;
	}

	public List<String> getIndustryTypes() {
		return industryTypes;
	}

	public void setIndustryTypes(List<String> industryTypes) {
		this.industryTypes = industryTypes;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public String getLeadMessage() {
		return leadMessage;
	}

	public void setLeadMessage(String leadMessage) {
		this.leadMessage = leadMessage;
	}

	public List<String> getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(List<String> leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getLeadSubject() {
		return leadSubject;
	}

	public void setLeadSubject(String leadSubject) {
		this.leadSubject = leadSubject;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailCC() {
		return mailCC;
	}

	public void setMailCC(String mailCC) {
		this.mailCC = mailCC;
	}

	public String getLeadCompany() {
		return leadCompany;
	}

	public void setLeadCompany(String leadCompany) {
		this.leadCompany = leadCompany;
	}

	public String getLeadTitle() {
		return leadTitle;
	}

	public void setLeadTitle(String leadTitle) {
		this.leadTitle = leadTitle;
	}

	public Date getSendDateFrom() {
		return sendDateFrom;
	}

	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}

	public Date getSendDateTo() {
		return sendDateTo;
	}

	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
	}

	@Override
	public String toString() {
		return "LeadMailSentSearchRequest [leadId=" + leadId + ", templateId=" + templateId + ", mailId=" + mailId
				+ ", mailCC=" + mailCC + ", leadCompany=" + leadCompany + ", leadTitle=" + leadTitle + ", sendDateFrom="
				+ sendDateFrom + ", sendDateTo=" + sendDateTo + ", leadMessage=" + leadMessage + ", leadSubject="
				+ leadSubject + ", leadStatus=" + leadStatus + ", leadCatogories=" + leadCatogories + ", leadStates="
				+ leadStates + ", leadSources=" + leadSources + ", industryTypes=" + industryTypes + ", products="
				+ products + "]";
	}
	

}
