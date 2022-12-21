package com.autolib.helpdesk.Accounting.dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Accounting.model.AccountBankStatementReq;
import com.autolib.helpdesk.Accounting.model.AccountStatementRequest;
import com.autolib.helpdesk.Accounting.model.AccountingReportRequest;
import com.autolib.helpdesk.Accounting.model.IncomeExpense;
import com.autolib.helpdesk.Accounting.model.IncomeExpenseRequest;
import com.autolib.helpdesk.Accounting.model.LetterPad;
import com.autolib.helpdesk.Accounting.model.LetterpadRequest;
import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentLedger;

public interface AccountingDAO {

	Map<String, Object> getAccountingDashboardData();

	Map<String, Object> saveIncomeExpense(IncomeExpense incomeExpense);

	Map<String, Object> deleteIncomeExpense(IncomeExpense incomeExpense);

	Map<String, Object> getIncomeExpense(IncomeExpense incomeExpense);

	Map<String, Object> getIncomeExpense(IncomeExpenseRequest incomeExpenseReq);

	Map<String, Object> getIncomeExpenseNeeded();

	Map<String, Object> getAgentLedger(Agent agent);

	Map<String, Object> uploadLegderProof(MultipartFile file, int ledgerId);

	/**
	 * @param ledger
	 * @return
	 */
	Map<String, Object> addAgentLedger(AgentLedger ledger);

	Map<String, Object> getAccountingReportData(AccountingReportRequest accountingReportReq);

	Map<String, Object> saveAccountStatement(AccountStatementRequest request);

	Map<String, Object> getStatementReportData(AccountBankStatementReq req);

	Map<String, Object> getCategoryNeeded();

	Map<String, Object> saveLetterPad(LetterPad letterpad);

	Map<String, Object> getLetterPad(int id);

	//Map<String, Object> getLetterpadReportPdf(LetterpadRequest letterpadReq);
	
	Map<String, Object> getLetterpadReportPdfTemplate1(LetterpadRequest letterpadReq);
	
	Map<String, Object> getLetterpadReportPdfTemplate2(LetterpadRequest letterpadReq);
	
	Map<String, Object> getLetterpadReportPdfTemplate3(LetterpadRequest letterpadReq);
	
	Map<String, Object> getLetterpadReportPdfTemplate4(LetterpadRequest letterpadReq);
	
	Map<String, Object> deleteLetterPad(LetterPad letterpad);

	Map<String, Object> getAllLetterPad(LetterpadRequest req);

	Map<String, Object> UploadGeneratedLetterpadPDF(int id, MultipartFile file);

	

	

}
