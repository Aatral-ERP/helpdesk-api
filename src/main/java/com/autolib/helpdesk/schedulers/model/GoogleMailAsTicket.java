/**
 * 
 */
package com.autolib.helpdesk.schedulers.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "google_mail_as_tickets")
public class GoogleMailAsTicket {

	/**
	 * 
	 */
	public GoogleMailAsTicket() {
		this.ticketId = 0;
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "ticket_id", nullable = false)
	private int ticketId = 0;
	@Column(name = "id_mail")
	private String idMail;
	@Lob
	@Column
	private String subject;
	@Lob
	@Column
	private String summary;
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;
	@Column
	private String product;
	@Column(name = "email_id", nullable = false)
	private String emailId;
	@Column
	private String emailUpdates;
	@Column
	private String attachments;
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

	public String getIdMail() {
		return idMail;
	}

	public void setIdMail(String idMail) {
		this.idMail = idMail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailUpdates() {
		return emailUpdates;
	}

	public void setEmailUpdates(String emailUpdates) {
		this.emailUpdates = emailUpdates;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return "GoogleMailAsTicket [id=" + id + ", ticketId=" + ticketId + ", idMail=" + idMail + ", subject=" + subject
				+ ", summary=" + summary + ", institute=" + institute + ", product=" + product + ", emailId=" + emailId
				+ ", emailUpdates=" + emailUpdates + ", attachments=" + attachments + ", createddatetime="
				+ createddatetime + "]";
	}

}
