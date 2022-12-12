package com.autolib.helpdesk.Sales.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.dao.InvoiceDAO;
import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceSearchRequest;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceDAO invDAO;

	@Override
	public Map<String, Object> saveDealInvoice(DealInvoiceRequest dealInvReq) {
		return invDAO.saveDealInvoice(dealInvReq);
	}

	@Override
	public Map<String, Object> getDealInvoice(int invoiceId) {
		return invDAO.getDealInvoice(invoiceId);
	}

	@Override
	public Map<String, Object> getDealInvoicesReport(DealInvoiceSearchRequest req) {
		return invDAO.getDealInvoicesReport(req);
	}

	@Override
	public Map<String, Object> generateInvoicePDF(DealInvoiceRequest dealInvocie) {
		Map<String, Object> resp = new HashMap<>();
		if (dealInvocie.getTemplateName().equalsIgnoreCase("Invoice_Template_1")) {
			if (dealInvocie.isDetailedPricing())
				resp = invDAO.generateInvoicePDFTemplate1(dealInvocie);
			else
				resp = invDAO.generateInvoicePDFTemplate1Lite(dealInvocie);
		} else if (dealInvocie.getTemplateName().equalsIgnoreCase("Invoice_Template_2")) {
			if (dealInvocie.isDetailedPricing())
				resp = invDAO.generateInvoicePDFTemplate2(dealInvocie);
			else
				resp = invDAO.generateInvoicePDFTemplate2Lite(dealInvocie);
		} else if (dealInvocie.getTemplateName().equalsIgnoreCase("Invoice_Template_3")) {
			if (dealInvocie.isDetailedPricing())
				resp = invDAO.generateInvoicePDFTemplate3(dealInvocie);
			else
				resp = invDAO.generateInvoicePDFTemplate3Lite(dealInvocie);
		}
		return resp;
	}

	@Override
	public Map<String, Object> UploadGeneratedInvoicePDF(int dealInvoiceId, MultipartFile file) {
		return invDAO.UploadGeneratedInvoicePDF(dealInvoiceId, file);
	}

	@Override
	public Map<String, Object> UploadInvoiceSatisfactoyPDF(int dealInvoiceId, MultipartFile file) {
		return invDAO.UploadInvoiceSatisfactoyPDF(dealInvoiceId, file);
	}

	@Override
	public Map<String, Object> UploadInvoiceWorkCompletionPDF(int dealInvoiceId, MultipartFile file) {
		return invDAO.UploadInvoiceWorkCompletionPDF(dealInvoiceId, file);
	}

	@Override
	public Map<String, Object> saveDealPayments(DealPayments req) {
		return invDAO.saveDealPayments(req);
	}

	@Override
	public Map<String, Object> getDealPayments(int invoiceId) {
		return invDAO.getDealPayments(invoiceId);
	}

	@Override
	public Map<String, Object> deleteDealPayments(DealPayments req) {
		return invDAO.deleteDealPayments(req);
	}

	@Override
	public Map<String, Object> createDealPaymentsReceipt(DealRequest req) {
		Map<String, Object> resp = new HashMap<>();
		System.out.println(":::Template::::" + req.getTemplateName());
		if (req.getTemplateName().equalsIgnoreCase("Payment_Template_1")) {
			resp = invDAO.createDealPaymentsReceipt1(req);
		} else if (req.getTemplateName().equalsIgnoreCase("Payment_Template_2")) {
			resp = invDAO.createDealPaymentsReceipt2(req);
		}
		return resp;
	}

	@Override
	public Map<String, Object> getDealPaymentsReport(DealInvoiceSearchRequest req) {
		return invDAO.getDealPaymentsReport(req);
	}

	@Override
	public Map<String, Object> getNextInvoiceNo() {
		return invDAO.getNextInvoiceNo();
	}

	@Override
	public Map<String, Object> generateDeliveryChallanPDF(DealInvoiceRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		System.out.println("Template()::::::::::" + dealReq.getTemplateName());
		if (dealReq.getTemplateName().equalsIgnoreCase("DC_Template_1")) {
			resp = invDAO.generateDeliveryChallanPDF1(dealReq);
		} else if (dealReq.getTemplateName().equalsIgnoreCase("DC_Template_2")) {
			resp = invDAO.generateDeliveryChallanPDF2(dealReq);
		} else if (dealReq.getTemplateName().equalsIgnoreCase("DC_Template_3")) {
			resp = invDAO.generateDeliveryChallanPDF3(dealReq);
		}
		return resp;
	}

	@Override
	public Map<String, Object> getPendingInvoiceQuantityProducts(DealInvoiceSearchRequest req) {
		return invDAO.getPendingInvoiceQuantityProducts(req);
	}

	@Override
	public Map<String, Object> saveInvoiceEmail(InvoiceEmail email) {
		return invDAO.saveInvoiceEmail(email);
	}

	@Override
	public Map<String, Object> addInvoiceEmailAttachment(int invoiceEmailId, MultipartFile file) {
		return invDAO.addInvoiceEmailAttachment(invoiceEmailId, file);
	}

	@Override
	public Map<String, Object> sendInvoiceEmail(InvoiceEmail email) {
		return invDAO.sendInvoiceEmail(email);
	}

	@Override
	public Map<String, Object> loadInvoiceEmailReminderSettings() {
		return invDAO.loadInvoiceEmailReminderSettings();
	}

	@Override
	public Map<String, Object> saveInvoiceEmailReminderSettings(InvoiceEmailReminderSettings settings) {
		return invDAO.saveInvoiceEmailReminderSettings(settings);
	}

	@Override
	public Map<String, Object> saveInvoiceReminders(DealInvoiceReminderRequest request) {
		return invDAO.saveInvoiceReminders(request);
	}
	
	@Override
	public Map<String, Object> getInvoiceReminders(int dealId) {
		return invDAO.getInvoiceReminders(dealId);
	}
	
	@Override
	public Map<String, Object> deleteInvoiceReminders(DealInvoiceReminderRequest request) {
		return invDAO.deleteInvoiceReminders(request);
	}

	@Override
	public Map<String, Object> searchInvoiceReminders(DealInvoiceReminderRequest request) {
		return invDAO.searchInvoiceReminders(request);
	}
	
	
}
