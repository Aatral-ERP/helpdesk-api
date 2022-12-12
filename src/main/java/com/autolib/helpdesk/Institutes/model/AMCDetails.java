package com.autolib.helpdesk.Institutes.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "amc_details", uniqueConstraints = {
		@UniqueConstraint(name = "uc_amcid_product", columnNames = { "amc_id", "product_id" }) })
public class AMCDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(name = "amc_id", nullable = false, length = 128)
	private String amcId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@Column(nullable = false)
	private String title;

	@Column(name = "amc_amount", nullable = false, precision = 10, scale = 2)
	private Double amcAmount;

	@Column(name = "product_id", nullable = false)
	private int product;

	@Column(name = "service_type", nullable = false)
	private String serviceType;

	@Column(nullable = false, precision = 10, scale = 2)
	private Double gst;

	@Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
	private Double totalAmount;

	@Column(name = "from_date", nullable = false)
	private Date fromDate;

	@Column(name = "to_date", nullable = false)
	private Date toDate;

	@Column(name = "paid_date", nullable = false)
	private Date paidDate;

	@Column(name = "inv_date", nullable = false)
	private Date invDate;

	@Column(name = "pay_mode", nullable = false)
	private String payMode;

	@Column(name = "transactions_details")
	private String transactionDetails;

	@Column(name = "receipt_file_name")
	private String receiptfileName;

	@Lob
	@Column
	private String description;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public AMCDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAmcId() {
		return amcId;
	}

	public void setAmcId(String amcId) {
		this.amcId = amcId;
	}

	/*
	 * public String getamcId() { return amcId; }
	 * 
	 * public void setamcId(String aMCId) { amcId = aMCId; }
	 */

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getAmcAmount() {
		return amcAmount;
	}

	public void setAmcAmount(Double amcAmount) {
		this.amcAmount = amcAmount;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

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

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getReceiptfileName() {
		return receiptfileName;
	}

	public void setReceiptfileName(String receiptfileName) {
		this.receiptfileName = receiptfileName;
	}

	@Override
	public String toString() {
		return "AMCDetails [id=" + id + ", amcId=" + amcId + ", institute=" + institute + ", title=" + title
				+ ", amcAmount=" + amcAmount + ", product=" + product + ", serviceType=" + serviceType + ", gst=" + gst
				+ ", totalAmount=" + totalAmount + ", fromDate=" + fromDate + ", toDate=" + toDate + ", paidDate="
				+ paidDate + ", invDate=" + invDate + ", payMode=" + payMode + ", transactionDetails="
				+ transactionDetails + ", receiptfileName=" + receiptfileName + ", description=" + description
				+ ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
