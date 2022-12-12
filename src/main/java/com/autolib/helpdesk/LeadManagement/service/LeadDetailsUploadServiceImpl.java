package com.autolib.helpdesk.LeadManagement.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.LeadManagement.model.Lead;
import com.autolib.helpdesk.LeadManagement.model.LeadContact;
import com.autolib.helpdesk.LeadManagement.model.LeadUploadContactDetail;
import com.autolib.helpdesk.LeadManagement.model.LeadUploadDetail;
import com.autolib.helpdesk.common.Util;

@Service
public class LeadDetailsUploadServiceImpl implements LeadDetailsUploadService {
	private final Logger logger = LogManager.getLogger(this.getClass());
	private Map<String, Object> resp = new HashMap<>();

	private List<LeadUploadDetail> leadUploadDetails = new ArrayList<>();
	private List<LeadUploadContactDetail> contactDetails = new ArrayList<>();
	private Workbook workbook = null;
	private DataFormatter formatter = new DataFormatter();
	List<Agent> agents = new ArrayList<>();
	private Map<String, String> leadStatus = new HashMap<>();

	@Autowired
	AgentRepository agentRepo;

	public LeadDetailsUploadServiceImpl() {
		super();
		leadStatus.put("new lead", "New Lead");
		leadStatus.put("attempted to contact", "Attempted to Contact");
		leadStatus.put("already purchased", "Already purchased");
		leadStatus.put("not required", "Not Required");
		leadStatus.put("contact in future", "Contact in Future");
		leadStatus.put("intrested", "Intrested");
		leadStatus.put("under processing", "Under Processing");
		leadStatus.put("immediate requirement", "Immediate Requirement");
		leadStatus.put("converted to sales", "Converted to Sales");
	}

	@Override
	public Map<String, Object> validateLeadDetailExcel(File uploadedfile) throws IOException {
		logger.info("validateLeadDetailExcel Started");

		extractLeadDetailsFromExcel(uploadedfile);

		extractLeadContactsFromExcel();

		agents = agentRepo.findAllMinDetails();

		validateLeadDetails();

		validateLeadContacts();

		logger.info("validateLeadDetailExcel Completed :: " + resp);
		return resp;
	}

	private void validateLeadDetails() {
		// TODO Auto-generated method stub
		validateDuplicateSerialNoErrors();

		validateOwnerEmailIds();

		populateOwnerName();

		validateCompany();

//		validateLeadStatus();

		validateSendEmailUpdates();

		validateActiveInActiveLeads();

		validateLeadDates();
	}

	private void validateLeadContacts() {
		// TODO Auto-generated method stub
		validateContactSerialNo();

		validateContactEmailId();

		validateContactAlternateEmailId();

		validateActiveInActiveContacts();
	}

