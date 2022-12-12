package com.autolib.helpdesk.HR.dao;

import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.HR.entity.SalaryDetails;
import com.autolib.helpdesk.HR.entity.SalaryDetailsRequest;
import com.autolib.helpdesk.HR.entity.SalaryEntries;
import com.autolib.helpdesk.HR.entity.SalaryEntriesRequest;

public interface SalaryDAO {

	public List<String> bankname();

	public Map<String, Object> saveSalaryDetails(SalaryDetailsRequest salary);

	public Map<String, Object> getStaffDetails();

	public Map<String, Object> getStaffDetailsEdit(String sid);

	public Map<String, Object> deleteStaffDetails(SalaryDetails salaryDetail);

	public Map<String, Object> generateSalarayEntry(SalaryEntriesRequest salaryEntries);

	public Map<String, Object> getSalaryEntries(SalaryEntriesRequest request);

	public Map<String, Object> generateSalaryEntries(SalaryDetailsRequest request);

	public Map<String, Object> getStaffEntriesEdit(String sid);

	public Map<String, Object> deleteStaffEntries(SalaryEntries salaryEntry);

	public Map<String, Object> updateSalaryEntries(SalaryEntriesRequest request);

	public Map<String, Object> generateSalaryEntryPayslip(SalaryEntries request);

	public Map<String, Object> generatePayslips(SalaryEntriesRequest request);

	public Map<String, Object> sendPayslipsMail(SalaryEntriesRequest request);

}
