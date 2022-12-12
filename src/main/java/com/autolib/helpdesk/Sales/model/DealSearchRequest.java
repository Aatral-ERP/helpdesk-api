/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.Institute;

/**
 * @author Kannadasan
 *
 */
public class DealSearchRequest {

	/**
	 * 
	 */
	public DealSearchRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<DealProducts> dealProducts;

	private List<Institute> institutes;

	private List<Agent> agents;

	private Date dealCreatedDateFrom;
	private Date dealCreatedDateTo;

	private Date dealModifiedDateFrom;
	private Date dealModifiedDateTo;

	private String dealType;

	private int setFrom;
	private int noOfRecords;

	private String quoteNo = "";
	private String quoteSubject = "";
	private Date quoteDateFrom = null;
	private Date quoteDateTo = null;
	private Date quoteValidDateFrom = null;
	private Date quoteValidDateTo = null;
	private String quoteStage = "";

	private String poNo = "";
	private String poTrackingNo = "";
	private String poRequisitionNo = "";
	private String poSubject = "";
	private Date poDateFrom = null;
	private Date poDateTo = null;
	private Date poDueDateFrom = null;
	private Date poDueDateTo = null;
	private String poStatus = "";

	private String soNo = "";
	private String soSubject = "";
	private Date soDueDateFrom = null;
	private Date soDueDateTo = null;
	private String soStatus = "";

	private String proInvoiceNo = "";
	private String proInvoiceSubject = "";
	private Date proInvoiceDueDateFrom = null;
	private Date proInvoiceDueDateTo = null;
	private Date proInvoiceDateFrom = null;
	private Date proInvoiceDateTo = null;

	private String invoiceNo = "";
	private String invoiceSubject = "";
	private Date invoiceDueDateFrom = null;
	private Date invoiceDueDateTo = null;
	private Date invoiceDateFrom = null;
	private Date invoiceDateTo = null;
	private String invoiceStatus = "";
	private String gstYear = "";
	private String gstMonth = "";
	private String registered = "";

	private String paymentReferenceNo = "";
	private String paymentReceiptNo = "";
	private String paymentSubject = "";
	private String paymentMode = "";
	private Date paymentDateFrom = null;
	private Date paymentDateTo = null;
	private String paymentDrawnOn = "";
	private String paymentAmount = "";

	private List<Agent> manufacturingAgents;
	private List<Agent> deliveryAgents;
	private List<Agent> installationAgents;
	private String projectImplementationStatus = "";
	
	private String date = "";
	
	private Date fromDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	private Date toDate;
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProjectImplementationStatus() {
		return projectImplementationStatus;
	}

	public void setProjectImplementationStatus(String projectImplementationStatus) {
		this.projectImplementationStatus = projectImplementationStatus;
	}

	public List<Agent> getManufacturingAgents() {
		return manufacturingAgents;
	}

	public void setManufacturingAgents(List<Agent> manufacturingAgents) {
		this.manufacturingAgents = manufacturingAgents;
	}

	public List<Agent> getDeliveryAgents() {
		return deliveryAgents;
	}

	public void setDeliveryAgents(List<Agent> deliveryAgents) {
		this.deliveryAgents = deliveryAgents;
	}

	public List<Agent> getInstallationAgents() {
		return installationAgents;
	}

	public void setInstallationAgents(List<Agent> installationAgents) {
		this.installationAgents = installationAgents;
	}

	public List<DealProducts> getDealProducts() {
		return dealProducts;
	}

