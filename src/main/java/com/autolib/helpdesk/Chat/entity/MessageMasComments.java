package com.autolib.helpdesk.Chat.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "message_mas_comments")
public class MessageMasComments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private int id;
	@Column(length = 45, nullable = false)
	public String msgcode;
	@Column(length = 4096, nullable = false)
	@Lob
	private String comment;
	@Column(length = 45, nullable = false)
	private String commentby;
	@Column(nullable = false)
	private boolean isdeleted = false;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date commentdatetime;
	@Transient
	private String commentbyname;

	public MessageMasComments() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentby() {
		return commentby;
	}

	public void setCommentby(String commentby) {
		this.commentby = commentby;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Date getCommentdatetime() {
		return commentdatetime;
	}

	public void setCommentdatetime(Date commentdatetime) {
		this.commentdatetime = commentdatetime;
	}

	public String getCommentbyname() {
		return commentbyname;
	}

	public void setCommentbyname(String commentbyname) {
		this.commentbyname = commentbyname;
	}

	@Override
	public String toString() {
		return "MessageMasComments [id=" + id + ", msgcode=" + msgcode + ", comment=" + comment + ", commentby="
				+ commentby + ", isdeleted=" + isdeleted + ", commentdatetime=" + commentdatetime + ", commentbyname="
				+ commentbyname + "]";
	}

}
