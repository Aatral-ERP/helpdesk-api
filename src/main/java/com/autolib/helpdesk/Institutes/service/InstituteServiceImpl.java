package com.autolib.helpdesk.Institutes.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Institutes.dao.InstituteDAO;
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
import com.autolib.helpdesk.common.Util;

@Service
public class InstituteServiceImpl implements InstituteService {

	@Autowired
	InstituteDAO instDAO;

	@Override
	public Map<String, Object> saveInstitute(Institute institute) {
		return instDAO.saveInstitute(institute);
	}

	@Override
	public Map<String, Object> editInstitute(Institute institute) {
		return instDAO.editInstitute(institute);
	}

	@Override
	public Map<String, Object> getInstitutePreDeleteData(Institute institute) {
		return instDAO.getInstitutePreDeleteData(institute);
	}

	@Override
	public Map<String, Object> deleteInstitute(Institute institute) {
		return instDAO.deleteInstitute(institute);
	}

	@Override
	public Map<String, Object> getInstituteDetails(Institute institute) {
		return instDAO.getInstituteDetails(institute);
	}

	@Override
	public Map<String, Object> saveInstituteContact(InstituteContact ic) {
		return instDAO.saveInstituteContact(ic);
	}
	
	@Override
	public Map<String, Object> sendInstituteContact(InstituteContact ic) {
		return instDAO.sendInstituteContact(ic);
	}

	@Override
	public Map<String, Object> deleteInstituteContact(InstituteContact ic) {
		return instDAO.deleteInstituteContact(ic);
	}

	@Override
	public Map<String, Object> getInstituteContacts(InstituteContact ic) {
		return instDAO.getInstituteContacts(ic);
	}

	@Override
	public Map<String, Object> changePassword(Map<String, Object> req) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		int count = instDAO.changePassword(req);
		if (count == 1) {
			respMap.putAll(Util.SuccessResponse());
		} else if (count == 0) {
			respMap.putAll(Util.notMatch());
		} else {
			respMap.putAll(Util.FailedResponse());
		}
		return respMap;
	}

	@Override
	public Map<String, Object> forgetPassword(Map<String, Object> req) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		int count = instDAO.sendOTP(req);
		System.out.println("======Count=======" + count);
		if (count == 1) {
			respMap.putAll(Util.SuccessResponse());
		} else if (count == 2) {
			respMap.putAll(Util.invalidUser());
		} else if (count == 3) {
			respMap.putAll(Util.exitUser());
		} else if (count == 4) {
			respMap.putAll(Util.invalidEmailAndMobile());
		} else {
			respMap.putAll(Util.FailedResponse());
		}
		return respMap;
	}

	@Override
	public Map<String, Object> resetPassword(Map<String, Object> reqMap) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		int count = instDAO.resetPassword(reqMap);
		if (count > 0) {
			respMap.putAll(Util.SuccessResponse());
		} else {
			respMap.putAll(Util.FailedResponse());
		}
		return respMap;

	}

	@Override
	public Map<String, Object> checkOTP(Map<String, Object> req) {

		int count = instDAO.checkOTP(req);
		if (count == 0) {
			req.putAll(Util.InvalidOTP());
		} else if (count == 1) {
			req.putAll(Util.SuccessResponse());
		} else if (count == 2) {
			req.putAll(Util.expiredOtpResponse());
		} else {
			req.putAll(Util.FailedResponse());
		}

		return req;
	}

	@Override
	public Map<String, Object> saveAmcDetails(AMCDetails amc) {
		return instDAO.saveAmcDetails(amc);
	}

	@Override
	public Map<String, Object> loadInstitute() {
		return instDAO.loadInstitute();
	}

	@Override
	public Map<String, Object> amcReport(Map<String, Object> reqMap) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = instDAO.amcReport(reqMap);
		if (resultMap != null) {
			respMap.putAll(Util.SuccessResponse());
		} else {
			respMap.putAll(Util.FailedResponse());
		}
		respMap.putAll(resultMap);
		// saveSearchStats(reqMap, "ebook-search");
		return respMap;
	}

	@Override
	public Map<String, Object> saveInstituteProducts(InstituteProducts ip) {
		// TODO Auto-generated method stub
		return instDAO.saveInstituteProducts(ip);
	}

	@Override
	public Map<String, Object> removeInstituteProducts(InstituteProducts ip) {
		// TODO Auto-generated method stub
		return instDAO.removeInstituteProducts(ip);
	}

	@Override
	public Map<String, Object> getInstituteProducts(InstituteProducts ip) {
		// TODO Auto-generated method stub
		return instDAO.getInstituteProducts(ip);
	}

	@Override
	public Map<String, Object> getInstituteProductsAllReportData() {
		// TODO Auto-generated method stub
		return instDAO.getInstituteProductsAllReportData();
	}

	@Override
	public Map<String, Object> getInstituteProductsAll(InstituteProductsRequest ipr) {
		// TODO Auto-generated method stub
		return instDAO.getInstituteProductsAll(ipr);
	}

	@Override
	public Map<String, Object> saveInstituteProductsAll(InstituteProductsRequest ipr) {
		// TODO Auto-generated method stub
		return instDAO.saveInstituteProductsAll(ipr);
	}

	@Override
	public Map<String, Object> InstituteAmc(AMCDetails amc) {
		return instDAO.InstituteAmc(amc);
	}

	@Override
	public Map<String, Object> createInvoice(InvoiceRequest ir) {
		return instDAO.createInvoice(ir);
	}

	@Override
	public Map<String, Object> loadAMCDetails(AMCSearchRequest amcSearchReq) {
		return instDAO.loadAMCDetails(amcSearchReq);
	}

	@Override
	public Map<String, Object> getAmcDetailsEdit(int aid) {
		return instDAO.getAmcDetailsEdit(aid);
	}

	@Override
	public Map<String, Object> getInstituteProductsExpiryReminder() {
		return instDAO.getInstituteProductsExpiryReminder();
	}

	@Override
	public Map<String, Object> getInstituteAMCReminderSentReport(AMCReminderRequest amcReminderReq) {
		return instDAO.getInstituteAMCReminderSentReport(amcReminderReq);
	}
	
	@Override
	public Map<String, Object> getInstContact(InstituteContactRequest instContactReq) {
		return instDAO.getInstContact(instContactReq);
	}
	
	@Override
	public Map<String, Object> getInstituteReport(InstituteRequest institute) {
		return instDAO.getInstituteReport(institute);
	}

	@Override
	public Map<String, Object> saveInstituteLogo(MultipartFile file, String instituteId) {
		return instDAO.saveInstituteLogo(file,instituteId);
	}
	
	@Override
	public Map<String, Object> getInvoiceAMCEntries(String req) {
		return instDAO.getInvoiceAMCEntries(req);
	}
	
	@Override
	public Map<String, Object> saveInstituteImport(InstituteImportReq request) {	
		return instDAO.saveInstituteImport(request);
	}
	
	@Override
	public Map<String, Object> updateInstituteImport(InstituteImportReq request) {	
		return instDAO.updateInstituteImport(request);
	}

}
