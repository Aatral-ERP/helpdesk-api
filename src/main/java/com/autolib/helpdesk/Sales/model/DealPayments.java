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
@Table(name = "deal_payments", indexes = { @Index(name = "payment_date_idx", columnList = "payment_date"),
		@Index(name = "deal_id_idx", columnList = "deal_id"),
		@Index(name = "invoice_id_idx", columnList = "invoice_id") })
public class DealPayments {

	/**
	 * 
	 */
	public DealPayments() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id", updatable = false, columnDefinition = "int(11) default 0")
	private int dealId;

	@Column(name = "invoice_id", updatable = false, columnDefinition = "int(11) default 0")
	private int invoiceId;

	@Lob
	@Column
	private String subject;

	@Column
	private String mode;

	@Column
	private String receiptno;

	@Column
	private String referenceno;

	@Column
	private String drawnon;

	@Column
	private String receiptfilename;

	@Column(name = "payment_date")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@Column(nullable = false)
	private Double amount;

	@Column(name = "gst_amount", nullable = false)
	private Double gstAmount;

	@Column(name = "total_amount", nullable = false)
	private Double totalAmount;

	@Lob
	@Column
	private String description = "";

	@Column(name = "created_by")
	private String createdBy = "";

	@Column(name = "modified_by")
	private String modifiedBy = "";

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

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public String getReceiptno() {
		return receiptno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public String getDrawnon() {
		return drawnon;
	}

	public void setDrawnon(String drawnon) {
		this.drawnon = drawnon;
	}

	public String getReceiptfilename() {
		return receiptfilename;
	}

	public void setReceiptfilename(String receiptfilename) {
		this.receiptfilename = receiptfilename;
	}

	@Override
	public String toString() {
		return "DealPayments [id=" + id + ", dealId=" + dealId + ", invoiceId=" + invoiceId + ", subject=" + subject
				+ ", mode=" + mode + ", receiptno=" + receiptno + ", referenceno=" + referenceno + ", drawnon="
				+ drawnon + ", receiptfilename=" + receiptfilename + ", paymentDate=" + paymentDate + ", amount="
				+ amount + ", gstAmount=" + gstAmount + ", totalAmount=" + totalAmount + ", description=" + description
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
