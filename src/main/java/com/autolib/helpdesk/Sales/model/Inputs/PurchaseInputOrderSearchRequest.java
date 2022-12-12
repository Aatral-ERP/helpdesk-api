package com.autolib.helpdesk.Sales.model.Inputs;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.Vendor;

public class PurchaseInputOrderSearchRequest {

	private List<PurchaseInputOrderProduct> purchaseInputOrderProducts;
	private List<Vendor> vendors;
	private List<Agent> agents;

	private String referenceNo;
	private String orderNo;

	private Date orderModifiedDateFrom;
	private Date orderModifiedDateTo;
	private Date orderDateFrom;
	private Date orderDateTo;
	private Date expectedDeliveryDateFrom;
	private Date expectedDeliveryDateTo;

	public PurchaseInputOrderSearchRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<PurchaseInputOrderProduct> getPurchaseInputOrderProducts() {
		return purchaseInputOrderProducts;
	}

	public void setPurchaseInputOrderProducts(List<PurchaseInputOrderProduct> purchaseInputOrderProducts) {
		this.purchaseInputOrderProducts = purchaseInputOrderProducts;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderModifiedDateFrom() {
		return orderModifiedDateFrom;
	}

	public void setOrderModifiedDateFrom(Date orderModifiedDateFrom) {
		this.orderModifiedDateFrom = orderModifiedDateFrom;
	}

	public Date getOrderModifiedDateTo() {
		return orderModifiedDateTo;
	}

	public void setOrderModifiedDateTo(Date orderModifiedDateTo) {
		this.orderModifiedDateTo = orderModifiedDateTo;
	}

	public Date getOrderDateFrom() {
		return orderDateFrom;
	}

	public void setOrderDateFrom(Date orderDateFrom) {
		this.orderDateFrom = orderDateFrom;
	}

	public Date getOrderDateTo() {
		return orderDateTo;
	}

	public void setOrderDateTo(Date orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

	public Date getExpectedDeliveryDateFrom() {
		return expectedDeliveryDateFrom;
	}

	public void setExpectedDeliveryDateFrom(Date expectedDeliveryDateFrom) {
		this.expectedDeliveryDateFrom = expectedDeliveryDateFrom;
	}

	public Date getExpectedDeliveryDateTo() {
		return expectedDeliveryDateTo;
	}

	public void setExpectedDeliveryDateTo(Date expectedDeliveryDateTo) {
		this.expectedDeliveryDateTo = expectedDeliveryDateTo;
	}

	@Override
	public String toString() {
		return "PurchaseInputOrderSearchRequest [purchaseInputOrderProducts=" + purchaseInputOrderProducts
				+ ", vendors=" + vendors + ", agents=" + agents + ", referenceNo=" + referenceNo + ", orderNo="
				+ orderNo + ", orderModifiedDateFrom=" + orderModifiedDateFrom + ", orderModifiedDateTo="
				+ orderModifiedDateTo + ", orderDateFrom=" + orderDateFrom + ", orderDateTo=" + orderDateTo
				+ ", expectedDeliveryDateFrom=" + expectedDeliveryDateFrom + ", expectedDeliveryDateTo="
				+ expectedDeliveryDateTo + "]";
	}

}
