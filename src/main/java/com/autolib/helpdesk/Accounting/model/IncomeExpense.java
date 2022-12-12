package com.autolib.helpdesk.Accounting.model;

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

@Entity
@Table(name = "unspecified_incomes_expenses", indexes = {
		@Index(name = "payment_date_idx", columnList = "payment_date"),
		@Index(name = "type_idx", columnList = "type") })
public class IncomeExpense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column
	private String subject;

	@Column
	private String toWhom;

	@Column
	private String type;

	@Column
	private String modeOfPayment;

	@Column(nullable = false)
	private Double amount = 0.00;

	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_date")
	private Date invoiceDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "payment_date", nullable = false)
	private Date paymentDate;

	@Column
	private String referenceno;

	@Column
	private String drawnon;

	@Column
	private String createdBy;

	@Column
	private String category;

	@Column
	private String invoiceNo;

	@Column
	private String modifiedBy;

	@Lob
	@Column
	private String remarks;

	@Column
	private String relatedTo;

	@Column
	private String relatedToAgentId;

	@Column(columnDefinition = "integer default 0", nullable = false)
	private int relatedToSupplierId;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRelatedTo() {
		return relatedTo;
	}

	public void setRelatedTo(String relatedTo) {
		this.relatedTo = relatedTo;
	}

	public String getRelatedToAgentId() {
		return relatedToAgentId;
	}

	public void setRelatedToAgentId(String relatedToAgentId) {
		this.relatedToAgentId = relatedToAgentId;
	}

	public int getRelatedToSupplierId() {
		return relatedToSupplierId;
	}

	public void setRelatedToSupplierId(int relatedToSupplierId) {
		this.relatedToSupplierId = relatedToSupplierId;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public String getDrawnon() {
		return drawnon;
	}

	public void setDrawnon(String drawnon) {
		this.drawnon = drawnon;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getToWhom() {
		return toWhom;
	}

	public void setToWhom(String toWhom) {
		this.toWhom = toWhom;
	}

	@Override
	public String toString() {
		return "IncomeExpense [id=" + id + ", subject=" + subject + ", toWhom=" + toWhom + ", type=" + type
				+ ", modeOfPayment=" + modeOfPayment + ", amount=" + amount + ", invoiceDate=" + invoiceDate
				+ ", paymentDate=" + paymentDate + ", referenceno=" + referenceno + ", drawnon=" + drawnon
				+ ", createdBy=" + createdBy + ", category=" + category + ", invoiceNo=" + invoiceNo + ", modifiedBy="
				+ modifiedBy + ", remarks=" + remarks + ", relatedTo=" + relatedTo + ", relatedToAgentId="
				+ relatedToAgentId + ", relatedToSupplierId=" + relatedToSupplierId + ", createddatetime="
				+ createddatetime + "]";
	}

}
