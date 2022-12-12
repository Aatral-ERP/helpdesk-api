package com.autolib.helpdesk.Sales.dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

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

public interface DealDAO {

	Map<String, Object> saveDeal(DealRequest dealReq);

	Map<String, Object> getDeals(DealSearchRequest dealSearchReq);

	Map<String, Object> getDeal(int dealId);

	Map<String, Object> deleteDeal(int dealId);

	Map<String, Object> saveNotes(NoteRequest note);

	Map<String, Object> deleteNotes(Notes note);

	Map<String, Object> getAllNotes(Notes note);

	Map<String, Object> getNoteAttachment(NoteAttachments note);

	Map<String, Object> saveDealQuotation(DealQuotation dealReq);

	Map<String, Object> getDealQuotation(int dealId);

	Map<String, Object> generateQuotationPDFTemplate1Lite(DealRequest dealReq);

	Map<String, Object> generateQuotationPDFTemplate2Lite(DealRequest dealReq);

	Map<String, Object> generateQuotationPDFTemplate3Lite(DealRequest dealQuote);

	Map<String, Object> generateQuotationPDFTemplate1(DealRequest dealReq);

	Map<String, Object> generateQuotationPDFTemplate2(DealRequest dealReq);

	Map<String, Object> generateQuotationPDFTemplate3(DealRequest dealQuote);

	Map<String, Object> UploadGeneratedQuotationPDF(int dealQuoteId, MultipartFile file);

	Map<String, Object> saveDealProformaInvoice(DealProformaInvoice dealInvReq);

	Map<String, Object> getDealProformaInvoice(int dealId);

	Map<String, Object> generateProformaInvoicePDFTemplate1Lite(DealRequest dealReq);

	Map<String, Object> generateProformaInvoicePDFTemplate2Lite(DealRequest dealReq);

	Map<String, Object> generateProformaInvoicePDFTemplate3Lite(DealRequest dealReq);

	Map<String, Object> generateProformaInvoicePDFTemplate1(DealRequest dealReq);

	Map<String, Object> generateProformaInvoicePDFTemplate2(DealRequest dealReq);

	Map<String, Object> generateProformaInvoicePDFTemplate3(DealRequest dealReq);

	Map<String, Object> UploadGeneratedProformaInvoicePDF(int dealProformaInvoiceId, MultipartFile file);

	Map<String, Object> getDealInvoicesPreview(int dealId);

	Map<String, Object> saveDealPurchaseOrder(DealPurchaseOrder req);

	Map<String, Object> getDealPurchaseOrder(int dealId);

	Map<String, Object> UploadPurchaseOrderFile(int dealPurchaseOrderId, MultipartFile file);

	Map<String, Object> saveDealSalesOrder(DealSalesOrder req);

	Map<String, Object> getDealSalesOrder(int dealId);

	Map<String, Object> UploadSalesOrderFile(int dealSalesOrderId, MultipartFile file);

	Map<String, Object> generateSalesOrderPDF1(DealRequest dealSales);

	Map<String, Object> generateSalesOrderPDF2(DealRequest dealSales);

	Map<String, Object> saveDealPayments(DealPayments req);

	Map<String, Object> getDealPayments(int dealId);

	Map<String, Object> deleteDealPayments(DealPayments req);

//	Map<String, Object> createDealPaymentsReceipt(DealRequest req);

	Map<String, Object> createDealPaymentsReceipt1(DealRequest req);

	Map<String, Object> createDealPaymentsReceipt2(DealRequest req);

	Map<String, Object> saveDealDeliveryChallan(DealDeliveryChallan dc);

	Map<String, Object> deleteDealDeliveryChallan(DealDeliveryChallan dc);
	
	Map<String, Object> getDealDeliveryChallan(int dealId);

	Map<String, Object> getPendingDCQuantityProducts(DealRequest dealReq);

	Map<String, Object> getDealQuotationsReport(DealSearchRequest req);

	Map<String, Object> getDealPurchaseOrdersReport(DealSearchRequest req);

	Map<String, Object> getDealProjectImplementationsReport(DealSearchRequest req);

	Map<String, Object> getDealSalesOrdersReport(DealSearchRequest req);

	Map<String, Object> getDealProformaInvoicesReport(DealSearchRequest req);

	Map<String, Object> getDealInvoicesReport(DealSearchRequest req);

	Map<String, Object> getDealDeliveryChallansReport(DealSearchRequest req);

	Map<String, Object> getDealPaymentsReport(DealSearchRequest req);

	Map<String, Object> saveDealEmail(DealEmail req);

	Map<String, Object> addDealEmailAttachment(int dealEmailId, MultipartFile file);

	Map<String, Object> sendDealEmail(DealEmail req);

	Map<String, Object> getDealEmail(DealEmail req);

	Map<String, Object> getAmcPayment(int id);

	/**
	 * @param dealProjectImplementation
	 * @return
	 */
	Map<String, Object> saveDealProjectImplementation(DealProjectImplementation dealProjectImplementation);

	/**
	 * @param dealProjectImplementation
	 * @return
	 */
	Map<String, Object> getDealProjectImplementation(int dealId);

	/**
	 * @param mailId
	 * @return
	 */
	Map<String, Object> getDealProjectImplementations(ProjectImplemantationRequest projectReq);

	/**
	 * @param comment
	 * @return
	 */
	Map<String, Object> saveDealProjectImplementationComments(DealProjectImplementationComments comment);

	/**
	 * @param comment
	 * @return
	 */
	Map<String, Object> deleteDealProjectImplementationComments(DealProjectImplementationComments comment);

	/**
	 * @param comment
	 * @return
	 */
	Map<String, Object> getAllDealProjectImplementationComments(DealProjectImplementationComments comment);

	Map<String, Object> generateDealInstamojoPaymentURL(Deal deal);

	Map<String, Object> generateSalesOrderPDF3(DealRequest dealSales);

	Map<String, Object> generateDeliveryChallanPDF1(DealRequest dealReq);

	Map<String, Object> generateDeliveryChallanPDF2(DealRequest dealReq);

	Map<String, Object> generateDeliveryChallanPDF3(DealRequest dealReq);

}
