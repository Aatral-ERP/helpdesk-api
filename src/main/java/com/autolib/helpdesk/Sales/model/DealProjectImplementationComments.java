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

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_project_implementation_comments", indexes = {
		@Index(name = "comment_at_status_idx", columnList = "comment_at_status"),
		@Index(name = "project_implemenation_id_idx", columnList = "project_implemenation_id"),
		@Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealProjectImplementationComments {

	/**
	 * 
	 */
	public DealProjectImplementationComments() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "project_implemenation_id")
	private int projectImplemenationId;

	@Column(name = "deal_id")
	private int dealId;

	@Column(name = "comment_at_status")
	private String commentAtStatus;

	@Column(name = "comment_by")
	private String commentBy;

	@Lob
	@Column
	private String comment;

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

	public int getProjectImplemenationId() {
		return projectImplemenationId;
	}

	public void setProjectImplemenationId(int projectImplemenationId) {
		this.projectImplemenationId = projectImplemenationId;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getCommentAtStatus() {
		return commentAtStatus;
	}

	public void setCommentAtStatus(String commentAtStatus) {
		this.commentAtStatus = commentAtStatus;
	}

	public String getCommentBy() {
		return commentBy;
	}

	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	@Override
	public String toString() {
		return "DealProjectImplementationComments [id=" + id + ", projectImplemenationId=" + projectImplemenationId
				+ ", dealId=" + dealId + ", commentAtStatus=" + commentAtStatus + ", commentBy=" + commentBy
				+ ", comment=" + comment + ", createddatetime=" + createddatetime + "]";
	}

}
