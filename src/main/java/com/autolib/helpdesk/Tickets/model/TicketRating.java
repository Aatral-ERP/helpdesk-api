package com.autolib.helpdesk.Tickets.model;

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
@Table(name = "ticket_ratings", indexes = { @Index(name = "ticket_id_idx", columnList = "ticket_id") })
public class TicketRating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "ticket_id", unique = true)
	private int ticketId;
	@Lob
	@Column
	private String feedback;
	@Column(name = "feedback_by")
	private String feedbackBy;
	@Column
	private double rating;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public TicketRating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
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

	public String getFeedbackBy() {
		return feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy) {
		this.feedbackBy = feedbackBy;
	}

	@Override
	public String toString() {
		return "TicketRating [id=" + id + ", ticketId=" + ticketId + ", feedback=" + feedback + ", feedbackBy="
				+ feedbackBy + ", rating=" + rating + ", createddatetime=" + createddatetime + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
