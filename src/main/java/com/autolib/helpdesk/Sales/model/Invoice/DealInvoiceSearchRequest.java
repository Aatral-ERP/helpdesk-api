package com.autolib.helpdesk.Sales.model.Invoice;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.Institute;

public class DealInvoiceSearchRequest {

	public DealInvoiceSearchRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<DealInvoiceProducts> dealInvoiceProducts;
	private List<Institute> institutes;
	private List<Agent> agents;

	private Date dealCreatedDateFrom;
	private Date dealCreatedDateTo;

	private String dealType = "";
	private int dealId = 0;

	private Date dealModifiedDateFrom;
	private Date dealModifiedDateTo;

	private String invoiceNo = "";
	private String poNo = "";
	private String soNo = "";
	private String invoiceSubject = "";
	private Date invoiceDueDateFrom = null;
	private Date invoiceDueDateTo = null;
	private Date invoiceDateFrom = null;
	private Date invoiceDateTo = null;
	private String invoiceStatus = "";
	private String gstYear = "";
	private String gstMonth = "";
	private String registered = "";
	private String paidStatus = "";

	private String paymentReferenceNo = "";
	private String paymentReceiptNo = "";
	private String paymentSubject = "";
	private String paymentMode = "";
	private Date paymentDateFrom = null;
	private Date paymentDateTo = null;
	private String paymentDrawnOn = "";
	private String paymentAmount = "";

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public String getPaymentReferenceNo() {
		return paymentReferenceNo;
	}

	public void setPaymentReferenceNo(String paymentReferenceNo) {
		this.paymentReferenceNo = paymentReferenceNo;
	}

	public String getPaymentReceiptNo() {
		return paymentReceiptNo;
	}

	public void setPaymentReceiptNo(String paymentReceiptNo) {
		this.paymentReceiptNo = paymentReceiptNo;
	}

	public String getPaymentSubject() {
		return paymentSubject;
	}

	public void setPaymentSubject(String paymentSubject) {
		this.paymentSubject = paymentSubject;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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

	public String getPaymentDrawnOn() {
		return paymentDrawnOn;
	}

	public void setPaymentDrawnOn(String paymentDrawnOn) {
		this.paymentDrawnOn = paymentDrawnOn;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public List<DealInvoiceProducts> getDealInvoiceProducts() {
		return dealInvoiceProducts;
	}

	public void setDealInvoiceProducts(List<DealInvoiceProducts> dealInvoiceProducts) {
		this.dealInvoiceProducts = dealInvoiceProducts;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public Date getDealCreatedDateFrom() {
		return dealCreatedDateFrom;
	}

	public void setDealCreatedDateFrom(Date dealCreatedDateFrom) {
		this.dealCreatedDateFrom = dealCreatedDateFrom;
	}

	public Date getDealCreatedDateTo() {
		return dealCreatedDateTo;
	}

	public void setDealCreatedDateTo(Date dealCreatedDateTo) {
		this.dealCreatedDateTo = dealCreatedDateTo;
	}

	public Date getDealModifiedDateFrom() {
		return dealModifiedDateFrom;
	}

	public void setDealModifiedDateFrom(Date dealModifiedDateFrom) {
		this.dealModifiedDateFrom = dealModifiedDateFrom;
	}

	public Date getDealModifiedDateTo() {
		return dealModifiedDateTo;
	}

	public void setDealModifiedDateTo(Date dealModifiedDateTo) {
		this.dealModifiedDateTo = dealModifiedDateTo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public String getInvoiceSubject() {
		return invoiceSubject;
	}

	public void setInvoiceSubject(String invoiceSubject) {
		this.invoiceSubject = invoiceSubject;
	}

	public Date getInvoiceDueDateFrom() {
		return invoiceDueDateFrom;
	}

	public void setInvoiceDueDateFrom(Date invoiceDueDateFrom) {
		this.invoiceDueDateFrom = invoiceDueDateFrom;
	}

	public Date getInvoiceDueDateTo() {
		return invoiceDueDateTo;
	}

	public void setInvoiceDueDateTo(Date invoiceDueDateTo) {
		this.invoiceDueDateTo = invoiceDueDateTo;
	}

	public Date getInvoiceDateFrom() {
		return invoiceDateFrom;
	}

	public void setInvoiceDateFrom(Date invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}

	public Date getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(Date invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getGstYear() {
		return gstYear;
	}

	public void setGstYear(String gstYear) {
		this.gstYear = gstYear;
	}

	public String getGstMonth() {
		return gstMonth;
	}

	public void setGstMonth(String gstMonth) {
		this.gstMonth = gstMonth;
	}

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

	@Override
	public String toString() {
		return "DealInvoiceSearchRequest [dealInvoiceProducts=" + dealInvoiceProducts + ", institutes=" + institutes
				+ ", agents=" + agents + ", dealCreatedDateFrom=" + dealCreatedDateFrom + ", dealCreatedDateTo="
				+ dealCreatedDateTo + ", dealType=" + dealType + ", dealModifiedDateFrom=" + dealModifiedDateFrom
				+ ", dealModifiedDateTo=" + dealModifiedDateTo + ", invoiceNo=" + invoiceNo + ", poNo=" + poNo
				+ ", soNo=" + soNo + ", invoiceSubject=" + invoiceSubject + ", invoiceDueDateFrom=" + invoiceDueDateFrom
				+ ", invoiceDueDateTo=" + invoiceDueDateTo + ", invoiceDateFrom=" + invoiceDateFrom + ", invoiceDateTo="
				+ invoiceDateTo + ", invoiceStatus=" + invoiceStatus + ", gstYear=" + gstYear + ", gstMonth=" + gstMonth
				+ ", registered=" + registered + ", paidStatus=" + paidStatus + ", paymentReferenceNo="
				+ paymentReferenceNo + ", paymentReceiptNo=" + paymentReceiptNo + ", paymentSubject=" + paymentSubject
				+ ", paymentMode=" + paymentMode + ", paymentDateFrom=" + paymentDateFrom + ", paymentDateTo="
				+ paymentDateTo + ", paymentDrawnOn=" + paymentDrawnOn + ", paymentAmount=" + paymentAmount + "]";
	}

}
