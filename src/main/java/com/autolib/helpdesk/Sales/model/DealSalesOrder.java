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
@Table(name = "deal_sales_order", indexes = { @Index(name = "sales_order_no_idx", columnList = "sales_order_no"),
		@Index(name = "due_date_idx", columnList = "due_date"), @Index(name = "deal_id_idx", columnList = "deal_id"),
		@Index(name = "createddatetime_idx", columnList = "createddatetime"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "status_idx", columnList = "status"), })
public class DealSalesOrder {

	/**
	 * 
	 */
	public DealSalesOrder() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "sales_order_no", unique = true, nullable = false)
	private String salesOrderNo = "";

	@Column(name = "purchase_order_no")
	private String purchaseOrderNo = "";

	@Column(name = "deal_id", nullable = false, updatable = false, unique = true)
	private int dealId;

	@Lob
	@Column
	private String subject;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column
	private String status = "";

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

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "DealSalesOrder [id=" + id + ", salesOrderNo=" + salesOrderNo + ", purchaseOrderNo=" + purchaseOrderNo
				+ ", dealId=" + dealId + ", subject=" + subject + ", dueDate=" + dueDate + ", status=" + status
				+ ", exciseDuty=" + exciseDuty + ", salesCommission=" + salesCommission + ", paidAmount=" + paidAmount
				+ ", shippingCost=" + shippingCost + ", terms=" + terms + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", filename=" + filename + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
