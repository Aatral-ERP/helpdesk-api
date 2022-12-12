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
@Table(name = "deal_quotations", indexes = { @Index(name = "quote_no_idx", columnList = "quote_no"),
		@Index(name = "quote_date_idx", columnList = "quote_date"),
		@Index(name = "valid_until_idx", columnList = "valid_until"),
		@Index(name = "deal_id_idx", columnList = "deal_id"),
		@Index(name = "createddatetime_idx", columnList = "createddatetime"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "quote_stage_idx", columnList = "quote_stage") })
public class DealQuotation {

	/**
	 * 
	 */
	public DealQuotation() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "quote_no", unique = true, nullable = false)
	private String quoteNo = "";

	@Column(name = "deal_id", nullable = false, updatable = false, unique = true)
	private int dealId;

	@Lob
	@Column
	private String subject;

	@Column(name = "quote_stage", nullable = false)
	private String quoteStage;

	@Column(name = "quote_date")
	@Temporal(TemporalType.DATE)
	private Date quoteDate;
	
	@Column(name = "reference_no", unique = true, nullable = false)
	private String referenceNo = "";
	
	@Column(name = "reference_date")
	@Temporal(TemporalType.DATE)
	private Date referenceDate;

	@Column(name = "valid_until")
	@Temporal(TemporalType.DATE)
	private Date validUntil;

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

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getQuoteStage() {
		return quoteStage;
	}

	public void setQuoteStage(String quoteStage) {
		this.quoteStage = quoteStage;
	}

	public Date getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	@Override
	public String toString() {
		return "DealQuotation [id=" + id + ", quoteNo=" + quoteNo + ", dealId=" + dealId + ", subject=" + subject
				+ ", quoteStage=" + quoteStage + ", quoteDate=" + quoteDate + ", referenceNo=" + referenceNo
				+ ", referenceDate=" + referenceDate + ", validUntil=" + validUntil + ", terms=" + terms
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", filename=" + filename
				+ ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}


}