	public void setDealProducts(List<DealProducts> dealProducts) {
		this.dealProducts = dealProducts;
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

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
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

	public int getSetFrom() {
		return setFrom;
	}

	public void setSetFrom(int setFrom) {
		this.setFrom = setFrom;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public String getProInvoiceNo() {
		return proInvoiceNo;
	}

	public void setProInvoiceNo(String proInvoiceNo) {
		this.proInvoiceNo = proInvoiceNo;
	}

	public String getProInvoiceSubject() {
		return proInvoiceSubject;
	}

	public void setProInvoiceSubject(String proInvoiceSubject) {
		this.proInvoiceSubject = proInvoiceSubject;
	}

	public Date getProInvoiceDueDateFrom() {
		return proInvoiceDueDateFrom;
	}

	public void setProInvoiceDueDateFrom(Date proInvoiceDueDateFrom) {
		this.proInvoiceDueDateFrom = proInvoiceDueDateFrom;
	}

	public Date getProInvoiceDueDateTo() {
		return proInvoiceDueDateTo;
	}

	public void setProInvoiceDueDateTo(Date proInvoiceDueDateTo) {
		this.proInvoiceDueDateTo = proInvoiceDueDateTo;
	}

	public Date getProInvoiceDateFrom() {
		return proInvoiceDateFrom;
	}

	public void setProInvoiceDateFrom(Date proInvoiceDateFrom) {
		this.proInvoiceDateFrom = proInvoiceDateFrom;
	}

	public Date getProInvoiceDateTo() {
		return proInvoiceDateTo;
	}

	public void setProInvoiceDateTo(Date proInvoiceDateTo) {
		this.proInvoiceDateTo = proInvoiceDateTo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getQuoteSubject() {
		return quoteSubject;
	}

	public void setQuoteSubject(String quoteSubject) {
		this.quoteSubject = quoteSubject;
	}

	public Date getQuoteDateFrom() {
		return quoteDateFrom;
	}

	public void setQuoteDateFrom(Date quoteDateFrom) {
		this.quoteDateFrom = quoteDateFrom;
	}

	public Date getQuoteDateTo() {
		return quoteDateTo;
	}

	public void setQuoteDateTo(Date quoteDateTo) {
		this.quoteDateTo = quoteDateTo;
	}

	public Date getQuoteValidDateFrom() {
		return quoteValidDateFrom;
	}

	public void setQuoteValidDateFrom(Date quoteValidDateFrom) {
		this.quoteValidDateFrom = quoteValidDateFrom;
	}

	public Date getQuoteValidDateTo() {
		return quoteValidDateTo;
	}

	public void setQuoteValidDateTo(Date quoteValidDateTo) {
		this.quoteValidDateTo = quoteValidDateTo;
	}

	public String getQuoteStage() {
		return quoteStage;
	}

	public void setQuoteStage(String quoteStage) {
		this.quoteStage = quoteStage;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getPoTrackingNo() {
		return poTrackingNo;
	}

	public void setPoTrackingNo(String poTrackingNo) {
		this.poTrackingNo = poTrackingNo;
	}

	public String getPoRequisitionNo() {
		return poRequisitionNo;
	}

	public void setPoRequisitionNo(String poRequisitionNo) {
		this.poRequisitionNo = poRequisitionNo;
	}

	public String getPoSubject() {
		return poSubject;
	}

	public void setPoSubject(String poSubject) {
		this.poSubject = poSubject;
	}

	public Date getPoDateFrom() {
		return poDateFrom;
	}

	public void setPoDateFrom(Date poDateFrom) {
		this.poDateFrom = poDateFrom;
	}

	public Date getPoDateTo() {
		return poDateTo;
	}

	public void setPoDateTo(Date poDateTo) {
		this.poDateTo = poDateTo;
	}

	public Date getPoDueDateFrom() {
		return poDueDateFrom;
	}

	public void setPoDueDateFrom(Date poDueDateFrom) {
		this.poDueDateFrom = poDueDateFrom;
	}

	public Date getPoDueDateTo() {
		return poDueDateTo;
	}

	public void setPoDueDateTo(Date poDueDateTo) {
		this.poDueDateTo = poDueDateTo;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public String getSoSubject() {
		return soSubject;
	}

	public void setSoSubject(String soSubject) {
		this.soSubject = soSubject;
	}

	public Date getSoDueDateFrom() {
		return soDueDateFrom;
	}

	public void setSoDueDateFrom(Date soDueDateFrom) {
		this.soDueDateFrom = soDueDateFrom;
	}

	public Date getSoDueDateTo() {
		return soDueDateTo;
	}

	public void setSoDueDateTo(Date soDueDateTo) {
		this.soDueDateTo = soDueDateTo;
	}

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
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

	@Override
	public String toString() {
		return "DealSearchRequest [dealProducts=" + dealProducts + ", institutes=" + institutes + ", agents=" + agents
				+ ", dealCreatedDateFrom=" + dealCreatedDateFrom + ", dealCreatedDateTo=" + dealCreatedDateTo
				+ ", dealModifiedDateFrom=" + dealModifiedDateFrom + ", dealModifiedDateTo=" + dealModifiedDateTo
				+ ", dealType=" + dealType + ", setFrom=" + setFrom + ", noOfRecords=" + noOfRecords + ", quoteNo="
				+ quoteNo + ", quoteSubject=" + quoteSubject + ", quoteDateFrom=" + quoteDateFrom + ", quoteDateTo="
				+ quoteDateTo + ", quoteValidDateFrom=" + quoteValidDateFrom + ", quoteValidDateTo=" + quoteValidDateTo
				+ ", quoteStage=" + quoteStage + ", poNo=" + poNo + ", poTrackingNo=" + poTrackingNo
				+ ", poRequisitionNo=" + poRequisitionNo + ", poSubject=" + poSubject + ", poDateFrom=" + poDateFrom
				+ ", poDateTo=" + poDateTo + ", poDueDateFrom=" + poDueDateFrom + ", poDueDateTo=" + poDueDateTo
				+ ", poStatus=" + poStatus + ", soNo=" + soNo + ", soSubject=" + soSubject + ", soDueDateFrom="
				+ soDueDateFrom + ", soDueDateTo=" + soDueDateTo + ", soStatus=" + soStatus + ", proInvoiceNo="
				+ proInvoiceNo + ", proInvoiceSubject=" + proInvoiceSubject + ", proInvoiceDueDateFrom="
				+ proInvoiceDueDateFrom + ", proInvoiceDueDateTo=" + proInvoiceDueDateTo + ", proInvoiceDateFrom="
				+ proInvoiceDateFrom + ", proInvoiceDateTo=" + proInvoiceDateTo + ", invoiceNo=" + invoiceNo
				+ ", invoiceSubject=" + invoiceSubject + ", invoiceDueDateFrom=" + invoiceDueDateFrom
				+ ", invoiceDueDateTo=" + invoiceDueDateTo + ", invoiceDateFrom=" + invoiceDateFrom + ", invoiceDateTo="
				+ invoiceDateTo + ", invoiceStatus=" + invoiceStatus + ", gstYear=" + gstYear + ", gstMonth=" + gstMonth
				+ ", registered=" + registered + ", paymentReferenceNo=" + paymentReferenceNo + ", paymentReceiptNo="
				+ paymentReceiptNo + ", paymentSubject=" + paymentSubject + ", paymentMode=" + paymentMode
				+ ", paymentDateFrom=" + paymentDateFrom + ", paymentDateTo=" + paymentDateTo + ", paymentDrawnOn="
				+ paymentDrawnOn + ", paymentAmount=" + paymentAmount + ", manufacturingAgents=" + manufacturingAgents
				+ ", deliveryAgents=" + deliveryAgents + ", installationAgents=" + installationAgents
				+ ", projectImplementationStatus=" + projectImplementationStatus + ", date=" + date + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}

	

}
