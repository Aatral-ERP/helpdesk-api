package com.autolib.helpdesk.LeadManagement.model;

import java.util.List;

public class LeadMailTemplateSearchRequest {

	private String title;
	private String subject;
	private String message;
	private List<String> status;
	private List<String> frequency;
	private List<String> categories;
	private List<String> states;
	private List<String> industryTypes;

	public List<String> getIndustryTypes() {
		return industryTypes;
	}

	public void setIndustryTypes(List<String> industryTypes) {
		this.industryTypes = industryTypes;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<String> frequency) {
		this.frequency = frequency;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LeadMailTemplateSearchRequest [title=" + title + ", subject=" + subject + ", message=" + message
				+ ", status=" + status + ", frequency=" + frequency + ", categories=" + categories + ", states="
				+ states + ", industryTypes=" + industryTypes + "]";
	}

}
