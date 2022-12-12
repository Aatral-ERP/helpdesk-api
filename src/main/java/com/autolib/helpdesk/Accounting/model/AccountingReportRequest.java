package com.autolib.helpdesk.Accounting.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.Vendor;
import com.autolib.helpdesk.Institutes.model.Institute;

public class AccountingReportRequest {
	
	public  AccountingReportRequest()
	{
		
	}
	private String createdBy;
	private String mode;
	private List<AccountCategory> category;
	private String subject;
	private String type;
	private String name;
	private List<Agent> agents;
	private List<Agent> createAgents;
	private List<Vendor> vendors;
	private List<Institute> institutes;
	private String amount;
	private Date paymentDate=null;
;	private Date paymentDateFrom = null;
	private Date paymentDateTo = null;
	
	
	
	
	public List<AccountCategory> getCategory() {
		return category;
	}
	public void setCategory(List<AccountCategory> category) {
		this.category = category;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Date getPaymentDateFrom() {
		return paymentDateFrom;
	}
	public void setPaymentDateFrom(Date paymentDateFrom) {
		this.paymentDateFrom = paymentDateFrom;
	}
	public Date getPaymentDateTo() {
		return paymentDateTo;
	}
	public void setPaymentDateTo(Date paymentDateTo) {
		this.paymentDateTo = paymentDateTo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List<Agent> getAgents() {
		return agents;
	}
	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	public List<Agent> getCreateAgents() {
		return createAgents;
	}
	public void setCreateAgents(List<Agent> createAgents) {
		this.createAgents = createAgents;
	}
	public List<Vendor> getVendors() {
		return vendors;
	}
	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}
	public List<Institute> getInstitutes() {
		return institutes;
	}
	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}
	@Override
	public String toString() {
		return "AccountingReportRequest [createdBy=" + createdBy + ", mode=" + mode + ", category=" + category
				+ ", subject=" + subject + ", type=" + type + ", name=" + name + ", agents=" + agents
				+ ", createAgents=" + createAgents + ", vendors=" + vendors + ", institutes=" + institutes + ", amount="
				+ amount + ", paymentDate=" + paymentDate + ", paymentDateFrom=" + paymentDateFrom + ", paymentDateTo="
				+ paymentDateTo + "]";
	}
	
	
	

}
