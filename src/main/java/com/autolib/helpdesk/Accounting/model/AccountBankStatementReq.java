package com.autolib.helpdesk.Accounting.model;

import java.util.Date;

public class AccountBankStatementReq {
	
	public  AccountBankStatementReq()
	{
		
	}

	private String description;
	private String refNo;
	private Date transDate=null;
	private Date transDateFrom = null;
	private Date transDateTo = null;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public Date getTransDateFrom() {
		return transDateFrom;
	}
	public void setTransDateFrom(Date transDateFrom) {
		this.transDateFrom = transDateFrom;
	}
	public Date getTransDateTo() {
		return transDateTo;
	}
	public void setTransDateTo(Date transDateTo) {
		this.transDateTo = transDateTo;
	}
	@Override
	public String toString() {
		return "AccountBankStatementReq [description=" + description + ", refNo=" + refNo + ", transDate=" + transDate
				+ ", transDateFrom=" + transDateFrom + ", transDateTo=" + transDateTo + "]";
	}

}
