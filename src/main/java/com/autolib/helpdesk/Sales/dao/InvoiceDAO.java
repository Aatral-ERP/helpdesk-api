package com.autolib.helpdesk.Sales.dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceSearchRequest;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;

public interface InvoiceDAO {

	Map<String, Object> saveDealInvoice(DealInvoiceRequest dealInvReq);

	Map<String, Object> getDealInvoice(int invoiceId);

	Map<String, Object> getDealInvoicesReport(DealInvoiceSearchRequest req);

	Map<String, Object> generateInvoicePDFTemplate1Lite(DealInvoiceRequest dealInvocie);

	Map<String, Object> generateInvoicePDFTemplate2Lite(DealInvoiceRequest dealInvocie);

	Map<String, Object> generateInvoicePDFTemplate3Lite(DealInvoiceRequest dealInvocie);

	Map<String, Object> generateInvoicePDFTemplate1(DealInvoiceRequest dealInvocie);

	Map<String, Object> generateInvoicePDFTemplate2(DealInvoiceRequest dealInvocie);

	Map<String, Object> generateInvoicePDFTemplate3(DealInvoiceRequest dealInvocie);

	Map<String, Object> UploadGeneratedInvoicePDF(int dealInvoiceId, MultipartFile file);

	Map<String, Object> UploadInvoiceSatisfactoyPDF(int dealInvoiceId, MultipartFile file);

	Map<String, Object> UploadInvoiceWorkCompletionPDF(int dealInvoiceId, MultipartFile file);

	Map<String, Object> saveDealPayments(DealPayments req);

	Map<String, Object> getDealPayments(int invoiceId);

	Map<String, Object> deleteDealPayments(DealPayments req);

	Map<String, Object> createDealPaymentsReceipt1(DealRequest req);

	Map<String, Object> createDealPaymentsReceipt2(DealRequest req);

	Map<String, Object> getDealPaymentsReport(DealInvoiceSearchRequest req);

	Map<String, Object> getNextInvoiceNo();

	Map<String, Object> generateDeliveryChallanPDF1(DealInvoiceRequest dealReq);

	Map<String, Object> generateDeliveryChallanPDF2(DealInvoiceRequest dealReq);

	Map<String, Object> generateDeliveryChallanPDF3(DealInvoiceRequest dealReq);

	Map<String, Object> getPendingInvoiceQuantityProducts(DealInvoiceSearchRequest req);

	Map<String, Object> saveInvoiceEmail(InvoiceEmail email);

	Map<String, Object> addInvoiceEmailAttachment(int invoiceEmailId, MultipartFile file);

	Map<String, Object> sendInvoiceEmail(InvoiceEmail email);

	Map<String, Object> loadInvoiceEmailReminderSettings();

	Map<String, Object> saveInvoiceEmailReminderSettings(InvoiceEmailReminderSettings settings);

	Map<String, Object> saveInvoiceReminders(DealInvoiceReminderRequest request);

	Map<String, Object> deleteInvoiceReminders(DealInvoiceReminderRequest request);

	Map<String, Object> getInvoiceReminders(int dealId);

	Map<String, Object> searchInvoiceReminders(DealInvoiceReminderRequest request);

}
