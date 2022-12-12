package com.autolib.helpdesk.Tickets.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ticket_service_invoices")
public class TicketServiceInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable = false, unique = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Ticket ticket;

	@Column(length = 1024)
	private String title;
	@Column(name = "invoice_no", unique = true)
	private String invoiceNo;

	@Lob
	@Column
	private String description;
	@Column
	private Double amount;
	@Column
	private Double gst;
	@Column(name = "ticket_amount")
	private Double totalAmount;
	@Column(name = "invoice_file_name")
	private String invoiceFileName;
	@Column
	private String product;

	@Column(name = "generated_by")
	private String generatedBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public TicketServiceInvoice() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getGST() {
		return gst;
	}

	public void setGST(Double gST) {
		this.gst = gST;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInvoiceFileName() {
		return invoiceFileName;
	}

	public void setInvoiceFileName(String invoiceFileName) {
		this.invoiceFileName = invoiceFileName;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Override
	public String toString() {
		return "TicketServiceInvoice [id=" + id + ", ticket=" + ticket + ", title=" + title + ", invoiceNo=" + invoiceNo
				+ ", description=" + description + ", amount=" + amount + ", gst=" + gst + ", totalAmount="
				+ totalAmount + ", invoiceFileName=" + invoiceFileName + ", product=" + product + ", generatedBy="
				+ generatedBy + ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime
				+ "]";
	}

}
