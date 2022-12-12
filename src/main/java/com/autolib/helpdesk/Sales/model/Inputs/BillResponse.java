package com.autolib.helpdesk.Sales.model.Inputs;

public class BillResponse {

	public BillResponse() {
		// TODO Auto-generated constructor stub
	}

	private Bill bill;

	private BillPayments billPayments;

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public BillPayments getBillPayments() {
		return billPayments;
	}

	public void setBillPayments(BillPayments billPayments) {
		this.billPayments = billPayments;
	}

}
