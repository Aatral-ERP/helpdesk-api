package com.autolib.helpdesk.Accounting.model;

import java.util.List;

public class AccountStatementRequest {
	
	
	private List<AccountStatement> accountStatement;

	public List<AccountStatement> getAccountStatement() {
		return accountStatement;
	}

	public void setAccountStatement(List<AccountStatement> accountStatement) {
		this.accountStatement = accountStatement;
	}

	@Override
	public String toString() {
		return "AccountStatementRequest [accountStatement=" + accountStatement + "]";
	}

	
	
}
