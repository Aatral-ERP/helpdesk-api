package com.autolib.helpdesk.Accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Accounting.model.AccountStatement;
import com.autolib.helpdesk.Accounting.model.AccountStatementRequest;

	public interface AccountStatementRepository extends JpaRepository<AccountStatement, Integer>
	{

		//void save(List<AccountStatement> accountstatement);

		//AccountStatement saveAll(AccountStatement accountStatement);

}
