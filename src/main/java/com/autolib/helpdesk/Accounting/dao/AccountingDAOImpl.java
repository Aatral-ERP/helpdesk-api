
package com.autolib.helpdesk.Accounting.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Accounting.model.AccountBankStatementReq;
import com.autolib.helpdesk.Accounting.model.AccountCategory;
import com.autolib.helpdesk.Accounting.model.AccountStatementRequest;
import com.autolib.helpdesk.Accounting.model.AccountingReportRequest;
import com.autolib.helpdesk.Accounting.model.AmcReportRequest;
import com.autolib.helpdesk.Accounting.model.IncomeExpense;
import com.autolib.helpdesk.Accounting.model.IncomeExpenseRequest;
import com.autolib.helpdesk.Accounting.model.LetterPad;
import com.autolib.helpdesk.Accounting.model.LetterpadRequest;
import com.autolib.helpdesk.Accounting.repository.AccountStatementRepository;
import com.autolib.helpdesk.Accounting.repository.IncomeExpenseRepository;
import com.autolib.helpdesk.Accounting.repository.LetterpadRepository;
import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentLedger;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.entity.Vendor;
import com.autolib.helpdesk.Agents.repository.AgentLedgerRepository;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.VendorRepository;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.common.Util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
public class AccountingDAOImpl implements AccountingDAO {

	@Autowired
	private AgentRepository agentRepo;

	@Autowired
	private VendorRepository vendorRepo;

	@Autowired
	private IncomeExpenseRepository inExRepo;

	@Autowired
	private AccountStatementRepository accStaRepo;

	@Autowired
	AgentLedgerRepository agentLedgerRepo;

	@Autowired
	private JdbcTemplate jdbcTemp;

	@Autowired
	EntityManager em;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	@Autowired
	LetterpadRepository letterpadRepo;

	@Autowired
	InfoDetailsRepository infoDetailRepo;

	@Autowired
	InstituteRepository instRepo;

	@Value("${al.letterpad.cmpInstituteName}")
	private String cmp;

	@Override
	public Map<String, Object> getAccountingDashboardData() {
		Map<String, Object> resp = new HashMap<>();

		List<Map<String, Object>> _income_expense_data = new ArrayList<>();
		try {

			// Income and Expense Starts
			List<Map<String, Object>> incomes = inExRepo.getAccountingIncomeReport();

			List<Map<String, Object>> expenses = inExRepo.getAccountingExpenseReport();

			incomes.forEach(income -> {
				Map<String, Object> _income_expense = new HashMap<>();

				_income_expense.put("label", income.get("label"));
				_income_expense.put("income", income.get("amount"));

				expenses.stream().filter(expense -> expense.get("label").equals(income.get("label")))
						.forEach(expense -> {
							_income_expense.put("expense", expense.get("amount"));
						});
				_income_expense_data.add(_income_expense);
			});

			// Income and Expense Ends Chart Data Starts
			resp.put("last6MonthsIncome", inExRepo.getLast6MonthsIncome());

			resp.put("last6MonthsExpense", inExRepo.getLast6MonthsExpense());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse());
		}

