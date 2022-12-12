package com.autolib.helpdesk.Tickets.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;
import com.autolib.helpdesk.common.EnumUtils.TicketPriority;
import com.autolib.helpdesk.common.EnumUtils.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tickets", indexes = { @Index(name = "institute_id_idx", columnList = "institute_id"),
		@Index(name = "service_type_idx", columnList = "service_type"),
		@Index(name = "priority_idx", columnList = "priority"), @Index(name = "priority_idx", columnList = "priority"),
		@Index(name = "status_idx", columnList = "status") })
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ticket_id", updatable = false, nullable = false)
	private int ticketId;

	@Fetch(FetchMode.JOIN)
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@Column
	private String product;

	@Transient
	private String instituteId;

	@Lob
	@Column(nullable = false)
	private String subject;

	@Lob
	@Column
	private String summary;

	@Column
	private Double rating;

	@Column
	private String emailUpdates;

	@Column(name = "service_under", length = 45, nullable = false)
	@Enumerated(EnumType.STRING)
	private ServiceUnder serviceUnder;

	@Column(name = "service_type", length = 128, nullable = false)
	private String serviceType;

	@Column(length = 45, nullable = false)
	@Enumerated(EnumType.STRING)
	private TicketPriority priority;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Column(name = "assigned_by")
	private String assignedBy;

	@Column(name = "assigned_to")
	private String assignedTo;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "assigned_date_time")
	private Date assignedDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closed_date_time")
	private Date closedDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "due_date_time")
	private Date dueDateTime;

	@Column(name = "last_updated_by")
	private String lastUpdatedBy;

	@Column(length = 45, nullable = false)
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public Ticket() {
		super();
	}

	public Ticket(int ticketId) {
		super();
		this.ticketId = ticketId;
	}

	public String getInstituteId() {
		return institute.getInstituteId();
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getEmailUpdates() {
		return emailUpdates;
	}

	public void setEmailUpdates(String emailUpdates) {
		this.emailUpdates = emailUpdates;
	}

	public ServiceUnder getServiceUnder() {
		return serviceUnder;
	}

	public void setServiceUnder(ServiceUnder serviceUnder) {
		this.serviceUnder = serviceUnder;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
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

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDueDateTime() {
		return dueDateTime;
	}

	public void setDueDateTime(Date dueDateTime) {
		this.dueDateTime = dueDateTime;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public Date getAssignedDateTime() {
		return assignedDateTime;
	}

	public void setAssignedDateTime(Date assignedDateTime) {
		this.assignedDateTime = assignedDateTime;
	}

	public Date getClosedDateTime() {
		return closedDateTime;
	}

	public void setClosedDateTime(Date closedDateTime) {
		this.closedDateTime = closedDateTime;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", institute=" + institute + ", product=" + product + ", instituteId="
				+ instituteId + ", subject=" + subject + ", summary=" + summary + ", rating=" + rating
				+ ", emailUpdates=" + emailUpdates + ", serviceUnder=" + serviceUnder + ", serviceType=" + serviceType
				+ ", priority=" + priority + ", emailId=" + emailId + ", assignedBy=" + assignedBy + ", assignedTo="
				+ assignedTo + ", createdBy=" + createdBy + ", assignedDateTime=" + assignedDateTime
				+ ", closedDateTime=" + closedDateTime + ", dueDateTime=" + dueDateTime + ", lastUpdatedBy="
				+ lastUpdatedBy + ", status=" + status + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
