package com.autolib.helpdesk.schedulers.model;

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
@Table(name = "lead_mail_sent_status", indexes = { @Index(name = "lead_id_idx", columnList = "lead_id", unique = false),
		@Index(name = "template_id_idx", columnList = "template_id", unique = false) })
public class LeadMailSentStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "lead_id", nullable = false)
	private int leadId = 0;
	@Column(name = "template_id", nullable = false)
	private int templateId = 0;

	@Column
	private String mailTo = "";

	@Column
	private String mailCC = "";

	@Column
	private String subject = "";

	@Lob
	@Column
	private String message = "";

	@Column
	private String files = "";

	@Column
	private String status = "";

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

	public int getLeadId() {
		return leadId;
	}

	public void setLeadId(int leadId) {
		this.leadId = leadId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCC() {
		return mailCC;
	}

	public void setMailCC(String mailCC) {
		this.mailCC = mailCC;
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

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	@Override
	public String toString() {
		return "LeadMailSentStatus [id=" + id + ", leadId=" + leadId + ", templateId=" + templateId + ", mailTo="
				+ mailTo + ", mailCC=" + mailCC + ", subject=" + subject + ", message=" + message + ", files=" + files
				+ ", status=" + status + ", createddatetime=" + createddatetime + "]";
	}

}