		resp.put("_income_expense_datas", _income_expense_data);
		return resp;
	}

	@Override
	public Map<String, Object> saveIncomeExpense(IncomeExpense incomeExpense) {
		Map<String, Object> resp = new HashMap<>();
		try {

//			boolean newIncomeExpense = incomeExpense.getId() > 0 ? false : true;

			incomeExpense = inExRepo.save(incomeExpense);

//			if (incomeExpense.getType().equalsIgnoreCase("expense")) {
//				AgentLedger ledger = new AgentLedger();
//				if (newIncomeExpense) {
//					ledger.setExpenseId(incomeExpense.getId());
//					ledger.setAgentEmailId(incomeExpense.getRelatedToAgentId());
//					ledger.setSubject(incomeExpense.getSubject());
//					ledger.setPaymentDate(incomeExpense.getPaymentDate());
//					ledger.setCredit(incomeExpense.getAmount());
//					ledger.setDebit(0.00);
//					ledger.setBalance(0.00);
//				} else {
//					ledger = agentLedgerRepo.findByExpenseId(incomeExpense.getId());
//
//					if (ledger != null) {
//						ledger.setExpenseId(incomeExpense.getId());
//						ledger.setAgentEmailId(incomeExpense.getRelatedToAgentId());
//						ledger.setSubject(incomeExpense.getSubject());
//						ledger.setPaymentDate(incomeExpense.getPaymentDate());
//						ledger.setCredit(incomeExpense.getAmount());
//						ledger.setDebit(0.00);
//						ledger.setBalance(0.00);
//					} else {
//						ledger = new AgentLedger();
//						ledger.setExpenseId(incomeExpense.getId());
//						ledger.setAgentEmailId(incomeExpense.getRelatedToAgentId());
//						ledger.setSubject(incomeExpense.getSubject());
//						ledger.setPaymentDate(incomeExpense.getPaymentDate());
//						ledger.setCredit(incomeExpense.getAmount());
//						ledger.setDebit(0.00);
//						ledger.setBalance(0.00);
//					}
//				}
//				agentLedgerRepo.save(ledger);
//			}
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("IncomeExpense", incomeExpense);
		return resp;
	}

	@Override
	public Map<String, Object> deleteIncomeExpense(IncomeExpense incomeExpense) {
		Map<String, Object> resp = new HashMap<>();
		try {

			inExRepo.delete(incomeExpense);

//			AgentLedger ledger = agentLedgerRepo.findByExpenseId(incomeExpense.getId());

//			agentLedgerRepo.delete(ledger);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getIncomeExpense(IncomeExpense incomeExpense) {
		Map<String, Object> resp = new HashMap<>();
		try {

			incomeExpense = inExRepo.findById(incomeExpense.getId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("IncomeExpense", incomeExpense);
		return resp;
	}

	@Override
	public Map<String, Object> getIncomeExpense(IncomeExpenseRequest incomeExpenseReq) {
		Map<String, Object> resp = new HashMap<>();
		List<Map<String, Object>> incomes = new ArrayList<>();
		List<Map<String, Object>> expenses = new ArrayList<>();
		try {

			incomes = inExRepo.get_recent_incomes(incomeExpenseReq.getDateFrom(), incomeExpenseReq.getDateTo());

			expenses = inExRepo.get_recent_expenses(incomeExpenseReq.getDateFrom(), incomeExpenseReq.getDateTo());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Incomes", incomes);
		resp.put("Expenses", expenses);
		resp.put("IncomesTotal",
				incomes.stream().mapToDouble(inc -> Double.parseDouble(String.valueOf(inc.get("amount")))).sum());
		resp.put("ExpensesTotal",
				expenses.stream().mapToDouble(inc -> Double.parseDouble(String.valueOf(inc.get("amount")))).sum());
		return resp;
	}

	@Override
	public Map<String, Object> getIncomeExpenseNeeded() {
		Map<String, Object> resp = new HashMap<>();
		List<String> _income_category = new ArrayList<>();
		List<String> _expense_category = new ArrayList<>();
		List<Agent> _agents = new ArrayList<>();
		List<Vendor> _vendors = new ArrayList<>();
		try {

			_income_category = inExRepo.findDistinctCategory("Income");

			_expense_category = inExRepo.findDistinctCategory("Expense");

			_agents = agentRepo.findAllMinDetails().stream()
					.filter(agent -> agent.getWorkingStatus().equalsIgnoreCase("working") && !agent.isBlocked())
					.collect(Collectors.toList());

			_vendors = vendorRepo.findAllMinDetails();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("IncomeCategorys", _income_category);
		resp.put("ExpenseCategorys", _expense_category);
		resp.put("Vendors", _vendors);
		resp.put("Agents", _agents);
		return resp;
	}

	@Override
	public Map<String, Object> getAgentLedger(Agent agent) {
		Map<String, Object> resp = new HashMap<>();
		List<AgentLedger> _ledger = new ArrayList<>();
		try {

			_ledger = agentLedgerRepo.findByAgentEmailId(agent.getEmailId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("AgentLedger", _ledger);
		return resp;
	}

	@Override
	public Map<String, Object> uploadLegderProof(MultipartFile photo, int ledgerId) {

		System.out.println("inside uploadLegderProof Dao Impl::::::::");
		Map<String, Object> respMap = new HashMap<>();
		AgentLedger legder = new AgentLedger();
		try {

			legder = agentLedgerRepo.findById(ledgerId);

			String filename = ledgerId + "_" + photo.getOriginalFilename();

			if (legder != null) {

				File directory = new File(contentPath + "/_agent_legder_proof" + "/");
				System.out.println(directory.getAbsolutePath());
				if (!directory.exists()) {
					directory.mkdirs();
				}

				File convertFile = new File(directory.getAbsoluteFile() + "/" + filename);
				convertFile.createNewFile();
				FileOutputStream fout = new FileOutputStream(convertFile);
				fout.write(photo.getBytes());
				fout.close();

				legder.setFilename(filename);

				agentLedgerRepo.save(legder);

				respMap.putAll(Util.SuccessResponse());
			} else
				respMap.putAll(Util.FailedResponse("Ledger Not Found...!!!"));

		} catch (Exception e) {
			e.printStackTrace();
			respMap.putAll(Util.FailedResponse(e.getMessage()));
		}
		respMap.put("AgentLedger", legder);
		return respMap;
	}

	@Override
	public Map<String, Object> addAgentLedger(AgentLedger ledger) {
		Map<String, Object> resp = new HashMap<>();
		try {

			ledger = agentLedgerRepo.save(ledger);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("AgentLedger", ledger);
		return resp;
	}

	@Override
	public Map<String, Object> getAccountingReportData(AccountingReportRequest accountingReportReq) {
		Map<String, Object> resp = new HashMap<>();
		List<Map<String, Object>> accountData = new ArrayList<>();

		try {

			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (!accountingReportReq.getType().isEmpty() && accountingReportReq.getType() != null) {
				filterQuery = filterQuery + " and type like '%" + accountingReportReq.getType() + "%'";
			}

			if (!accountingReportReq.getSubject().isEmpty() && accountingReportReq.getSubject() != null) {
				filterQuery = filterQuery + " and subject like '%" + accountingReportReq.getSubject() + "%'";
			}

			if (!accountingReportReq.getMode().isEmpty() && accountingReportReq.getMode() != null) {
				filterQuery = filterQuery + " and mode like '%" + accountingReportReq.getMode() + "%'";
			}

//			if (!accountingReportReq.getCategory().isEmpty() && accountingReportReq.getCategory() != null) {
//				filterQuery = filterQuery + " and category = '" + accountingReportReq.getCategory() + "'";
//			}

			if (accountingReportReq.getPaymentDateFrom() != null && accountingReportReq.getPaymentDateTo() != null) {
				filterQuery = filterQuery + " and payment_Date between '"
						+ sdf.format(accountingReportReq.getPaymentDateFrom()) + "' and '"
						+ sdf.format(accountingReportReq.getPaymentDateTo()) + "'";
			}

			if (accountingReportReq.getCategory() != null && accountingReportReq.getCategory().size() > 0) {
				String category = "'0'";
				for (AccountCategory cate : accountingReportReq.getCategory()) {
					category = category + ",'" + cate.getCategory() + "'";
				}
				filterQuery = filterQuery + " and category in (" + category + ") ";
			}

			if (accountingReportReq.getCreateAgents() != null && accountingReportReq.getCreateAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : accountingReportReq.getCreateAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and created_By in (" + agents + ") ";
			}

			if (accountingReportReq.getAgents() != null && accountingReportReq.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : accountingReportReq.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and id in (" + agents + ") ";
			}

			if (accountingReportReq.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : accountingReportReq.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				filterQuery = filterQuery + " and inst_id in (" + instituteIds + ") ";
			}

			if (accountingReportReq.getVendors() != null && accountingReportReq.getVendors().size() > 0) {
				String vendors = "'0'";
				for (Vendor vendor : accountingReportReq.getVendors()) {
					vendors = vendors + ",'" + vendor.getId() + "'";
				}
				filterQuery = filterQuery + " and vendor_id in (" + vendors + ") ";
			}

			System.out.println(":::FilterQuery::" + filterQuery);

//			accountData=jdbcTemp.queryForList("SELECT * FROM(SELECT * FROM(SELECT b.subject,'Income' AS TYPE,'Deal Payments' AS category,b.created_by,b.MODE,b.total_amount as total_amount,b.amount as amount,b.payment_date,c.institute_name AS NAME,c.institute_id AS inst_id,'' AS vendor_id,'' AS id FROM deals a,deal_payments b,institutes c WHERE a.id=b.deal_id AND a.institute_id=c.institute_id UNION ALL " + 
//					"SELECT b.SUBJECT,'Expense' AS TYPE,'Purchase Bills' AS category,b.created_by,b.MODE,b.total_amount as total_amount,b.amount as amount,b.payment_date,c.vendor_name AS NAME,'' AS inst_id,c.id AS vendor_id,'' AS id FROM bills a,purchase_inputs_bill_payments b,vendors c WHERE a.id=b.bill_id AND a.vendor_id=c.id UNION ALL " + 
//					"SELECT a.SUBJECT,'Expense' AS TYPE,a.journal,'Accounts Team' AS created_by,'' AS MODE,credit as total_amount,'' AS amount,a.payment_date,b.first_name AS NAME,'' AS inst_id,'' AS vendor_id,b.email_id AS id FROM agent_ledger a,agents b WHERE a.agent_email_id=b.email_id UNION ALL " + 
//					"SELECT a.SUBJECT,a.TYPE,a.category,a.created_by,a.mode_of_payment AS MODE,a.amount AS total_amount,a.amount as amount,a.payment_date,b.vendor_name AS NAME,'' AS inst_id,'' AS vendor_id,b.id AS id FROM unspecified_incomes_expenses a,vendors b WHERE a.related_to_supplier_id=b.id  UNION ALL " + 
//					"SELECT a.SUBJECT,a.TYPE,a.category,a.created_by,a.mode_of_payment AS MODE,a.amount AS total_amount,a.amount as amount,a.payment_date,b.first_name AS NAME,'' AS inst_id,'' AS vendor_id,b.email_id AS id FROM unspecified_incomes_expenses a,agents b WHERE a.related_to_agent_id=b.email_id UNION ALL " + 
//					"SELECT CONCAT('Salary for ', salary_month) AS SUBJECT,'Expense' AS TYPE,'Salary Credit' AS category,'Accounts Team' AS created_by,mode_of_payment AS MODE,SUM(net_pay) AS total_amount, SUM(net_pay) AS amount,salary_date AS payment_date,'' AS NAME,'' AS inst_id,'' AS vendor_id,'' AS id FROM salary_entries se GROUP BY salary_month ) acc WHERE 2>1    ) acc WHERE 2>1 " + filterQuery) ;
//			

			accountData = jdbcTemp.queryForList(
					"SELECT * FROM(SELECT b.subject,'Income' AS TYPE,'Deal Payments' AS category,b.created_by,b.MODE,b.total_amount AS total_amount,b.amount AS amount,b.payment_date,c.institute_name AS NAME,c.institute_id AS inst_id,'' AS vendor_id,'' AS id,a.invoice_no AS billno FROM deal_invoices a,deal_payments b,institutes c WHERE a.id=b.invoice_id AND a.institute_id=c.institute_id UNION ALL "
							+ "SELECT b.SUBJECT,'Expense' AS TYPE,'Purchase Bills' AS category,b.created_by,b.MODE,b.total_amount AS total_amount,b.amount AS amount,b.payment_date,c.vendor_name AS NAME,'' AS inst_id,c.id AS vendor_id,'' AS id,a.bill_no AS billno FROM bills a,purchase_inputs_bill_payments b,vendors c WHERE a.id=b.bill_id AND a.vendor_id=c.id UNION ALL "
							+ "SELECT CONCAT('Salary for ', salary_month) AS SUBJECT,'Expense' AS TYPE,'Salary Credit' AS category,'Accounts Team' AS created_by,mode_of_payment AS MODE,SUM(net_pay) AS total_amount, SUM(net_pay) AS amount,salary_credited_date AS payment_date,employee_name AS NAME,'' AS inst_id, '' AS vendor_id, '' AS id,'' AS billno FROM salary_entries GROUP BY salary_month,employee_name UNION ALL "
							+ "SELECT a.SUBJECT,'Expense' AS TYPE,a.journal AS category,'Accounts Team' AS created_by,'' AS MODE,a.credit AS total_amount,'' AS amount,a.payment_date,b.first_name AS NAME,'' AS inst_id,'' AS vendor_id,b.email_id AS id,'' AS billno FROM agent_ledger a,agents b WHERE a.agent_email_id=b.email_id UNION ALL "
							+ "SELECT a.SUBJECT,a.TYPE,a.category AS category,a.created_by,a.mode_of_payment AS MODE,a.amount AS total_amount,a.amount AS amount,payment_date,b.vendor_name AS NAME,'' AS inst_id,'' AS vendor_id,b.id AS id,a.invoice_no AS billno FROM unspecified_incomes_expenses a,vendors b WHERE a.related_to_supplier_id=b.id UNION ALL "
							+ "SELECT a.SUBJECT,a.TYPE,a.category AS category,a.created_by,a.mode_of_payment AS MODE,a.amount AS total_amount,a.amount AS amount,payment_date,'' AS NAME,'' AS inst_id,'' AS vendor_id,'' AS id,'' AS billno FROM unspecified_incomes_expenses a WHERE a.related_to_supplier_id='0') acc WHERE 2>1 "
							+ filterQuery);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}
		resp.put("AccountData", accountData);
		return resp;
	}

	@Override
	public Map<String, Object> getCategoryNeeded() {
		Map<String, Object> resp = new HashMap<>();
		List<Map<String, Object>> categoryList = new ArrayList<>();
		try {

			categoryList = jdbcTemp.queryForList("SELECT DISTINCT 'Deal Payments' AS category FROM deals UNION ALL "
					+ "SELECT DISTINCT 'Purchase Bills' AS category FROM bills UNION ALL "
					+ "SELECT DISTINCT category FROM unspecified_incomes_expenses UNION ALL "
					+ "SELECT DISTINCT 'Salary Credit' AS category FROM salary_entries UNION ALL "
					+ "SELECT DISTINCT journal FROM agent_ledger");

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("categoryList", categoryList);

		return resp;
	}

	@Override
	public Map<String, Object> saveAccountStatement(AccountStatementRequest request) {

		System.out.println("saveAccountStatement DAOImpl method ends::::::");

		Map<String, Object> respMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//		request.setTransactionDate(sdf.format(request.getTransactionDate()));
//		request.setPostDate(sdf.format(request.getPostDate()));

		try {

			accStaRepo.saveAll(request.getAccountStatement());

			respMap.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			respMap.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return respMap;
	}

	public Map<String, Object> getStatementReportData(AccountBankStatementReq req) {
		Map<String, Object> resp = new HashMap<>();

		try {

			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (!req.getRefNo().isEmpty() && req.getRefNo() != null) {
				filterQuery = filterQuery + " and bs.refNo like '%" + req.getRefNo() + "%'";
			}

			if (!req.getDescription().isEmpty() && req.getDescription() != null) {
				filterQuery = filterQuery + " and bs.description like '%" + req.getDescription() + "%'";
			}

			if (req.getTransDateFrom() != null && req.getTransDateTo() != null) {
				filterQuery = filterQuery + " and bs.transactionDate between '" + sdf.format(req.getTransDateFrom())
						+ "' and '" + sdf.format(req.getTransDateTo()) + "'";
			}

			System.out.println(":::FilterQuery::" + filterQuery);

			Query query = em.createQuery("Select bs from AccountStatement bs where 2>1" + filterQuery);
			resp.put("AccountStatementData", query.getResultList());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}
		// resp.put("AccountStatementData", accountStatementData);
		return resp;
	}

	@Override
	public Map<String, Object> saveLetterPad(LetterPad letterpad) {
		Map<String, Object> respMap = new HashMap<>();

		try {

//			if (letterpad.getSubject() == null || letterpad.getSubject().isEmpty())
//				respMap.putAll(Util.invalidMessage("Subject Cannot be empty..!!"));
//			if (letterpad.getContent() == null || letterpad.getContent().isEmpty())
//				respMap.putAll(Util.invalidMessage("Content Cannot be empty..!!"));
//			if (letterpad.getRegardText() == null || letterpad.getRegardText().isEmpty())
//				respMap.putAll(Util.invalidMessage("Regards Cannot be empty..!!"));
//			if (letterpad.getToAddress() == null || letterpad.getToAddress().isEmpty())
//				respMap.putAll(Util.invalidMessage("Address Cannot be empty..!!"));
//			
//			else {

			// letterpad.getInstitute().setInstituteId("5555709");

			letterpad = letterpadRepo.save(letterpad);
			respMap.put("letterpad", letterpad);
			respMap.putAll(Util.SuccessResponse());
			// }

		} catch (Exception ex) {
			respMap.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		return respMap;
	}

	@Override
	public Map<String, Object> getLetterPad(int id) {
		Map<String, Object> respMap = new HashMap<>();
		LetterPad letterpad = new LetterPad();
		Institute inst = new Institute();
		try {

			letterpad = letterpadRepo.findById(id);
			inst = instRepo.findByInstituteId(letterpad.getInstitute().getInstituteId());

			respMap.put("letterpad", letterpad);
			respMap.put("institute", inst);
			respMap.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respMap;
	}

	@Override
	public Map<String, Object> getLetterpadReportPdfTemplate1(LetterpadRequest letterpadReq) {
		Map<String, Object> resp = new HashMap<>();
		LetterPad lp = new LetterPad();

		try {

			System.out.println("EmailId::::::" + letterpadReq.getSignatureBy());

			InputStream stream = null;

			// stream =
			// this.getClass().getResourceAsStream("/reports/Letter_Pad/LetterPad_Template2.jrxml");

			stream = this.getClass().getResourceAsStream("/reports/Letter_Pad/LetterPad_Template1.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			System.out.println("Email Id:::::::" + letterpadReq.getSignatureBy());

			InfoDetails info = infoDetailRepo.findById(1);

			lp = letterpadRepo.findById(letterpadReq.getId());
			letterpadReq.setLetterpad(letterpadRepo.findById(letterpadReq.getId()));
			Agent agent = agentRepo.findByEmailId(letterpadReq.getSignatureBy());
			Institute inst = instRepo.findByInstituteId(lp.getInstitute().getInstituteId());

			System.out.println(info.toString());

			System.out.println("LetterPad Data::::::::" + letterpadReq.getLetterpad());

			parameters.put("cmp_logo_footer", info.getLogoAsFile());
			parameters.put("background_image", info.getBackgroundImageAsFile());
			parameters.put("cmp_logo_url", letterpadReq.isAddLogo() ? info.getLogoAsFile() : null);
			parameters.put("roundseal", letterpadReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", letterpadReq.isAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", letterpadReq.isAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("letterpad_no", "Reference No :  2CQR/" + letterpadReq.getId());

			// parameters.put("header_label", letterpadReq.isAddRoundSeal() ?
			// info.getRoundSealAsFile() : null);
			parameters.put("designation", letterpadReq.getDesignation());

			parameters.put("cmp_name", info.getCmpName());

			String website_template = "";

			if (!info.getCmpWebsiteUrl().equals("") && !info.getCmpWebsiteUrl().equals(null)) {
				website_template = website_template + info.getCmpWebsiteUrl() + "<br>";
			}
			if (!info.getCmpEmail().equals("") && !info.getCmpEmail().equals(null)) {
				website_template = website_template + info.getCmpEmail() + "<br>";
			}
			if (!info.getCmpPhone().equals("") && !info.getCmpPhone().equals(null)) {
				website_template = website_template + info.getCmpPhone() + "/";
			}
			if (!info.getCmpLandLine().equals("") && !info.getCmpLandLine().equals(null)) {
				website_template = website_template + info.getCmpLandLine() + "";
			}

			parameters.put("cmp_website", website_template);
			parameters.put("cmp_address", info.getCompanyAddressHTML1());

			parameters.put("date",
					"Date : " + Util.sdfFormatter(letterpadReq.getLetterpad().getLetterPadDate(), "dd-MM-yyyy"));

			String toaddress_label = "To :" + "<br><br>";
			if (!letterpadReq.getLetterpad().getBillingTo().equals("")
					&& !letterpadReq.getLetterpad().getBillingTo().equals(null)) {

				toaddress_label = toaddress_label + letterpadReq.getLetterpad().getBillingTo() + ",<br>";
			}
			if (!inst.getInstituteName().equals("") && !inst.getInstituteName().equals(null)) {

				toaddress_label = toaddress_label + inst.getInstituteName() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingStreet1().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet1().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingStreet1();
			}
			if (!letterpadReq.getLetterpad().getBillingStreet2().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet2().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingStreet2() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingCity().equals("")
					&& !letterpadReq.getLetterpad().getBillingCity().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingCity() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingState().equals("")
					&& !letterpadReq.getLetterpad().getBillingState().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingState();
			}
			if (!letterpadReq.getLetterpad().getBillingCountry().equals("")
					&& !letterpadReq.getLetterpad().getBillingCountry().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingCountry();
			}

			if (!letterpadReq.getLetterpad().getBillingZIPCode().equals("")
					&& !letterpadReq.getLetterpad().getBillingZIPCode().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingZIPCode();
			}

			// parameters.put("toaddress", toaddress_label);
			// parameters.put("subject", "Sub: " +
			// letterpadReq.getLetterpad().getSubject());
			// parameters.put("content",
			// letterpadReq.getLetterpad().getContent().replaceAll("\n", "<br>"));

			String contentLabel = "";
			contentLabel = contentLabel + toaddress_label + "<br><br>Sub: " + letterpadReq.getLetterpad().getSubject()
					+ "";
			contentLabel = contentLabel + "<br><br>"
					+ letterpadReq.getLetterpad().getContent().replaceAll("\n", "<br>");

			parameters.put("content", contentLabel);

			parameters.put("thanks", "Thanking You");
			parameters.put("header_label", letterpadReq.isAddLetterHead() ? letterpadReq.getHeader() : "LETTERPAD");
			parameters.put("regards", agent.getFirstName() + " " + agent.getLastName() + "<br>"
					+ letterpadReq.getDesignation() + "<br>" + info.getCmpName());

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Letterpads/" + lp.getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			System.out.println("=[==============" + lp.getId());
			letterpadReq.setFilename(lp.getId() + ".pdf");

			letterpadReq.getLetterpad().setFileName(lp.getId() + ".pdf");

			final String filePath = directory.getAbsolutePath() + "/" + letterpadReq.getFilename();
			System.out.println(filePath);

			letterpadRepo.save(letterpadReq.getLetterpad());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			// Merge Preamble if any

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}
		resp.put("Letterpad", letterpadReq.getLetterpad());
		return resp;
	}

	public Map<String, Object> getLetterpadReportPdfTemplate2(LetterpadRequest letterpadReq) {
		Map<String, Object> resp = new HashMap<>();
		LetterPad lp = new LetterPad();

		try {

			System.out.println("EmailId::::::" + letterpadReq.getSignatureBy());

			InputStream stream = null;

			// stream =
			// this.getClass().getResourceAsStream("/reports/Letter_Pad/LetterPad_Template2.jrxml");

			stream = this.getClass().getResourceAsStream("/reports/Letter_Pad/LetterPad_Template2.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			System.out.println("Email Id:::::::" + letterpadReq.getSignatureBy());

			InfoDetails info = infoDetailRepo.findById(1);

			lp = letterpadRepo.findById(letterpadReq.getId());
			letterpadReq.setLetterpad(letterpadRepo.findById(letterpadReq.getId()));
			Agent agent = agentRepo.findByEmailId(letterpadReq.getSignatureBy());
			Institute inst = instRepo.findByInstituteId(lp.getInstitute().getInstituteId());

			System.out.println(info.toString());

			System.out.println("LetterPad Data::::::::" + letterpadReq.getLetterpad());

			parameters.put("cmp_logo_footer", info.getLogoAsFile());
			parameters.put("cmp_logo_url", letterpadReq.isAddLogo() ? info.getLogoAsFile() : null);
			parameters.put("roundseal", letterpadReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", letterpadReq.isAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", letterpadReq.isAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("letterpad_no", "Reference No :  AL/" + letterpadReq.getId());

			// parameters.put("header_label", letterpadReq.isAddRoundSeal() ?
			// info.getRoundSealAsFile() : null);
			parameters.put("designation", letterpadReq.getDesignation());

			parameters.put("cmp_name", info.getCmpName());

			String website_template = "";

			if (!info.getCmpWebsiteUrl().equals("") && !info.getCmpWebsiteUrl().equals(null)) {
				website_template = website_template + info.getCmpWebsiteUrl() + "<br>";
			}
			if (!info.getCmpEmail().equals("") && !info.getCmpEmail().equals(null)) {
				website_template = website_template + info.getCmpEmail() + "<br>";
			}
			if (!info.getCmpPhone().equals("") && !info.getCmpPhone().equals(null)) {
				website_template = website_template + info.getCmpPhone() + "/";
			}
			if (!info.getCmpLandLine().equals("") && !info.getCmpLandLine().equals(null)) {
				website_template = website_template + info.getCmpLandLine() + "";
			}

			parameters.put("cmp_website", website_template);
			parameters.put("cmp_address", info.getCompanyAddressHTML1());

			parameters.put("date",
					"Date : " + Util.sdfFormatter(letterpadReq.getLetterpad().getLetterPadDate(), "dd-MM-yyyy"));

			String toaddress_label = "To :" + "<br><br>";
			if (!letterpadReq.getLetterpad().getBillingTo().equals("")
					&& !letterpadReq.getLetterpad().getBillingTo().equals(null)) {

				toaddress_label = toaddress_label + letterpadReq.getLetterpad().getBillingTo() + ",<br>";
			}
			if (!inst.getInstituteName().equals("") && !inst.getInstituteName().equals(null)) {

				toaddress_label = toaddress_label + inst.getInstituteName() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingStreet1().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet1().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingStreet1();
			}
			if (!letterpadReq.getLetterpad().getBillingStreet2().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet2().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingStreet2() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingCity().equals("")
					&& !letterpadReq.getLetterpad().getBillingCity().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingCity() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingState().equals("")
					&& !letterpadReq.getLetterpad().getBillingState().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingState();
			}
			if (!letterpadReq.getLetterpad().getBillingCountry().equals("")
					&& !letterpadReq.getLetterpad().getBillingCountry().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingCountry();
			}

			if (!letterpadReq.getLetterpad().getBillingZIPCode().equals("")
					&& !letterpadReq.getLetterpad().getBillingZIPCode().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingZIPCode();
			}

			// parameters.put("toaddress", toaddress_label);
			// parameters.put("subject", "Sub: " +
			// letterpadReq.getLetterpad().getSubject());
			// parameters.put("content",
			// letterpadReq.getLetterpad().getContent().replaceAll("\n", "<br>"));

			String contentLabel = "";
			contentLabel = contentLabel + toaddress_label + "<br><br>Sub: " + letterpadReq.getLetterpad().getSubject()
					+ "";
			contentLabel = contentLabel + "<br><br>"
					+ letterpadReq.getLetterpad().getContent().replaceAll("\n", "<br>");

			parameters.put("content", contentLabel);

			parameters.put("thanks", "Thanking You");
			parameters.put("header_label", letterpadReq.isAddLetterHead() ? letterpadReq.getHeader() : "LETTERPAD");
			parameters.put("regards", agent.getFirstName() + " " + agent.getLastName() + "<br>"
					+ letterpadReq.getDesignation() + "<br>" + info.getCmpName());

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Letterpads/" + lp.getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			System.out.println("=[==============" + lp.getId());
			letterpadReq.setFilename(lp.getId() + ".pdf");

			letterpadReq.getLetterpad().setFileName(lp.getId() + ".pdf");

			final String filePath = directory.getAbsolutePath() + "/" + letterpadReq.getFilename();
			System.out.println(filePath);

			letterpadRepo.save(letterpadReq.getLetterpad());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			// Merge Preamble if any

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}
		resp.put("Letterpad", letterpadReq.getLetterpad());
		return resp;
	}

	@Override
	public Map<String, Object> deleteLetterPad(LetterPad letterpad) {

		Map<String, Object> respMap = new HashMap<>();
		try {

			letterpadRepo.delete(letterpad);
			respMap.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			respMap.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		return respMap;
	}

	public Map<String, Object> getAllLetterPad(LetterpadRequest req) {
		Map<String, Object> respMap = new HashMap<>();
		List<LetterPad> list = new ArrayList<>();
		List<Map<String, Object>> categoryList = new ArrayList<>();
		try {

			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (req.getInstitutes().size() > 0 && req.getInstitutes() != null) {

				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				filterQuery = filterQuery + " and i.institute_id in (" + instituteIds + ") ";
			}

			if (req.getId() != 0) {
				filterQuery = filterQuery + " and lp.id ='" + req.getId() + "'";
			}

			if (!req.getSubject().isEmpty() && req.getSubject() != null) {
				filterQuery = filterQuery + " and lp.subject like '" + req.getSubject() + "%'";
			}

			if (req.getLatterpadDateFrom() != null && req.getLatterpadDateTo() != null) {
				filterQuery = filterQuery + " and lp.date between '" + sdf.format(req.getLatterpadDateFrom())
						+ "' and '" + sdf.format(req.getLatterpadDateTo()) + "'";
			}

			System.out.println(":::FilterQuery::" + filterQuery);

			categoryList = jdbcTemp.queryForList(
					"SELECT lp.id AS id,lp.subject AS SUBJECT,lp.content AS content,lp.date AS DATE,lp.file_name AS file_name,i.institute_id AS institute_id,i.institute_name AS institute_name FROM letterpad lp JOIN institutes i ON(lp.institute_id = i.institute_id) WHERE 2>1 "
							+ filterQuery+" ORDER BY lp.id DESC");

//			Query query = em.createQuery("Select lp from letterpad lp where 2>1" + filterQuery);
			respMap.put("letterpadData", categoryList);
			respMap.putAll(Util.SuccessResponse());

			// list = letterpadRepo.findAll();

		} catch (Exception ex) {
			ex.printStackTrace();
			respMap.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return respMap;
	}

	@Override
	public Map<String, Object> UploadGeneratedLetterpadPDF(int id, MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		LetterPad letterpad = letterpadRepo.findById(id);

		File directory = new File(contentPath + "/Letterpads/" + id);
		System.out.println(directory.getAbsolutePath());
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File convertFile = new File(directory.getAbsoluteFile() + "/" + file.getOriginalFilename());

		try {
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();

			letterpad.setFileName(file.getOriginalFilename());
			letterpad = letterpadRepo.save(letterpad);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("LetterPad", letterpad);
		return resp;
	}

	@Override
	public Map<String, Object> getLetterpadReportPdfTemplate3(LetterpadRequest letterpadReq) {
		Map<String, Object> resp = new HashMap<>();
		LetterPad lp = new LetterPad();

		try {

			System.out.println("EmailId::::::" + letterpadReq.getSignatureBy());

			InputStream stream = null;

			// stream =
			// this.getClass().getResourceAsStream("/reports/Letter_Pad/LetterPad_Template2.jrxml");

			stream = this.getClass().getResourceAsStream("/reports/Letter_Pad/Template_3_2CQR.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			System.out.println("Email Id:::::::" + letterpadReq.getSignatureBy());

			InfoDetails info = infoDetailRepo.findById(1);

			lp = letterpadRepo.findById(letterpadReq.getId());
			letterpadReq.setLetterpad(letterpadRepo.findById(letterpadReq.getId()));
			Agent agent = agentRepo.findByEmailId(letterpadReq.getSignatureBy());
			Institute inst = instRepo.findByInstituteId(lp.getInstitute().getInstituteId());

			//

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			// parameters.put("cmp_logo_url", info.getLogoAsFile());
//			parameters.put("roundseal", letterpadReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);

			parameters.put("cmp_logo_url", letterpadReq.isAddLogo() ? info.getLogoAsFile() : null);
			parameters.put("roundseal", letterpadReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);

			parameters.put("fullseal", letterpadReq.isAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", letterpadReq.isAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", letterpadReq.getDesignation());

			parameters.put("dealtype_label", !letterpadReq.isAddLetterHead() ? letterpadReq.getHeader() : "LETTER");

			String website_template = "";

			if (!info.getCmpWebsiteUrl().equals("") && !info.getCmpWebsiteUrl().equals(null)) {
				website_template = website_template + info.getCmpWebsiteUrl() + "<br>";
			}
			if (!info.getCmpEmail().equals("") && !info.getCmpEmail().equals(null)) {
				website_template = website_template + info.getCmpEmail() + "<br>";
			}
			if (!info.getCmpPhone().equals("") && !info.getCmpPhone().equals(null)) {
				website_template = website_template + info.getCmpPhone() + "/";
			}
			if (!info.getCmpLandLine().equals("") && !info.getCmpLandLine().equals(null)) {
				website_template = website_template + info.getCmpLandLine() + "";
			}

			parameters.put("cmp_website", website_template);
			parameters.put("cmp_address", info.getCompanyAddressHTML1());

			parameters.put("date",
					"Date : " + Util.sdfFormatter(letterpadReq.getLetterpad().getLetterPadDate(), "dd-MM-yyyy"));

			String toaddress_label = "<font color ='red'><b>TO :</b></font><br>";
			if (!letterpadReq.getLetterpad().getBillingTo().equals("")
					&& !letterpadReq.getLetterpad().getBillingTo().equals(null)) {

				toaddress_label = toaddress_label + letterpadReq.getLetterpad().getBillingTo() + ",<br>";
			}
			if (!inst.getInstituteName().equals("") && !inst.getInstituteName().equals(null)) {

				toaddress_label = toaddress_label + inst.getInstituteName() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingStreet1().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet1().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingStreet1();
			}
			if (!letterpadReq.getLetterpad().getBillingStreet2().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet2().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingStreet2() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingCity().equals("")
					&& !letterpadReq.getLetterpad().getBillingCity().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingCity() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingState().equals("")
					&& !letterpadReq.getLetterpad().getBillingState().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingState();
			}
			if (!letterpadReq.getLetterpad().getBillingCountry().equals("")
					&& !letterpadReq.getLetterpad().getBillingCountry().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingCountry();
			}

			if (!letterpadReq.getLetterpad().getBillingZIPCode().equals("")
					&& !letterpadReq.getLetterpad().getBillingZIPCode().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingZIPCode();
			}

			String contentLabel = "";
			String subjectLabel = "";

			subjectLabel = subjectLabel + "Sub: " + letterpadReq.getLetterpad().getSubject() + "<br>";
			contentLabel = contentLabel + "<br>" + letterpadReq.getLetterpad().getContent().replaceAll("\n", "<br>");

			String deal_date_label = "", deal_date_text = "";

			// parameters.put("letterpad_no", "Reference No : 2CQR/"+letterpadReq.getId());

			// deal_date_label = deal_date_label + "Reference No :
			// 2CQR/"+letterpadReq.getId()+"<br>";

			deal_date_label = deal_date_label + "Reference No : <br>";
			deal_date_text = deal_date_text + "2CQR/" + letterpadReq.getId() + "<br>";
			deal_date_label = deal_date_label + "Date : <br>";
			deal_date_text = deal_date_text
					+ Util.sdfFormatter(letterpadReq.getLetterpad().getLetterPadDate(), "dd-MM-yyyy") + "<br>";
			/*
			 * deal_date_label = deal_date_label + "PO No : <br>"; deal_date_text =
			 * deal_date_text +
			 * String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()) +
			 * "<br>";
			 */

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			parameters.put("billing_to", toaddress_label);
			// parameters.put("subject", subjectLabel);
			parameters.put("content", subjectLabel + contentLabel);
			parameters.put("thanks", "Thanking You");
			parameters.put("regards", agent.getFirstName() + " " + agent.getLastName() + "<br>"
					+ letterpadReq.getDesignation() + "<br>" + info.getCmpName());

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Letterpads/" + lp.getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			System.out.println("=[==============" + lp.getId());
			letterpadReq.setFilename(lp.getId() + ".pdf");

			letterpadReq.getLetterpad().setFileName(lp.getId() + ".pdf");

			final String filePath = directory.getAbsolutePath() + "/" + letterpadReq.getFilename();
			System.out.println(filePath);

			letterpadRepo.save(letterpadReq.getLetterpad());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			// Merge Preamble if any

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}
		resp.put("Letterpad", letterpadReq.getLetterpad());
		return resp;
	}

	@Override
	public Map<String, Object> getLetterpadReportPdfTemplate4(LetterpadRequest letterpadReq) {
		Map<String, Object> resp = new HashMap<>();
		LetterPad lp = new LetterPad();

		try {

			System.out.println("EmailId::::::" + letterpadReq.getSignatureBy());

			InputStream stream = null;

			// stream =
			// this.getClass().getResourceAsStream("/reports/Letter_Pad/LetterPad_Template2.jrxml");

			stream = this.getClass().getResourceAsStream("/reports/Letter_Pad/Template_4_AutoLib.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			System.out.println("Email Id:::::::" + letterpadReq.getSignatureBy());

			InfoDetails info = infoDetailRepo.findById(1);

			lp = letterpadRepo.findById(letterpadReq.getId());
			letterpadReq.setLetterpad(letterpadRepo.findById(letterpadReq.getId()));
			Agent agent = agentRepo.findByEmailId(letterpadReq.getSignatureBy());
			Institute inst = instRepo.findByInstituteId(lp.getInstitute().getInstituteId());

			//

			parameters.put("cmp_name", "<font color='#000066'>" + info.getCmpName() + "</font>");
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			// parameters.put("cmp_logo_url", info.getLogoAsFile());
//			parameters.put("roundseal", letterpadReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);

			parameters.put("cmp_logo_url", letterpadReq.isAddLogo() ? info.getLogoAsFile() : null);
			parameters.put("roundseal", letterpadReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);

			parameters.put("fullseal", letterpadReq.isAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", letterpadReq.isAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", letterpadReq.getDesignation());

			parameters.put("dealtype_label", !letterpadReq.isAddLetterHead() ? letterpadReq.getHeader() : "");

			String website_template = "";

			if (!info.getCmpWebsiteUrl().equals("") && !info.getCmpWebsiteUrl().equals(null)) {
				website_template = website_template + info.getCmpWebsiteUrl() + "<br>";
			}
			if (!info.getCmpEmail().equals("") && !info.getCmpEmail().equals(null)) {
				website_template = website_template + info.getCmpEmail() + "<br>";
			}
			if (!info.getCmpPhone().equals("") && !info.getCmpPhone().equals(null)) {
				website_template = website_template + info.getCmpPhone() + "/";
			}
			if (!info.getCmpLandLine().equals("") && !info.getCmpLandLine().equals(null)) {
				website_template = website_template + info.getCmpLandLine() + "";
			}

			parameters.put("cmp_website", website_template);
			parameters.put("cmp_address", info.getCompanyAddressHTML1());

			parameters.put("date",
					"Date : " + Util.sdfFormatter(letterpadReq.getLetterpad().getLetterPadDate(), "dd-MM-yyyy"));

			String toaddress_label = "<b>TO :</b><br>";
			if (!letterpadReq.getLetterpad().getBillingTo().equals("")
					&& !letterpadReq.getLetterpad().getBillingTo().equals(null)) {

				toaddress_label = toaddress_label + letterpadReq.getLetterpad().getBillingTo() + ",<br>";
			}
			if (!inst.getInstituteName().equals("") && !inst.getInstituteName().equals(null)) {

				toaddress_label = toaddress_label + inst.getInstituteName() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingStreet1().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet1().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingStreet1();
			}
			if (!letterpadReq.getLetterpad().getBillingStreet2().equals("")
					&& !letterpadReq.getLetterpad().getBillingStreet2().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingStreet2() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingCity().equals("")
					&& !letterpadReq.getLetterpad().getBillingCity().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingCity() + ",";
			}
			if (!letterpadReq.getLetterpad().getBillingState().equals("")
					&& !letterpadReq.getLetterpad().getBillingState().equals(null)) {

				toaddress_label = toaddress_label + "<br>" + letterpadReq.getLetterpad().getBillingState();
			}
			if (!letterpadReq.getLetterpad().getBillingCountry().equals("")
					&& !letterpadReq.getLetterpad().getBillingCountry().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingCountry();
			}

			if (!letterpadReq.getLetterpad().getBillingZIPCode().equals("")
					&& !letterpadReq.getLetterpad().getBillingZIPCode().equals(null)) {

				toaddress_label = toaddress_label + "," + letterpadReq.getLetterpad().getBillingZIPCode();
			}

			String contentLabel = "";
			String subjectLabel = "";

			subjectLabel = subjectLabel + "Sub: " + letterpadReq.getLetterpad().getSubject() + "<br>";
			contentLabel = contentLabel + "<br>" + letterpadReq.getLetterpad().getContent().replaceAll("\n", "<br>");

			String deal_date_label = "", deal_date_text = "";

			// parameters.put("letterpad_no", "Reference No : 2CQR/"+letterpadReq.getId());

			// deal_date_label = deal_date_label + "Reference No :
			// 2CQR/"+letterpadReq.getId()+"<br>";

			deal_date_label = deal_date_label + "Reference No : <br>";
			deal_date_text = deal_date_text + "AL/" + letterpadReq.getId() + "<br>";
			deal_date_label = deal_date_label + "Date : <br>";
			deal_date_text = deal_date_text
					+ Util.sdfFormatter(letterpadReq.getLetterpad().getLetterPadDate(), "dd-MM-yyyy") + "<br>";
			/*
			 * deal_date_label = deal_date_label + "PO No : <br>"; deal_date_text =
			 * deal_date_text +
			 * String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()) +
			 * "<br>";
			 */

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			parameters.put("billing_to", toaddress_label);
			// parameters.put("subject", subjectLabel);
			parameters.put("content", subjectLabel + contentLabel);
			parameters.put("thanks", "Thanking You");
			parameters.put("regards", agent.getFirstName() + " " + agent.getLastName() + "<br>"
					+ letterpadReq.getDesignation() + "<br>" + info.getCmpName());

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Letterpads/" + lp.getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			System.out.println("=[==============" + lp.getId());
			letterpadReq.setFilename(lp.getId() + ".pdf");

			letterpadReq.getLetterpad().setFileName(lp.getId() + ".pdf");

			final String filePath = directory.getAbsolutePath() + "/" + letterpadReq.getFilename();
			System.out.println(filePath);

			letterpadRepo.save(letterpadReq.getLetterpad());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			// Merge Preamble if any

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse());
			e.printStackTrace();
		}
		resp.put("Letterpad", letterpadReq.getLetterpad());
		return resp;
	}

	@Override
	public Map<String, Object> getAmcReport(AmcReportRequest amcReport) {
		Map<String, Object> respMap = new HashMap<>();
		List<Map<String, Object>> amcReportData = new ArrayList<>();
		try {
			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-DD-mm");

			
			  if(amcReport.getInstitutes().size()>0) { String inst="0"; 
			  for(Institute institute: amcReport.getInstitutes()) {
			  
			  inst = inst +","+institute.getInstituteId(); } filterQuery = filterQuery +
			  "AND i.institute_id in ("+inst+")"; }
			  
			  
			  if(amcReport.getProducts().size()>0) { 
				  String prod="0"; 
				  for(Product product:amcReport.getProducts()) {
			  
			      prod = prod +","+product.getId(); } 
				  filterQuery = filterQuery +" AND p.product_id in ("+prod+")"; 
				  }
			  
			  if(amcReport.getPaymentFromDate()!=null &&
			  amcReport.getPaymentToDate()!=null) {
			  
			  filterQuery = filterQuery
			  +" AND dp.payment_date between '"+sdf.format(amcReport.getPaymentFromDate())
			  +"' AND '"+sdf.format(amcReport.getPaymentToDate())+"' "; }
			  
			  if(amcReport.getFromDate()!=null && amcReport.getToDate()!=null) {
			  
			  filterQuery = filterQuery
			  +" AND d.amc_to_date between '"+sdf.format(amcReport.getFromDate())
			  +"' AND '"+sdf.format(amcReport.getToDate())+"' "; 
			  }
			 

			
			/*
			 * Query query = em.createQuery(
			 * "SELECT new com.autolib.helpdesk.Accounting.model.AmcReportResponse(dp,d) " +
			 * "FROM DealPayments dp JOIN Deal d ON (dp.dealId=d.id) " + filterQuery,
			 * AmcReportResponse.class);
			 */

			
			  amcReportData=jdbcTemp.
			  queryForList("SELECT i.institute_name,dp.payment_date,dp.total_amount AS paid_amount,dp.mode,d.amc_from_date,d.amc_to_date,p.name,di.invoice_no,di.invoice_date FROM deal_payments dp JOIN deals d   ON(d.id=dp.deal_id) JOIN institutes i ON (d.institute_id=i.institute_id AND d.deal_type='AMC') JOIN deal_products p ON(d.id=p.deal_id)\r\n"
			  + " JOIN deal_invoices di ON(d.id=di.deal_id) WHERE 2>1 "+filterQuery );
			 
			respMap.put("amcList", amcReportData);
			respMap.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			ex.printStackTrace();
			respMap.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return respMap;
	}

}
