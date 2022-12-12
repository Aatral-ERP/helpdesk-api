package com.autolib.helpdesk.HR.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.HR.dao.SalaryDAO;
import com.autolib.helpdesk.HR.entity.SalaryDetails;
import com.autolib.helpdesk.HR.entity.SalaryDetailsRequest;
import com.autolib.helpdesk.HR.entity.SalaryEntries;
import com.autolib.helpdesk.HR.entity.SalaryEntriesRequest;

@Service
public class SalaryServiceImpl implements SalaryService {

	@Autowired
	private SalaryDAO salaryDAO;

	@Cacheable(value = "neededCache", key = "#type", unless = "#result.size() == 0")
	public List<String> bankname(String type) {
		return salaryDAO.bankname();
	}

	@Override
	public Map<String, Object> saveSalaryDetails(SalaryDetailsRequest salary) {
		return salaryDAO.saveSalaryDetails(salary);
	}

	@Override
	public Map<String, Object> getStaffDetails() {
		return salaryDAO.getStaffDetails();
	}

	@Override
	public Map<String, Object> getStaffDetailsEdit(String sid) {
		return salaryDAO.getStaffDetailsEdit(sid);
	}

	@Override
	public Map<String, Object> deleteStaffDetails(SalaryDetails salaryDetail) {
		return salaryDAO.deleteStaffDetails(salaryDetail);
	}

	@Override
	public Map<String, Object> generateSalarayEntry(SalaryEntriesRequest req) {
		return salaryDAO.generateSalarayEntry(req);
	}

	@Override
	public Map<String, Object> getSalaryEntries(SalaryEntriesRequest request) {
		return salaryDAO.getSalaryEntries(request);
	}

	@Override
	public Map<String, Object> generateSalaryEntries(SalaryDetailsRequest request) {
		return salaryDAO.generateSalaryEntries(request);
	}

	@Override
	public Map<String, Object> getStaffEntriesEdit(String sid) {
		return salaryDAO.getStaffEntriesEdit(sid);
	}

	@Override
	public Map<String, Object> deleteStaffEntries(SalaryEntries salaryEntry) {
		return salaryDAO.deleteStaffEntries(salaryEntry);
	}

	@Override
	public Map<String, Object> updateSalaryEntries(SalaryEntriesRequest request) {
		return salaryDAO.updateSalaryEntries(request);
	}

	@Override
	public Map<String, Object> generateSalaryEntryPayslip(SalaryEntries request) {
		return salaryDAO.generateSalaryEntryPayslip(request);
	}

	@Override
	public Map<String, Object> generatePayslips(SalaryEntriesRequest request) {
		return salaryDAO.generatePayslips(request);
	}

	@Override
	public Map<String, Object> sendPayslipsMail(SalaryEntriesRequest request) {
		return salaryDAO.sendPayslipsMail(request);
	}

}
