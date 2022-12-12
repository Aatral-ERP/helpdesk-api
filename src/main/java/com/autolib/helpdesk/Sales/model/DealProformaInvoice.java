/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_proforma_invoices", indexes = {
		@Index(name = "proforma_invoice_no_idx", columnList = "proforma_invoice_no"),
		@Index(name = "invoice_date_idx", columnList = "invoice_date"),
		@Index(name = "due_date_idx", columnList = "due_date"),
		@Index(name = "invoice_status_idx", columnList = "invoice_status"),
		@Index(name = "createddatetime_idx", columnList = "createddatetime"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealProformaInvoice {

	/**
	 * 
	 */
	public DealProformaInvoice() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "proforma_invoice_no", unique = true, nullable = false)
	private String proformaInvoiceNo = "";

	@Column(name = "purchase_order_no")
	private String purchaseOrderNo = "";

	@Column(name = "sales_order_no")
	private String salesOrderNo = "";

	@Column(name = "deal_id", nullable = false, updatable = false, unique = true)
	private int dealId;

	@Lob
	@Column
	private String subject;

	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "invoice_status")
	private String invoiceStatus = "";

	@Column(name = "excise_duty")
	private Double exciseDuty;

	@Column(name = "sale_commission")
	private Double salesCommission;

	@Column(name = "paid_amount")
	private Double paidAmount;

	@Column(name = "shipping_cost")
	private Double shippingCost;

	@Lob
	@Column(name = "terms")
	private String terms = "";

	@Column(name = "created_by")
	private String createdBy = "";

	@Column(name = "modified_by")
	private String modifiedBy = "";

	@Column(name = "filename")
	private String filename = "";

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProformaInvoiceNo() {
		return proformaInvoiceNo;
	}

	public void setProformaInvoiceNo(String proformaInvoiceNo) {
		this.proformaInvoiceNo = proformaInvoiceNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Double getExciseDuty() {
		return exciseDuty;
	}

	public void setExciseDuty(Double exciseDuty) {
		this.exciseDuty = exciseDuty;
	}

	public Double getSalesCommission() {
		return salesCommission;
	}

	public void setSalesCommission(Double salesCommission) {
		this.salesCommission = salesCommission;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	@Override
	public String toString() {
		return "DealInvoice [id=" + id + ", proformaInvoiceNo=" + proformaInvoiceNo + ", purchaseOrderNo="
				+ purchaseOrderNo + ", salesOrderNo=" + salesOrderNo + ", dealId=" + dealId + ", subject=" + subject
				+ ", invoiceDate=" + invoiceDate + ", dueDate=" + dueDate + ", invoiceStatus=" + invoiceStatus
				+ ", exciseDuty=" + exciseDuty + ", salesCommission=" + salesCommission + ", paidAmount=" + paidAmount
				+ ", shippingCost=" + shippingCost + ", terms=" + terms + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", filename=" + filename + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
