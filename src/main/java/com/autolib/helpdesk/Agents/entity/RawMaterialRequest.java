package com.autolib.helpdesk.Agents.entity;

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

@Entity
@Table(name = "raw_materials_requests", indexes = { @Index(name = "subject_idx", columnList = "subject"),
		@Index(name = "request_by_idx", columnList = "request_by"),
		@Index(name = "request_to_idx", columnList = "request_to"),
		@Index(name = "product_id_idx", columnList = "product_id"), @Index(name = "status_idx", columnList = "status"),
		@Index(name = "request_date_idx", columnList = "request_date"),
		@Index(name = "approved_date_idx", columnList = "approved_date") })
public class RawMaterialRequest {

	public RawMaterialRequest() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(name = "subject")
	private String subject;

	@Column(name = "request_by")
	private String requestBy;

	@Column(name = "request_to")
	private String requestTo;

	@Column
	private String status;

	@Column(name = "product_id")
	private int productId;

	@Column(length = 512)
	private String productName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_date", nullable = false)
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date")
	private Date approvedDate;

	@Lob
	@Column
	private String remarks;

	@Lob
	@Column
	private String approveRejectRemarks;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public String getRequestTo() {
		return requestTo;
	}

	public void setRequestTo(String requestTo) {
		this.requestTo = requestTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApproveRejectRemarks() {
		return approveRejectRemarks;
	}

	public void setApproveRejectRemarks(String approveRejectRemarks) {
		this.approveRejectRemarks = approveRejectRemarks;
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
		return "RawMaterialRequest [id=" + id + ", subject=" + subject + ", requestBy=" + requestBy + ", requestTo="
				+ requestTo + ", status=" + status + ", productId=" + productId + ", productName=" + productName
				+ ", requestDate=" + requestDate + ", approvedDate=" + approvedDate + ", remarks=" + remarks
				+ ", approveRejectRemarks=" + approveRejectRemarks + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
