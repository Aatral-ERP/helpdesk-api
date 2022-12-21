package com.autolib.helpdesk.Accounting.model;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealPayments;

public class AmcReportResponse {

	public AmcReportResponse(com.autolib.helpdesk.Sales.model.DealPayments dealPayments,
			com.autolib.helpdesk.Sales.model.Deal deal) {

		super();

		this.dealPayments = dealPayments;
		this.deal = deal;

	}

	private Institute Institute;

	private DealPayments dealPayments;

	private Deal deal;

	public Institute getInstitute() {
		return Institute;
	}

	public DealPayments getDealPayments() {
		return dealPayments;
	}

	public void setDealPayments(DealPayments dealPayments) {
		this.dealPayments = dealPayments;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public void setInstitute(Institute institute) {
		Institute = institute;
	}

	

}
