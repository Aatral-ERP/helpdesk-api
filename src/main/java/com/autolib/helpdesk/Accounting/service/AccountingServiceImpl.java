package com.autolib.helpdesk.Accounting.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Accounting.dao.AccountingDAO;
import com.autolib.helpdesk.Accounting.model.AccountBankStatementReq;
import com.autolib.helpdesk.Accounting.model.AccountStatementRequest;
import com.autolib.helpdesk.Accounting.model.AccountingReportRequest;
import com.autolib.helpdesk.Accounting.model.AmcReportRequest;
import com.autolib.helpdesk.Accounting.model.IncomeExpense;
import com.autolib.helpdesk.Accounting.model.IncomeExpenseRequest;
import com.autolib.helpdesk.Accounting.model.LetterPad;
import com.autolib.helpdesk.Accounting.model.LetterpadRequest;
import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentLedger;

@Service
public class AccountingServiceImpl implements AccountingService {

	@Autowired
	private AccountingDAO acntDAO;
	
	@Value("${al.letterpad.cmpInstituteName}")
	private String cmp;

	@Override
	public Map<String, Object> getAccountingDashboardData() {
		return acntDAO.getAccountingDashboardData();
	}

	@Override
	public Map<String, Object> saveIncomeExpense(IncomeExpense incomeExpense) {
		return acntDAO.saveIncomeExpense(incomeExpense);
	}

	@Override
	public Map<String, Object> deleteIncomeExpense(IncomeExpense incomeExpense) {
		return acntDAO.deleteIncomeExpense(incomeExpense);
	}

	@Override
	public Map<String, Object> getIncomeExpense(IncomeExpense incomeExpense) {
		return acntDAO.getIncomeExpense(incomeExpense);
	}

	@Override
	public Map<String, Object> getIncomeExpense(IncomeExpenseRequest incomeExpenseReq) {
		return acntDAO.getIncomeExpense(incomeExpenseReq);
	}

	@Override
	public Map<String, Object> getIncomeExpenseNeeded() {
		return acntDAO.getIncomeExpenseNeeded();
	}

	@Override
	public Map<String, Object> getAgentLedger(Agent agent) {
		return acntDAO.getAgentLedger(agent);
	}

	@Override
	public Map<String, Object> uploadLegderProof(MultipartFile file, int ledgerId) {
		return acntDAO.uploadLegderProof(file, ledgerId);
	}

	@Override
	public Map<String, Object> addAgentLedger(AgentLedger ledger) {
		return acntDAO.addAgentLedger(ledger);
	}

	@Override
	public Map<String, Object> getAccountingReportData(AccountingReportRequest accountingReportReq) {
		return acntDAO.getAccountingReportData(accountingReportReq);
	}

	@Override
	public Map<String, Object> saveAccountStatement(AccountStatementRequest request) {
		return acntDAO.saveAccountStatement(request);
	}

	@Override
	public Map<String, Object> getStatementReportData(AccountBankStatementReq req) {
		return acntDAO.getStatementReportData(req);
	}

	@Override
	public Map<String, Object> getCategoryNeeded() {
		return acntDAO.getCategoryNeeded();
	}

	@Override
	public Map<String, Object> saveLetterPad(LetterPad letterpad) {
		return acntDAO.saveLetterPad(letterpad);
	}
	
	@Override
	public Map<String, Object> getLetterPad(int id) {
		return acntDAO.getLetterPad(id);
	}

	@Override
	public Map<String, Object> getLetterpadReportPdf(LetterpadRequest letterpadReq) {

		if(cmp.equalsIgnoreCase("2CQR"))
			//return acntDAO.getLetterpadReportPdfTemplate1(letterpadReq);
			return acntDAO.getLetterpadReportPdfTemplate3(letterpadReq);
		
		else if(cmp.equalsIgnoreCase("AUTOLIB"))
			return acntDAO.getLetterpadReportPdfTemplate4(letterpadReq);
		else 
			return acntDAO.getLetterpadReportPdfTemplate2(letterpadReq);
	}

	@Override
	public Map<String, Object> deleteLetterPad(LetterPad letterpad) {
		return acntDAO.deleteLetterPad(letterpad);
	}

	@Override
	public Map<String, Object> getAllLetterPad(LetterpadRequest req) {
		return acntDAO.getAllLetterPad(req);
	}

	@Override
	public Map<String, Object> UploadGeneratedLetterpadPDF(int id, MultipartFile file) {
		return acntDAO.UploadGeneratedLetterpadPDF(id,file);
	}

	@Override
	public Map<String, Object> getAmcReport(AmcReportRequest amcReport) {
		return acntDAO.getAmcReport(amcReport);
	}

}
