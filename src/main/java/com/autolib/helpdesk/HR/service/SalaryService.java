package com.autolib.helpdesk.HR.service;

import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.HR.entity.SalaryDetails;
import com.autolib.helpdesk.HR.entity.SalaryDetailsRequest;
import com.autolib.helpdesk.HR.entity.SalaryEntries;
import com.autolib.helpdesk.HR.entity.SalaryEntriesRequest;

public interface SalaryService {

	List<String> bankname(String type);

	Map<String, Object> saveSalaryDetails(SalaryDetailsRequest salary);

	Map<String, Object> getStaffDetails();

	Map<String, Object> getStaffDetailsEdit(String sid);

	Map<String, Object> deleteStaffDetails(SalaryDetails salaryDetail);

	Map<String, Object> generateSalarayEntry(SalaryEntriesRequest req);

	Map<String, Object> getSalaryEntries(SalaryEntriesRequest request);

	Map<String, Object> generateSalaryEntries(SalaryDetailsRequest request);

	Map<String, Object> getStaffEntriesEdit(String id);

	Map<String, Object> deleteStaffEntries(SalaryEntries salaryEntry);

	Map<String, Object> updateSalaryEntries(SalaryEntriesRequest request);

	Map<String, Object> generateSalaryEntryPayslip(SalaryEntries request);

	Map<String, Object> generatePayslips(SalaryEntriesRequest request);

	Map<String, Object> sendPayslipsMail(SalaryEntriesRequest request);

}
