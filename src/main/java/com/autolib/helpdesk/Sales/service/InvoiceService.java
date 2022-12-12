package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceSearchRequest;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;

public interface InvoiceService {

	Map<String, Object> saveDealInvoice(DealInvoiceRequest dealInvReq);

	Map<String, Object> getDealInvoice(int invoiceId);

	Map<String, Object> getDealInvoicesReport(DealInvoiceSearchRequest req);

	Map<String, Object> generateInvoicePDF(DealInvoiceRequest dealReq);

	Map<String, Object> UploadGeneratedInvoicePDF(int dealInvoiceId, MultipartFile file);

	Map<String, Object> UploadInvoiceSatisfactoyPDF(int dealInvoiceId, MultipartFile file);

	Map<String, Object> UploadInvoiceWorkCompletionPDF(int dealInvoiceId, MultipartFile file);

	Map<String, Object> saveDealPayments(DealPayments req);

	Map<String, Object> getDealPayments(int invoiceId);

	Map<String, Object> deleteDealPayments(DealPayments req);

	Map<String, Object> createDealPaymentsReceipt(DealRequest req);

	Map<String, Object> getDealPaymentsReport(DealInvoiceSearchRequest req);

	Map<String, Object> getNextInvoiceNo();

	Map<String, Object> generateDeliveryChallanPDF(DealInvoiceRequest dealReq);

	Map<String, Object> getPendingInvoiceQuantityProducts(DealInvoiceSearchRequest req);

	Map<String, Object> saveInvoiceEmail(InvoiceEmail email);

	Map<String, Object> addInvoiceEmailAttachment(int invoiceEmailId, MultipartFile file);

	Map<String, Object> sendInvoiceEmail(InvoiceEmail email);

	Map<String, Object> loadInvoiceEmailReminderSettings();

	Map<String, Object> saveInvoiceEmailReminderSettings(InvoiceEmailReminderSettings settings);

	Map<String, Object> saveInvoiceReminders(DealInvoiceReminderRequest request);

	Map<String, Object> getInvoiceReminders(int dealId);

	Map<String, Object> deleteInvoiceReminders(DealInvoiceReminderRequest request);

	Map<String, Object> searchInvoiceReminders(DealInvoiceReminderRequest request);

}
