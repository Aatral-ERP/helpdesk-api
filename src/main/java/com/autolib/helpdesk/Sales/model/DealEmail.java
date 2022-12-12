package com.autolib.helpdesk.Sales.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "deal_emails")
public class DealEmail {

	public DealEmail() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Lob
	@Column
	private String subject;

	@Lob
	@Column
	private String message;

	@Column(name = "deal_id")
	private int dealId;

	@Column
	private String tab;

	@Column
	private String filename;

	@Column(columnDefinition = "smallint(6) default '0'")
	private int sent;

	@Column(name = "mail_ids")
	private String mailIds;

	@Column(name = "mail_id_cc", columnDefinition = "varchar(512)")
	private String mailIdCC = "";

	@Column(name = "created_by")
	private String createdBy = "";

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getMailIds() {
		return this.mailIds;
	}

	public void setMailIds(String mailIds) {
		this.mailIds = mailIds;
	}

	public String getMailIdCC() {
		return mailIdCC;
	}

	public void setMailIdCC(String mailIdCC) {
		this.mailIdCC = mailIdCC;
	}

	public int getSent() {
		return sent;
	}

	public void setSent(int sent) {
		this.sent = sent;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "DealEmail [id=" + id + ", subject=" + subject + ", message=" + message + ", dealId=" + dealId + ", tab="
				+ tab + ", filename=" + filename + ", sent=" + sent + ", mailIds=" + mailIds + ", mailIdCC=" + mailIdCC
				+ ", createdBy=" + createdBy + ", createddatetime=" + createddatetime + "]";
	}

}
