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
@Table(name = "deal_purchase_orders", indexes = {
		@Index(name = "purchase_order_no_idx", columnList = "purchase_order_no"),
		@Index(name = "purchase_order_date_idx", columnList = "purchase_order_date"),
		@Index(name = "due_date_idx", columnList = "due_date"),
		@Index(name = "requisition_no_idx", columnList = "requisition_no"),
		@Index(name = "tracking_no_idx", columnList = "tracking_no"),
		@Index(name = "status_idx", columnList = "status"), @Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealPurchaseOrder {

	/**
	 * 
	 */
	public DealPurchaseOrder() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "purchase_order_no", unique = true, nullable = false)
	private String purchaseOrderNo = "";

	@Column(name = "deal_id", nullable = false, updatable = false, unique = true)
	private int dealId;

	@Lob
	@Column
	private String subject;

	@Column(name = "requisition_no")
	private String requisitionNo;

	@Column(name = "tracking_no")
	private String trackingNo;

	@Column
	private String status;

	@Column(name = "excise_duty")
	private Double exciseDuty;

	@Column(name = "sale_commission")
	private Double salesCommission;

	@Column(name = "purchase_order_date")
	@Temporal(TemporalType.DATE)
	private Date purchaseOrderDate;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

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

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
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

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
		return "DealPurchaseOrder [id=" + id + ", purchaseOrderNo=" + purchaseOrderNo + ", dealId=" + dealId
				+ ", subject=" + subject + ", requisitionNo=" + requisitionNo + ", trackingNo=" + trackingNo
				+ ", status=" + status + ", exciseDuty=" + exciseDuty + ", salesCommission=" + salesCommission
				+ ", purchaseOrderDate=" + purchaseOrderDate + ", dueDate=" + dueDate + ", terms=" + terms
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", filename=" + filename
				+ ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
