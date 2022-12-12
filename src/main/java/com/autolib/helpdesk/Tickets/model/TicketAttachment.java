
package com.autolib.helpdesk.Tickets.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ticket_attachments", indexes = { @Index(name = "ticket_id_idx", columnList = "ticket_id") })
public class TicketAttachment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Ticket ticket;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_size")
	private long fileSize;

	@Column(name = "file_type", length = 128)
	private String fileType;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@Transient
	private int ticketId;

	public TicketAttachment() {
		super();
	}

	public TicketAttachment(Ticket ticket, String fileName, long fileSize, String fileType) {
		super();
		this.ticket = ticket;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public int getTicketId() {
		return this.ticket.getTicketId();
	}

	@Override
	public String toString() {
		return "TicketAttachment [id=" + id + ", ticket=" + ticket + ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileType=" + fileType + ", createddatetime=" + createddatetime + ", ticketId=" + ticketId + "]";
	}

}
