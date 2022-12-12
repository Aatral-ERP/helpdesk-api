package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.dao.SalesDAO;
import com.autolib.helpdesk.Sales.model.TermsAndConditions;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	SalesDAO salesDAO;

	@Override
	public Map<String, Object> getSalesNeededData(Map<String, Object> needed) {
		return salesDAO.getSalesNeededData(needed);
	}

	@Override
	public Map<String, Object> getSalesDashboardData(Map<String, Object> req) {
		return salesDAO.getSalesDashboardData(req);
	}

	@Override
	public Map<String, Object> uploadPreambleDocuments(MultipartFile file) {
		return salesDAO.uploadPreambleDocuments(file);
	}

	@Override
	public Map<String, Object> getPreambleDocumentsList() {
		return salesDAO.getPreambleDocumentsList();
	}

	@Override
	public Map<String, Object> deletePreambleDocumentsList(String filename) {
		return salesDAO.deletePreambleDocumentsList(filename);
	}

	@Override
	public Map<String, Object> saveTermsAndConditions(TermsAndConditions terms) {
		return salesDAO.saveTermsAndConditions(terms);
	}

	@Override
	public Map<String, Object> deleteTermsAndConditions(TermsAndConditions terms) {
		return salesDAO.deleteTermsAndConditions(terms);
	}

	@Override
	public Map<String, Object> getTermsAndConditions(String type) {
		return salesDAO.getTermsAndConditions(type);
	}

}
