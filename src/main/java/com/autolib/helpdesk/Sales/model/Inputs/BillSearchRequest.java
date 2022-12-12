package com.autolib.helpdesk.Sales.model.Inputs;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.Vendor;

public class BillSearchRequest {

	private List<BillProducts> billProducts;
	private List<Vendor> vendors;
	private List<Agent> agents;

	private String billNo;
	private String orderNo;

	private Date billModifiedDateFrom;
	private Date billModifiedDateTo;
	private Date billDateFrom;
	private Date billDateTo;
	private Date dueDateFrom;
	private Date dueDateTo;

	public BillSearchRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<BillProducts> getBillProducts() {
		return billProducts;
	}

	public void setBillProducts(List<BillProducts> billProducts) {
		this.billProducts = billProducts;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getBillModifiedDateFrom() {
		return billModifiedDateFrom;
	}

	public void setBillModifiedDateFrom(Date billModifiedDateFrom) {
		this.billModifiedDateFrom = billModifiedDateFrom;
	}

	public Date getBillModifiedDateTo() {
		return billModifiedDateTo;
	}

	public void setBillModifiedDateTo(Date billModifiedDateTo) {
		this.billModifiedDateTo = billModifiedDateTo;
	}

	public Date getBillDateFrom() {
		return billDateFrom;
	}

	public void setBillDateFrom(Date billDateFrom) {
		this.billDateFrom = billDateFrom;
	}

	public Date getBillDateTo() {
		return billDateTo;
	}

	public void setBillDateTo(Date billDateTo) {
		this.billDateTo = billDateTo;
	}

	public Date getDueDateFrom() {
		return dueDateFrom;
	}

	public void setDueDateFrom(Date dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	public Date getDueDateTo() {
		return dueDateTo;
	}

	public void setDueDateTo(Date dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	@Override
	public String toString() {
		return "BillSearchRequest [billProducts=" + billProducts + ", vendors=" + vendors + ", agents=" + agents
				+ ", billNo=" + billNo + ", orderNo=" + orderNo + ", billModifiedDateFrom=" + billModifiedDateFrom
				+ ", billModifiedDateTo=" + billModifiedDateTo + ", billDateFrom=" + billDateFrom + ", billDateTo="
				+ billDateTo + ", dueDateFrom=" + dueDateFrom + ", dueDateTo=" + dueDateTo + "]";
	}

}
