package com.autolib.helpdesk.Accounting.model;

import java.util.Date;

public class IncomeExpenseRequest {

	public IncomeExpenseRequest() {
		// TODO Auto-generated constructor stub
	}

	private Date dateFrom;

	private Date dateTo;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "IncomeExpenseRequest [dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}

}
