package com.autolib.helpdesk.Agents.entity;

import java.util.Date;
import java.util.List;

public class RawMaterialReportRequests {

	public RawMaterialReportRequests() {
		// TODO Auto-generated constructor stub
	}

	private List<Product> products;
	private List<Agent> requestBy;
	private List<Agent> requestTo;

	private Date requestDateFrom;
	private Date requestDateTo;

	private Date approvedDateFrom;
	private Date approvedDateTo;

	private String subject;
	private String status;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Agent> getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(List<Agent> requestBy) {
		this.requestBy = requestBy;
	}

	public List<Agent> getRequestTo() {
		return requestTo;
	}

	public void setRequestTo(List<Agent> requestTo) {
		this.requestTo = requestTo;
	}

	public Date getRequestDateFrom() {
		return requestDateFrom;
	}

	public void setRequestDateFrom(Date requestDateFrom) {
		this.requestDateFrom = requestDateFrom;
	}

	public Date getRequestDateTo() {
		return requestDateTo;
	}

	public void setRequestDateTo(Date requestDateTo) {
		this.requestDateTo = requestDateTo;
	}

	public Date getApprovedDateFrom() {
		return approvedDateFrom;
	}

	public void setApprovedDateFrom(Date approvedDateFrom) {
		this.approvedDateFrom = approvedDateFrom;
	}

	public Date getApprovedDateTo() {
		return approvedDateTo;
	}

	public void setApprovedDateTo(Date approvedDateTo) {
		this.approvedDateTo = approvedDateTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RawMaterialReportRequests [products=" + products + ", requestBy=" + requestBy + ", requestTo="
				+ requestTo + ", requestDateFrom=" + requestDateFrom + ", requestDateTo=" + requestDateTo
				+ ", approvedDateFrom=" + approvedDateFrom + ", approvedDateTo=" + approvedDateTo + ", subject="
				+ subject + ", status=" + status + "]";
	}

}
