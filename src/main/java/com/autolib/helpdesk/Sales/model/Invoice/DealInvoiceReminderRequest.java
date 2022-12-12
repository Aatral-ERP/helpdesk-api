package com.autolib.helpdesk.Sales.model.Invoice;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Institutes.model.Institute;

public class DealInvoiceReminderRequest {

	int dealId;

	List<DealInvoiceReminder> dealInvoiceReminders;

	private Date fromDate;
	private Date toDate;
	private List<Institute> institutes;
	private String description;

	private Date createdFromDate;
	private Date createdToDate;

	public Date getCreatedFromDate() {
		return createdFromDate;
	}

	public void setCreatedFromDate(Date createdFromDate) {
		this.createdFromDate = createdFromDate;
	}

	public Date getCreatedToDate() {
		return createdToDate;
	}

	public void setCreatedToDate(Date createdToDate) {
		this.createdToDate = createdToDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public List<DealInvoiceReminder> getDealInvoiceReminders() {
		return dealInvoiceReminders;
	}

	public void setDealInvoiceReminders(List<DealInvoiceReminder> dealInvoiceReminders) {
		this.dealInvoiceReminders = dealInvoiceReminders;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "DealInvoiceReminderRequest [dealId=" + dealId + ", dealInvoiceReminders=" + dealInvoiceReminders
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", institutes=" + institutes + ", description="
				+ description + ", createdFromDate=" + createdFromDate + ", createdToDate=" + createdToDate + "]";
	}

}
