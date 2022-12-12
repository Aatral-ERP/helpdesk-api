package com.autolib.helpdesk.Sales.model.Inputs;


public class BillPaymentResponse {
	
	private BillPayments BillPayments;
	private Bill Bill;
	
	public BillPaymentResponse(com.autolib.helpdesk.Sales.model.Inputs.BillPayments billPayments,
			com.autolib.helpdesk.Sales.model.Inputs.Bill bill) {  
		super();
		BillPayments = billPayments;
		Bill = bill;
	}
	
	public BillPayments getBillPayments() {
		return BillPayments;
	}
	public void setBillPayments(BillPayments billPayments) {
		BillPayments = billPayments;
	}

	public Bill getBill() {
		return Bill;
	}

	public void setBill(Bill bill) {
		Bill = bill;
	}
		
}
