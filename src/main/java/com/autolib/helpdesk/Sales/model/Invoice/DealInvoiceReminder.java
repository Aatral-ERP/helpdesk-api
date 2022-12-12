package com.autolib.helpdesk.Sales.model.Invoice;

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
@Table(name = "deals_invoice_reminders")
public class DealInvoiceReminder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id", columnDefinition = "integer default 0", nullable = false)
	private int dealId;

	@Column(name = "invoice_id", columnDefinition = "integer default 0", nullable = false)
	private int invoiceId;

	@Column(name = "invoice_No")
	private String invoiceNo;

	@Lob
	@Column
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name = "reminder_date")
	private Date reminderDate;

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

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Override
	public String toString() {
		return "DealInvoiceReminder [id=" + id + ", dealId=" + dealId + ", invoiceId=" + invoiceId + ", invoiceNo="
				+ invoiceNo + ", description=" + description + ", reminderDate=" + reminderDate + ", createddatetime="
				+ createddatetime + "]";
	}

}
