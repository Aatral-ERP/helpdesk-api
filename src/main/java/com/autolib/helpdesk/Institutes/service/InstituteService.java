package com.autolib.helpdesk.Institutes.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Institutes.model.AMCDetails;
import com.autolib.helpdesk.Institutes.model.AMCReminderRequest;
import com.autolib.helpdesk.Institutes.model.AMCSearchRequest;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.model.InstituteContactRequest;
import com.autolib.helpdesk.Institutes.model.InstituteImportReq;
import com.autolib.helpdesk.Institutes.model.InstituteProducts;
import com.autolib.helpdesk.Institutes.model.InstituteProductsRequest;
import com.autolib.helpdesk.Institutes.model.InstituteRequest;
import com.autolib.helpdesk.Institutes.model.InvoiceRequest;

public interface InstituteService {

	Map<String, Object> saveInstitute(Institute institute);

	Map<String, Object> editInstitute(Institute institute);

	Map<String, Object> getInstitutePreDeleteData(Institute institute);

	Map<String, Object> deleteInstitute(Institute institute);

	Map<String, Object> getInstituteDetails(Institute institute);

	Map<String, Object> saveInstituteContact(InstituteContact ic);
	
	Map<String, Object> sendInstituteContact(InstituteContact ic);

	Map<String, Object> deleteInstituteContact(InstituteContact ic);

	Map<String, Object> getInstituteContacts(InstituteContact ic);

	Map<String, Object> changePassword(Map<String, Object> req);

	Map<String, Object> forgetPassword(Map<String, Object> req);

	Map<String, Object> resetPassword(Map<String, Object> req);

	Map<String, Object> checkOTP(Map<String, Object> req);

	Map<String, Object> saveAmcDetails(AMCDetails amc);

	Map<String, Object> loadInstitute();

	Map<String, Object> amcReport(Map<String, Object> reqMap);

	Map<String, Object> saveInstituteProducts(InstituteProducts ip);

	Map<String, Object> removeInstituteProducts(InstituteProducts ip);

	Map<String, Object> getInstituteProducts(InstituteProducts ip);

	Map<String, Object> getInstituteProductsAllReportData();

	Map<String, Object> getInstituteProductsAll(InstituteProductsRequest ipr);

	Map<String, Object> saveInstituteProductsAll(InstituteProductsRequest ipr);

	Map<String, Object> InstituteAmc(AMCDetails amc);

	Map<String, Object> createInvoice(InvoiceRequest ir);

	Map<String, Object> loadAMCDetails(AMCSearchRequest amcSearchReq);

	Map<String, Object> getAmcDetailsEdit(int aid);

	Map<String, Object> getInstituteProductsExpiryReminder();

	Map<String, Object> getInstituteAMCReminderSentReport(AMCReminderRequest amcReminderReq);
	
	Map<String, Object> getInstContact(InstituteContactRequest instContactReq);
	
	Map<String, Object> getInstituteReport(InstituteRequest institute);

	Map<String, Object> saveInstituteLogo(MultipartFile file, String instituteId);
	
	Map<String, Object> saveInstituteImport(InstituteImportReq institute);

	Map<String, Object> updateInstituteImport(InstituteImportReq institute);
	
	Map<String, Object> getInvoiceAMCEntries(String req);


}
