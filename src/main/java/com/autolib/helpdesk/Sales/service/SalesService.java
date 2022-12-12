package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.model.TermsAndConditions;

public interface SalesService {

	Map<String, Object> getSalesNeededData(Map<String, Object> needed);

	/**
	 * @param needed
	 * @return
	 */
	Map<String, Object> getSalesDashboardData(Map<String, Object> req);

	Map<String, Object> uploadPreambleDocuments(MultipartFile file);

	Map<String, Object> getPreambleDocumentsList();

	Map<String, Object> deletePreambleDocumentsList(String filename);

	Map<String, Object> saveTermsAndConditions(TermsAndConditions terms);

	Map<String, Object> deleteTermsAndConditions(TermsAndConditions terms);

	Map<String, Object> getTermsAndConditions(String type);

}
