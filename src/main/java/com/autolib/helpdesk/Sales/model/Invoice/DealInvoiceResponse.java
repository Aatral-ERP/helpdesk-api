package com.autolib.helpdesk.Sales.model.Invoice;

import com.autolib.helpdesk.Sales.model.DealPayments;

public class DealInvoiceResponse {

	public DealInvoiceResponse() {
		// TODO Auto-generated constructor stub
	}

	public DealInvoiceResponse(DealInvoice dealInvoice, DealPayments dealPayments) {
		super();
		this.dealInvoice = dealInvoice;
		this.dealPayments = dealPayments;
	}

	private DealInvoice dealInvoice;

	private DealPayments dealPayments;

	public DealInvoice getDealInvoice() {
		return dealInvoice;
	}

	public void setDealInvoice(DealInvoice dealInvoice) {
		this.dealInvoice = dealInvoice;
	}

	public DealPayments getDealPayments() {
		return dealPayments;
	}

	public void setDealPayments(DealPayments dealPayments) {
		this.dealPayments = dealPayments;
	}

}
