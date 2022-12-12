package com.autolib.helpdesk.Sales.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.dao.DealDAO;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealDeliveryChallan;
import com.autolib.helpdesk.Sales.model.DealEmail;
import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.DealProformaInvoice;
import com.autolib.helpdesk.Sales.model.DealProjectImplementation;
import com.autolib.helpdesk.Sales.model.DealProjectImplementationComments;
import com.autolib.helpdesk.Sales.model.DealPurchaseOrder;
import com.autolib.helpdesk.Sales.model.DealQuotation;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.model.DealSalesOrder;
import com.autolib.helpdesk.Sales.model.DealSearchRequest;
import com.autolib.helpdesk.Sales.model.NoteAttachments;
import com.autolib.helpdesk.Sales.model.NoteRequest;
import com.autolib.helpdesk.Sales.model.Notes;
import com.autolib.helpdesk.Sales.model.ProjectImplemantationRequest;
import com.autolib.helpdesk.Sales.model.ValidateQuotation;
import com.autolib.helpdesk.common.Util;

@Service
public class DealServiceImpl implements DealService {

	@Autowired
	DealDAO dealDAO;

	@Override
	public Map<String, Object> saveDeal(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();
		if (ValidateQuotation.validateSaveDeal(dealReq).size() == 0) {
			resp = dealDAO.saveDeal(dealReq);
		} else {
			resp.put("Errors", ValidateQuotation.validateSaveDeal(dealReq));
			resp.putAll(Util.FailedResponse());
		}
		return resp;
	}

	@Override
	public Map<String, Object> getDeals(DealSearchRequest dealSearchReq) {
		return dealDAO.getDeals(dealSearchReq);
	}

	@Override
	public Map<String, Object> getDeal(int dealId) {
		return dealDAO.getDeal(dealId);
	}

	@Override
	public Map<String, Object> deleteDeal(int dealId) {
		return dealDAO.deleteDeal(dealId);
	}

	@Override
	public Map<String, Object> saveNotes(NoteRequest note) {
		return dealDAO.saveNotes(note);
	}

	@Override
	public Map<String, Object> deleteNotes(Notes note) {
		return dealDAO.deleteNotes(note);
	}

	@Override
	public Map<String, Object> getAllNotes(Notes note) {
		return dealDAO.getAllNotes(note);
	}

	@Override
	public Map<String, Object> getNoteAttachment(NoteAttachments note) {
		return dealDAO.getNoteAttachment(note);
	}

	@Override
	public Map<String, Object> saveDealQuotation(DealQuotation dealReq) {
		return dealDAO.saveDealQuotation(dealReq);
	}

	@Override
	public Map<String, Object> getDealQuotation(int dealId) {
		return dealDAO.getDealQuotation(dealId);
	}

	@Override
	public Map<String, Object> generateQuotationPDF(DealRequest dealQuote) {
		Map<String, Object> resp = new HashMap<>();
		if (dealQuote.getTemplateName().equalsIgnoreCase("Quote_Template_1")) {
			if (dealQuote.isDetailedPricing())
				resp = dealDAO.generateQuotationPDFTemplate1(dealQuote);
			else
				resp = dealDAO.generateQuotationPDFTemplate1Lite(dealQuote);
		} else if (dealQuote.getTemplateName().equalsIgnoreCase("Quote_Template_2")) {
			if (dealQuote.isDetailedPricing())
				resp = dealDAO.generateQuotationPDFTemplate2(dealQuote);
			else
				resp = dealDAO.generateQuotationPDFTemplate2Lite(dealQuote);
		} else if (dealQuote.getTemplateName().equalsIgnoreCase("Quote_Template_3")) {
			if (dealQuote.isDetailedPricing())
				resp = dealDAO.generateQuotationPDFTemplate3(dealQuote);
			else
				resp = dealDAO.generateQuotationPDFTemplate3Lite(dealQuote);
		}
		return resp;
	}

	@Override
	public Map<String, Object> UploadGeneratedQuotationPDF(int dealQuoteId, MultipartFile file) {
		return dealDAO.UploadGeneratedQuotationPDF(dealQuoteId, file);
	}

	@Override
	public Map<String, Object> saveDealProformaInvoice(DealProformaInvoice dealInvReq) {
		return dealDAO.saveDealProformaInvoice(dealInvReq);
	}

	@Override
	public Map<String, Object> getDealProformaInvoice(int dealId) {
		return dealDAO.getDealProformaInvoice(dealId);
	}

