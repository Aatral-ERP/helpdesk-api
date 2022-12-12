package com.autolib.helpdesk.Sales.model.Invoice;

import com.autolib.helpdesk.Sales.model.Deal;

public class DealInvoiceReminderResponse {

	private DealInvoiceReminder reminder;
	private Deal deal;

	public DealInvoiceReminderResponse(DealInvoiceReminder reminder, Deal deal) {
		super();
		this.reminder = reminder;
		this.deal = deal;
	}

	public DealInvoiceReminder getReminder() {
		return reminder;
	}

	public void setReminder(DealInvoiceReminder reminder) {
		this.reminder = reminder;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

}