	private void validateActiveInActiveContacts() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		contactDetails.forEach(_contact -> {
			if (!Arrays.asList("YES", "NO").contains(_contact.getActive().toUpperCase()))
				unknown.put(_contact.getRowNo(), _contact.getActive());
		});
		if (!unknown.isEmpty())
			resp.put("Contact_ActiveInActiveError", unknown);

	}

	private void validateContactAlternateEmailId() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		contactDetails.forEach(_contact -> {
			if (!_contact.getAlternateEmail().isEmpty() && !Util.validateEmailID(_contact.getAlternateEmail()))
				unknown.put(_contact.getRowNo(), _contact.getAlternateEmail());
		});
		if (!unknown.isEmpty())
			resp.put("Contact_AlternateEmailIds", unknown);
	}

	private void validateContactEmailId() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		contactDetails.forEach(_contact -> {
			if (!_contact.getEmailId().isEmpty() && !Util.validateEmailID(_contact.getEmailId()))
				unknown.put(_contact.getRowNo(), _contact.getEmailId());
		});
		if (!unknown.isEmpty())
			resp.put("Contact_EmailIds", unknown);
	}

	private void validateContactSerialNo() {
		List<String> leadSnos = leadUploadDetails.stream().map(LeadUploadDetail::getSno).distinct()
				.collect(Collectors.toList());
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		contactDetails.forEach(_contact -> {
			if (!leadSnos.contains(_contact.getSno()))
				unknown.put(_contact.getRowNo(), _contact.getSno());
		});
		if (!unknown.isEmpty())
			resp.put("Contact_SerialNos", unknown);
	}

	private void validateCompany() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		leadUploadDetails.stream().filter(_lead -> _lead.getLeadDate().isEmpty()).forEach(_lead -> {
			unknown.put(_lead.getRowNo(), "");
		});
		if (!unknown.isEmpty())
			resp.put("Lead_Company", unknown);

	}

	private void validateLeadDates() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		leadUploadDetails.stream().filter(_lead -> !_lead.getLeadDate().isEmpty()).forEach(_lead -> {
			System.out.println("_lead.getLeadDate()::" + _lead.getLeadDate());
			if (!_lead.getLeadDate().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
				unknown.put(_lead.getRowNo(), "' " + _lead.getLeadDate() + " '");
		});
		if (!unknown.isEmpty())
			resp.put("Lead_Date", unknown);
	}

	private void validateActiveInActiveLeads() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		leadUploadDetails.forEach(_lead -> {
			if (!Arrays.asList("YES", "NO").contains(_lead.getActiveLead().toUpperCase()))
				unknown.put(_lead.getRowNo(), _lead.getActiveLead());
		});
		if (!unknown.isEmpty())
			resp.put("Lead_ActiveInActive", unknown);
	}

	private void validateSendEmailUpdates() {
		// TODO Auto-generated method stub
		Map<Integer, String> unknown = new TreeMap<>();
		leadUploadDetails.forEach(_lead -> {
			if (!Arrays.asList("YES", "NO").contains(_lead.getSendEmailUpdates().toUpperCase()))
				unknown.put(_lead.getRowNo(), _lead.getSendEmailUpdates());
		});
		if (!unknown.isEmpty())
			resp.put("Lead_SendEmailUpdates", unknown);
	}

	private void validateLeadStatus() {
		// TODO Auto-generated method stub

		Map<Integer, String> unknownStatus = new TreeMap<>();
		leadUploadDetails.forEach(_lead -> {
			if (!leadStatus.containsKey(_lead.getStatus().toLowerCase()))
				unknownStatus.put(_lead.getRowNo(), _lead.getStatus());
		});
		if (!unknownStatus.isEmpty())
			resp.put("Lead_Status", unknownStatus);

	}

	void populateOwnerName() {

		leadUploadDetails.stream().filter(_lead -> _lead.getOwnerName().isEmpty())
				.forEach(lead -> lead.setOwnerName(getAgentname(lead)));

	}

	private String getAgentname(LeadUploadDetail lead) {
		Optional<Agent> agent = agents.stream()
				.filter(_agent -> _agent.getEmailId().equalsIgnoreCase(lead.getOwnerEmailId())).findFirst();
		return agent.isPresent() ? agent.get().getEmailId() : "";
	}

	private void validateOwnerEmailIds() {
		// TODO Auto-generated method stub
		List<String> emailIds = agents.stream().map(Agent::getEmailId).distinct().collect(Collectors.toList());
		System.out.println(emailIds.size() + " :: " + emailIds.toString());
		Map<Integer, String> unknownOwner = new TreeMap<>();
		leadUploadDetails.forEach(_lead -> {
			if (!Util.validateEmailID(_lead.getOwnerEmailId()))
				unknownOwner.put(_lead.getRowNo(), _lead.getOwnerEmailId());
			else if (!emailIds.contains(_lead.getOwnerEmailId()))
				unknownOwner.put(_lead.getRowNo(), _lead.getOwnerEmailId() + " <unknown>");
		});
		if (!unknownOwner.isEmpty())
			resp.put("Lead_OwnerEmailId", unknownOwner);
	}

	private void validateDuplicateSerialNoErrors() {
		// TODO Auto-generated method stub

		// Check for Duplicate Serial No.s in Lead_details
		Set<String> set = new HashSet<>();
		Map<Integer, String> duplicates = new TreeMap<>();
		leadUploadDetails.forEach(_lead -> {
			if (!set.add(_lead.getSno())) {
				duplicates.put(_lead.getRowNo(), _lead.getSno());
			}
		});
		if (!duplicates.isEmpty())
			resp.put("Lead_DuplicateSerialNo", duplicates);
	}

	private void extractLeadDetailsFromExcel(File uploadedfile) {
		// TODO Auto-generated method stub

		leadUploadDetails = new ArrayList<>();
		contactDetails = new ArrayList<>();
		workbook = null;
		formatter = new DataFormatter();
		agents = new ArrayList<>();

		try {
			workbook = WorkbookFactory.create(uploadedfile);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			resp.put("error", "Couldn't read data from Excel");
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		logger.info("extractLeadDetailsFromExcel Started");
		Sheet sheet = workbook.getSheet("Lead_Details");

		sheet.removeRow(sheet.getRow(0));

		Iterator<Row> rows = sheet.iterator();

		while (rows.hasNext()) {
			Row currentRow = rows.next();
			logger.info("Lead currentRow :: " + formatter.formatCellValue(currentRow.getCell(0)));
			LeadUploadDetail leadUploadDetail = new LeadUploadDetail(currentRow.getRowNum());

			leadUploadDetail.setSno(formatter.formatCellValue(currentRow.getCell(0)));
			leadUploadDetail.setOwnerEmailId(formatter.formatCellValue(currentRow.getCell(1)));
			leadUploadDetail.setOwnerName(formatter.formatCellValue(currentRow.getCell(2)));
			leadUploadDetail.setCompany(formatter.formatCellValue(currentRow.getCell(3)));
			leadUploadDetail.setContactPersonFullName(formatter.formatCellValue(currentRow.getCell(4)));
			leadUploadDetail.setLeadTitle(formatter.formatCellValue(currentRow.getCell(5)));
			leadUploadDetail.setLeadSource(formatter.formatCellValue(currentRow.getCell(6)));
			leadUploadDetail.setCategory(formatter.formatCellValue(currentRow.getCell(7)));
			leadUploadDetail.setStatus(formatter.formatCellValue(currentRow.getCell(8)));
			leadUploadDetail.setIndustryType(formatter.formatCellValue(currentRow.getCell(9)));
			leadUploadDetail.setNoOfEmployees(formatter.formatCellValue(currentRow.getCell(10)));
			leadUploadDetail.setAnnualRevenue(formatter.formatCellValue(currentRow.getCell(11)));
			leadUploadDetail.setStreet(formatter.formatCellValue(currentRow.getCell(12)));
			leadUploadDetail.setCity(formatter.formatCellValue(currentRow.getCell(13)));
			leadUploadDetail.setState(formatter.formatCellValue(currentRow.getCell(14)));
			leadUploadDetail.setCountry(formatter.formatCellValue(currentRow.getCell(15)));
			leadUploadDetail.setPincode(formatter.formatCellValue(currentRow.getCell(16)));
			leadUploadDetail.setWebsite(formatter.formatCellValue(currentRow.getCell(17)));
			leadUploadDetail.setSendEmailUpdates(formatter.formatCellValue(currentRow.getCell(18)));
			leadUploadDetail.setActiveLead(formatter.formatCellValue(currentRow.getCell(19)));
			leadUploadDetail.setDescription(formatter.formatCellValue(currentRow.getCell(20)));
			leadUploadDetail.setProducts(formatter.formatCellValue(currentRow.getCell(21)));

			try {
				if (HSSFDateUtil.isCellDateFormatted(currentRow.getCell(22))) {
					leadUploadDetail.setLeadDate(Util.sdfFormatter(
							HSSFDateUtil.getJavaDate(currentRow.getCell(22).getNumericCellValue()), "dd/MM/yyyy"));
				}
			} catch (Exception e) {
				System.err.println("Error Lead Date at Row : " + currentRow.getRowNum() + " : " + e.getMessage());
				leadUploadDetail.setLeadDate("");
			}

			System.out.println(currentRow.getRowNum() + " :: " + leadUploadDetail.toString());
			leadUploadDetails.add(leadUploadDetail);
		}

		logger.info("extractLeadDetailsFromExcel Completed:::" + leadUploadDetails.size());

	}

	private void extractLeadContactsFromExcel() {
		// TODO Auto-generated method stub

		logger.info("extractLeadContactsFromExcel Started");

		Sheet sheet = workbook.getSheet("Lead_Contacts");

		sheet.removeRow(sheet.getRow(0));

		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			Row currentRow = rows.next();

			logger.info("Contact currentRow:: " + formatter.formatCellValue(currentRow.getCell(0)));

			LeadUploadContactDetail leadContact = new LeadUploadContactDetail(currentRow.getRowNum());

			leadContact.setSno(formatter.formatCellValue(currentRow.getCell(0)));
			leadContact.setName(formatter.formatCellValue(currentRow.getCell(1)));
			leadContact.setDesignation(formatter.formatCellValue(currentRow.getCell(2)));
			leadContact.setEmailId(formatter.formatCellValue(currentRow.getCell(3)));
			leadContact.setPhoneNo(formatter.formatCellValue(currentRow.getCell(4)));
			leadContact.setAlternateEmail(formatter.formatCellValue(currentRow.getCell(5)));
			leadContact.setAlternatePhoneNo(formatter.formatCellValue(currentRow.getCell(6)));
			leadContact.setActive(formatter.formatCellValue(currentRow.getCell(7)));

			System.out.println(leadContact.getRowNo() + " :: " + leadContact.toString());
			contactDetails.add(leadContact);
		}

		logger.info("extractLeadContactsFromExcel Completed:::" + contactDetails.size());

	}

	@Override
	public List<LeadUploadDetail> leadMapper(File file) {
		// TODO Auto-generated method stub

		extractLeadDetailsFromExcel(file);

		extractLeadContactsFromExcel();

		leadUploadDetails.forEach(_lead -> {
			_lead.setContactDetails(contactDetails.stream().filter(_contact -> _contact.getRowNo() == _lead.getRowNo())
					.collect(Collectors.toList()));
		});

		return leadUploadDetails;
	}

}