	@Override
	public Map<String, Object> generateProformaInvoicePDF(DealRequest dealInvocie) {
		Map<String, Object> resp = new HashMap<>();
		if (dealInvocie.getTemplateName().equalsIgnoreCase("Proforma_Invoice_Template_1")) {
			if (dealInvocie.isDetailedPricing())
				resp = dealDAO.generateProformaInvoicePDFTemplate1(dealInvocie);
			else
				resp = dealDAO.generateProformaInvoicePDFTemplate1Lite(dealInvocie);
		} else if (dealInvocie.getTemplateName().equalsIgnoreCase("Proforma_Invoice_Template_2")) {
			if (dealInvocie.isDetailedPricing())
				resp = dealDAO.generateProformaInvoicePDFTemplate2(dealInvocie);
			else
				resp = dealDAO.generateProformaInvoicePDFTemplate2Lite(dealInvocie);
		} else if (dealInvocie.getTemplateName().equalsIgnoreCase("Proforma_Invoice_Template_3")) {
			if (dealInvocie.isDetailedPricing())
				resp = dealDAO.generateProformaInvoicePDFTemplate3(dealInvocie);
			else
				resp = dealDAO.generateProformaInvoicePDFTemplate3Lite(dealInvocie);
		}
		return resp;
	}

	@Override
	public Map<String, Object> UploadGeneratedProformaInvoicePDF(int dealProformaInvoiceId, MultipartFile file) {
		return dealDAO.UploadGeneratedProformaInvoicePDF(dealProformaInvoiceId, file);
	}

	@Override
	public Map<String, Object> getDealInvoicesPreview(int dealId) {
		return dealDAO.getDealInvoicesPreview(dealId);
	}

	@Override
	public Map<String, Object> saveDealPurchaseOrder(DealPurchaseOrder req) {
		return dealDAO.saveDealPurchaseOrder(req);
	}

	@Override
	public Map<String, Object> getDealPurchaseOrder(int dealId) {
		return dealDAO.getDealPurchaseOrder(dealId);
	}

	@Override
	public Map<String, Object> generateSalesOrderPDF(DealRequest dealInvocie) {
		Map<String, Object> resp = new HashMap<>();
		if (dealInvocie.getTemplateName().equalsIgnoreCase("Sales_Order_Template_1")) {
			resp = dealDAO.generateSalesOrderPDF1(dealInvocie);
		} else if (dealInvocie.getTemplateName().equalsIgnoreCase("Sales_Order_Template_2")) {
			resp = dealDAO.generateSalesOrderPDF2(dealInvocie);
		} else if (dealInvocie.getTemplateName().equalsIgnoreCase("Sales_Order_Template_3")) {
			resp = dealDAO.generateSalesOrderPDF3(dealInvocie);
		}
		return resp;
	}

	@Override
	public Map<String, Object> UploadPurchaseOrderFile(int dealPurchaseOrderId, MultipartFile file) {
		return dealDAO.UploadPurchaseOrderFile(dealPurchaseOrderId, file);
	}

	@Override
	public Map<String, Object> saveDealSalesOrder(DealSalesOrder req) {
		return dealDAO.saveDealSalesOrder(req);
	}

	@Override
	public Map<String, Object> getDealSalesOrder(int dealId) {
		return dealDAO.getDealSalesOrder(dealId);
	}

	@Override
	public Map<String, Object> UploadSalesOrderFile(int dealSalesOrderId, MultipartFile file) {
		return dealDAO.UploadSalesOrderFile(dealSalesOrderId, file);
	}

	@Override
	public Map<String, Object> saveDealPayments(DealPayments req) {
		return dealDAO.saveDealPayments(req);
	}

	@Override
	public Map<String, Object> getDealPayments(int dealId) {
		return dealDAO.getDealPayments(dealId);
	}

	@Override
	public Map<String, Object> deleteDealPayments(DealPayments req) {
		return dealDAO.deleteDealPayments(req);
	}

//	@Override
//	public Map<String, Object> createDealPaymentsReceipt(DealRequest req) {
//		return dealDAO.createDealPaymentsReceipt(req);
//	}

	@Override
	public Map<String, Object> createDealPaymentsReceipt(DealRequest req) {
		Map<String, Object> resp = new HashMap<>();
		System.out.println(":::Template::::" + req.getTemplateName());
		if (req.getTemplateName().equalsIgnoreCase("Payment_Template_1")) {
			resp = dealDAO.createDealPaymentsReceipt1(req);
		} else if (req.getTemplateName().equalsIgnoreCase("Payment_Template_2")) {
			resp = dealDAO.createDealPaymentsReceipt2(req);
		}
		return resp;
	}

