package com.autolib.helpdesk.Accounting.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Accounting.model.AccountBankStatementReq;
import com.autolib.helpdesk.Accounting.model.AccountStatementRequest;
import com.autolib.helpdesk.Accounting.model.AccountingReportRequest;
import com.autolib.helpdesk.Accounting.model.IncomeExpense;
import com.autolib.helpdesk.Accounting.model.IncomeExpenseRequest;
import com.autolib.helpdesk.Accounting.model.LetterPad;
import com.autolib.helpdesk.Accounting.model.LetterpadRequest;
import com.autolib.helpdesk.Accounting.service.AccountingService;
import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentLedger;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("accounts")
public class AccountingController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	private AccountingService acntService;

	@PostMapping("get-accounting-dashboard-data")
	public ResponseEntity<?> getAccountingDashboardData(@RequestHeader(value = "Authorization") String token) {

		logger.info("getAccountingDashboardData starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getAccountingDashboardData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAccountingDashboardData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-income-expense")
	public ResponseEntity<?> saveIncomeExpense(@RequestHeader(value = "Authorization") String token,
			@RequestBody IncomeExpense incomeExpense) {

		logger.info("saveIncomeExpense starts:::" + incomeExpense);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.saveIncomeExpense(incomeExpense);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveIncomeExpense ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-income-expense")
	public ResponseEntity<?> deleteIncomeExpense(@RequestHeader(value = "Authorization") String token,
			@RequestBody IncomeExpense incomeExpense) {

		logger.info("deleteIncomeExpense starts:::" + incomeExpense);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.deleteIncomeExpense(incomeExpense);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteIncomeExpense ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-income-expense")
	public ResponseEntity<?> getIncomeExpense(@RequestHeader(value = "Authorization") String token,
			@RequestBody IncomeExpense incomeExpense) {

		logger.info("getIncomeExpense starts:::" + incomeExpense);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getIncomeExpense(incomeExpense);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getIncomeExpense ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-income-expense-report")
	public ResponseEntity<?> getIncomeExpense(@RequestHeader(value = "Authorization") String token,
			@RequestBody IncomeExpenseRequest incomeExpenseReq) {

		logger.info("getIncomeExpense starts:::" + incomeExpenseReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getIncomeExpense(incomeExpenseReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getIncomeExpense ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-income-expense-needed")
	public ResponseEntity<?> getIncomeExpenseNeeded(@RequestHeader(value = "Authorization") String token) {

		logger.info("getIncomeExpenseData starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getIncomeExpenseNeeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getIncomeExpenseData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-agent-ledger")
	public ResponseEntity<?> getAgentLedger(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) {

		logger.info("getAgentLedger starts:::" + agent);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getAgentLedger(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAgentLedger ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-agent-ledger")
	public ResponseEntity<?> addAgentLedger(@RequestHeader(value = "Authorization") String token,
			@RequestBody AgentLedger ledger) {

		logger.info("addAgentLedger starts:::" + ledger);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.addAgentLedger(ledger);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("addAgentLedger ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("/upload-ledger-proof")
	ResponseEntity<?> uploadLegderProof(@RequestHeader(value = "Authorization") String token,
			@RequestParam("photo") MultipartFile photo, @RequestParam("ledgerId") int ledgerId) {
		logger.info("uploadLegderProof req Starts::::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);
			resp = acntService.uploadLegderProof(photo, ledgerId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-account-report")
	public ResponseEntity<?> getAccountingReportData(@RequestHeader(value = "Authorization") String token,
			@RequestBody AccountingReportRequest accountingReportReq) {

		logger.info("getAccountingReportData starts:::" + accountingReportReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getAccountingReportData(accountingReportReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAccountingReportData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-account-statement")
	public ResponseEntity<?> saveAccountStatement(@RequestHeader(value = "Authorization") String token,
			@RequestBody AccountStatementRequest request) {
		System.out.println("saveAccountStatement req starts::" + request.toString());
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.saveAccountStatement(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-bank-statement-report")
	public ResponseEntity<?> getStatementReportData(@RequestHeader(value = "Authorization") String token,
			@RequestBody AccountBankStatementReq req) {

		logger.info("getStatementReportData starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getStatementReportData(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAccountingReportData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-category-needed")
	public ResponseEntity<?> getCategoryNeeded(@RequestHeader(value = "Authorization") String token) {

		logger.info("getCategoryNeeded starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getCategoryNeeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getCategoryNeeded ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	

	@PostMapping("save-letterpad")
	public ResponseEntity<?> saveLetterPad(@RequestHeader(value = "Authorization")String token , @RequestBody LetterPad letterpad) {

		logger.info("save-letterpad starts:::"+letterpad);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.saveLetterPad(letterpad);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("save-letterpad ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@GetMapping("get-letterpad/{id}")
	public ResponseEntity<?> getletterpad(@RequestHeader(value = "Authorization") String token,
			@PathVariable("id") int id) throws Exception {
		logger.info("get-letterpad starts:::" + id);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			resp = acntService.getLetterPad(id);

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		logger.info("getCallReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-letterpad-pdf")
	public ResponseEntity<?> getLetterpadReportPdf(@RequestHeader(value = "Authorization") String token,
			@RequestBody LetterpadRequest letterpadReq) {

		logger.info("get-letterpad starts:::" + letterpadReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getLetterpadReportPdf(letterpadReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getLetterpadReportPdf ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("delete-letterpad")
	public ResponseEntity<?> deleteLetterPad(@RequestHeader(value = "Authorization")String token ,@RequestBody LetterPad letterpad) {

		logger.info("delete-letterpad::::::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.deleteLetterPad(letterpad);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("delete-letterpad::::::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("get-all-letterpad")
	public ResponseEntity<?> getAllLetterPad(@RequestHeader(value = "Authorization")String token,@RequestBody LetterpadRequest req) {

		logger.info("get-all-letterpad:::"+req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = acntService.getAllLetterPad(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("get-all-letterpad ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	
	@PostMapping("upload-generated-letterpad-pdf")
	public ResponseEntity<?> UploadGeneratedLetterpadPDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("id") int id) throws Exception {
		logger.info("UploadGeneratedLetterpadPDF starts:::" + id);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = acntService.UploadGeneratedLetterpadPDF(id, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadGeneratedLetterpadPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("amc-report")
	public ResponseEntity<?> getAmcReport(@RequestBody AmcReportRequest amcReport){
		
		Map<String,Object> respMap = new HashMap<>();
		logger.info("getAmcReport request Starts:::::"+amcReport.toString());
		try {
			respMap=acntService.getAmcReport(amcReport);
		}catch(Exception ex) { ex.printStackTrace();}
		return new ResponseEntity<>(respMap,HttpStatus.OK);
		
	}
	
}




