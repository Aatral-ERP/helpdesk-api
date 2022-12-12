package com.autolib.helpdesk.Sales.model.Inputs;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.Vendor;

public class PurchaseInputOrderRequest {

	public PurchaseInputOrderRequest() {
		// TODO Auto-generated constructor stub
	}

	private PurchaseInputOrders purchaseInputOrder;

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

	private String templateName = "";
	private boolean addRoundSeal = false;
	private boolean addFullSeal = false;
	private boolean addSign = false;
	private String signatureBy = "";
	private String designation = "";
	private String fileAsBase64 = "";
	private String filename = "";
	private String receiptContent = "";

	public PurchaseInputOrders getPurchaseInputOrder() {
		return purchaseInputOrder;
	}

	public void setPurchaseInputOrder(PurchaseInputOrders purchaseInputOrder) {
		this.purchaseInputOrder = purchaseInputOrder;
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

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean getAddRoundSeal() {
		return addRoundSeal;
	}

	public void setAddRoundSeal(boolean addRoundSeal) {
		this.addRoundSeal = addRoundSeal;
	}

	public boolean getAddFullSeal() {
		return addFullSeal;
	}

	public void setAddFullSeal(boolean addFullSeal) {
		this.addFullSeal = addFullSeal;
	}

	public boolean getAddSign() {
		return addSign;
	}

	public void setAddSign(boolean addSign) {
		this.addSign = addSign;
	}

	public String getSignatureBy() {
		return signatureBy;
	}

	public void setSignatureBy(String signatureBy) {
		this.signatureBy = signatureBy;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFileAsBase64() {
		return fileAsBase64;
	}

	public void setFileAsBase64(String fileAsBase64) {
		this.fileAsBase64 = fileAsBase64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getReceiptContent() {
		return receiptContent;
	}

	public void setReceiptContent(String receiptContent) {
		this.receiptContent = receiptContent;
	}

	@Override
	public String toString() {
		return "PurchaseInputOrderRequest [purchaseInputOrder=" + purchaseInputOrder + ", purchaseInputOrderProducts="
				+ purchaseInputOrderProducts + ", vendors=" + vendors + ", agents=" + agents + ", referenceNo="
				+ referenceNo + ", orderNo=" + orderNo + ", orderModifiedDateFrom=" + orderModifiedDateFrom
				+ ", orderModifiedDateTo=" + orderModifiedDateTo + ", orderDateFrom=" + orderDateFrom + ", orderDateTo="
				+ orderDateTo + ", expectedDeliveryDateFrom=" + expectedDeliveryDateFrom + ", expectedDeliveryDateTo="
				+ expectedDeliveryDateTo + ", templateName=" + templateName + ", addRoundSeal=" + addRoundSeal
				+ ", addFullSeal=" + addFullSeal + ", addSign=" + addSign + ", signatureBy=" + signatureBy
				+ ", designation=" + designation + ", fileAsBase64=" + fileAsBase64 + ", filename=" + filename
				+ ", receiptContent=" + receiptContent + "]";
	}

}
