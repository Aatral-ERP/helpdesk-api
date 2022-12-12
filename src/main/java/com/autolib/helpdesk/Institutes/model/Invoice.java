/**
 * 
 */
package com.autolib.helpdesk.Institutes.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "invoices")
public class Invoice {

	/**
	 * 
	 */
	public Invoice() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "invoice_no", length = 128)
	private String invoiceNo;
	@Column(name = "reference_no")
	private String referenceNo;
	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	@Column(name = "gst_amount")
	private int gstAmount;
	@Column(name = "gst_percentage")
	private int gstPercentage;
	@Column(name = "total_amount")
	private Double totalAmount;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(int gstAmount) {
		this.gstAmount = gstAmount;
	}

	public int getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(int gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
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

	@Override
	public String toString() {
		return "Invoice [invoiceNo=" + invoiceNo + ", referenceNo=" + referenceNo + ", invoiceDate=" + invoiceDate
				+ ", gstAmount=" + gstAmount + ", gstPercentage=" + gstPercentage + ", totalAmount=" + totalAmount
				+ ", institute=" + institute + ", createddatetime=" + createddatetime + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
