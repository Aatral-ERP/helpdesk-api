package com.autolib.helpdesk.LeadManagement.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

public class LeadActivityReportRequest {
	
	public LeadActivityReportRequest() {
		
	}
	
	private List<Lead> lead;
	
	private List<Agent> agent;
	
	private Date fromDateTime;
	
	private Date toDateTime;
	
	private Date lastUpdateFromDate;
	
	private Date lastUpdateToDate;
	
	private Date createdFromDate;
	
	private Date createdToDate;

	public List<Lead> getLead() {
		return lead;
	}

	public void setLead(List<Lead> lead) {
		this.lead = lead;
	}

	public List<Agent> getAgent() {
		return agent;
	}

	public void setAgent(List<Agent> agent) {
		this.agent = agent;
	}

	public Date getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(Date fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public Date getToDateTime() {
		return toDateTime;
	}

	public void setToDateTime(Date toDateTime) {
		this.toDateTime = toDateTime;
	}

	public Date getLastUpdateFromDate() {
		return lastUpdateFromDate;
	}

	public void setLastUpdateFromDate(Date lastUpdateFromDate) {
		this.lastUpdateFromDate = lastUpdateFromDate;
	}

	public Date getLastUpdateToDate() {
		return lastUpdateToDate;
	}

	public void setLastUpdateToDate(Date lastUpdateToDate) {
		this.lastUpdateToDate = lastUpdateToDate;
	}

	public Date getCreatedFromDate() {
		return createdFromDate;
	}

	public void setCreatedFromDate(Date createdFromDate) {
		this.createdFromDate = createdFromDate;
	}

	public Date getCreatedToDate() {
		return createdToDate;
	}

	public void setCreatedToDate(Date createdToDate) {
		this.createdToDate = createdToDate;
	}

	@Override
	public String toString() {
		return "LeadActivityReportRequest [lead=" + lead + ", agent=" + agent + ", fromDateTime=" + fromDateTime
				+ ", toDateTime=" + toDateTime + ", lastUpdateFromDate=" + lastUpdateFromDate + ", lastUpdateToDate="
				+ lastUpdateToDate + ", createdFromDate=" + createdFromDate + ", createdToDate=" + createdToDate + "]";
	}

	

}