	@Override
	public Map<String, Object> saveDealDeliveryChallan(DealDeliveryChallan dc) {
		return dealDAO.saveDealDeliveryChallan(dc);
	}

	@Override
	public Map<String, Object> deleteDealDeliveryChallan(DealDeliveryChallan dc) {
		return dealDAO.deleteDealDeliveryChallan(dc);
	}

	@Override
	public Map<String, Object> getDealDeliveryChallan(int dealId) {
		return dealDAO.getDealDeliveryChallan(dealId);
	}

	@Override
	public Map<String, Object> getPendingDCQuantityProducts(DealRequest dealReq) {
		return dealDAO.getPendingDCQuantityProducts(dealReq);
	}

	@Override
	public Map<String, Object> getDealQuotationsReport(DealSearchRequest req) {
		return dealDAO.getDealQuotationsReport(req);
	}

	@Override
	public Map<String, Object> getDealSalesOrdersReport(DealSearchRequest req) {
		return dealDAO.getDealSalesOrdersReport(req);
	}

	@Override
	public Map<String, Object> getDealPurchaseOrdersReport(DealSearchRequest req) {
		return dealDAO.getDealPurchaseOrdersReport(req);
	}

	@Override
	public Map<String, Object> getDealProjectImplementationsReport(DealSearchRequest req) {
		return dealDAO.getDealProjectImplementationsReport(req);
	}

	@Override
	public Map<String, Object> getDealProformaInvoicesReport(DealSearchRequest req) {
		return dealDAO.getDealProformaInvoicesReport(req);
	}

	@Override
	public Map<String, Object> getDealInvoicesReport(DealSearchRequest req) {
		return dealDAO.getDealInvoicesReport(req);
	}

	@Override
	public Map<String, Object> getDealDeliveryChallansReport(DealSearchRequest req) {
		return dealDAO.getDealDeliveryChallansReport(req);
	}

	@Override
	public Map<String, Object> getDealPaymentsReport(DealSearchRequest req) {
		return dealDAO.getDealPaymentsReport(req);
	}

	@Override
	public Map<String, Object> saveDealEmail(DealEmail req) {
		return dealDAO.saveDealEmail(req);
	}

	@Override
	public Map<String, Object> addDealEmailAttachment(int dealEmailId, MultipartFile file) {
		return dealDAO.addDealEmailAttachment(dealEmailId, file);
	}

	@Override
	public Map<String, Object> sendDealEmail(DealEmail req) {
		return dealDAO.sendDealEmail(req);
	}

	@Override
	public Map<String, Object> getDealEmail(DealEmail req) {
		return dealDAO.getDealEmail(req);
	}

	@Override
	public Map<String, Object> getAmcPayment(int id) {
		return dealDAO.getAmcPayment(id);
	}

	@Override
	public Map<String, Object> saveDealProjectImplementation(DealProjectImplementation dealProjectImplementation) {
		return dealDAO.saveDealProjectImplementation(dealProjectImplementation);
	}

	@Override
	public Map<String, Object> getDealProjectImplementation(int dealId) {
		return dealDAO.getDealProjectImplementation(dealId);
	}

	@Override
	public Map<String, Object> getDealProjectImplementations(ProjectImplemantationRequest projectReq) {
		return dealDAO.getDealProjectImplementations(projectReq);
	}

	@Override
	public Map<String, Object> saveDealProjectImplementationComments(DealProjectImplementationComments comment) {
		return dealDAO.saveDealProjectImplementationComments(comment);
	}

	@Override
	public Map<String, Object> deleteDealProjectImplementationComments(DealProjectImplementationComments comment) {
		return dealDAO.deleteDealProjectImplementationComments(comment);
	}

	@Override
	public Map<String, Object> getAllDealProjectImplementationComments(DealProjectImplementationComments comment) {
		return dealDAO.getAllDealProjectImplementationComments(comment);
	}

	@Override
	public Map<String, Object> generateDealInstamojoPaymentURL(Deal deal) {
		return dealDAO.generateDealInstamojoPaymentURL(deal);
	}

	@Override
	public Map<String, Object> generateDeliveryChallanPDF(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();
		if (dealReq.getTemplateName().equalsIgnoreCase("DC_Template_1")) {
			resp = dealDAO.generateDeliveryChallanPDF1(dealReq);
		} else if (dealReq.getTemplateName().equalsIgnoreCase("DC_Template_2")) {
			resp = dealDAO.generateDeliveryChallanPDF2(dealReq);
		} else if (dealReq.getTemplateName().equalsIgnoreCase("DC_Template_3")) {
			resp = dealDAO.generateDeliveryChallanPDF3(dealReq);
		}
		return resp;
	}

}
