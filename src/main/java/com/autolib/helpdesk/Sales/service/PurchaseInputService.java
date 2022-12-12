package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.model.Inputs.Bill;
import com.autolib.helpdesk.Sales.model.Inputs.BillAttachments;
import com.autolib.helpdesk.Sales.model.Inputs.BillPaymentSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillPayments;
import com.autolib.helpdesk.Sales.model.Inputs.BillRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrderRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrders;

public interface PurchaseInputService {

	Map<String, Object> saveBill(BillRequest saveBill);

	Map<String, Object> searchBills(BillSearchRequest req);

	Map<String, Object> getBill(int billId);

	Map<String, Object> deleteBill(Bill bill);

	Map<String, Object> getBillAttachment(BillAttachments attach);

	Map<String, Object> savePurchaseInputOrder(PurchaseInputOrderRequest request);

	Map<String, Object> searchPurchaseInputOrders(PurchaseInputOrderRequest searchRequest);

	Map<String, Object> getPurchaseInputOrder(int orderId);

	Map<String, Object> deletePurchaseInputOrder(PurchaseInputOrders order);

	Map<String, Object> generatePurchaseInputOrdersPDF(PurchaseInputOrderRequest req);

	Map<String, Object> UploadGeneratedPurchaseInputOrdersPDF(int orderId, MultipartFile file);

	Map<String, Object> savePurchaseInputsPayments(BillPayments billPayments);

	Map<String, Object> getPurchaseInputsBillPayments(int billId);

	Map<String, Object> deletePurchaseInputsPayments(BillPayments billPayments);

	Map<String, Object> getPurchaseInputsPaymentsReport(BillSearchRequest req);
	
	Map<String, Object> searchBillsPayment(BillPaymentSearchRequest req);
}
