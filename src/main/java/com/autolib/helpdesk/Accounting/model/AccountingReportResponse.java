package com.autolib.helpdesk.Accounting.model;
import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.Inputs.BillPayments;

public class AccountingReportResponse {
	

	private BillPayments BillPayments;
	private DealPayments DealPayments;
	private IncomeExpense IncomeExpense;
	
	public AccountingReportResponse(com.autolib.helpdesk.Sales.model.Inputs.BillPayments billPayments,
			com.autolib.helpdesk.Sales.model.DealPayments dealPayments,
			com.autolib.helpdesk.Accounting.model.IncomeExpense incomeExpense) {  
		super();
		BillPayments = billPayments;
		DealPayments = dealPayments;
	}

	public BillPayments getBillPayments() {
		return BillPayments;
	}

	public void setBillPayments(BillPayments billPayments) {
		BillPayments = billPayments;
	}

	public DealPayments getDealPayments() {
		return DealPayments;
	}

	public void setDealPayments(DealPayments dealPayments) {
		DealPayments = dealPayments;
	}

	public IncomeExpense getIncomeExpense() {
		return IncomeExpense;
	}

	public void setIncomeExpense(IncomeExpense incomeExpense) {
		IncomeExpense = incomeExpense;
	}
	
}
