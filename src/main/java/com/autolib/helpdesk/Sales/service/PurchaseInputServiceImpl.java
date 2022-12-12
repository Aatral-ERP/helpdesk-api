package com.autolib.helpdesk.Sales.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.dao.PurchaseInputDAO;
import com.autolib.helpdesk.Sales.model.Inputs.Bill;
import com.autolib.helpdesk.Sales.model.Inputs.BillAttachments;
import com.autolib.helpdesk.Sales.model.Inputs.BillPaymentSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillPayments;
import com.autolib.helpdesk.Sales.model.Inputs.BillRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrderRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrders;

@Service
public class PurchaseInputServiceImpl implements PurchaseInputService {

	@Autowired
	PurchaseInputDAO piDAO;

	@Override
	public Map<String, Object> saveBill(BillRequest saveBill) {
		// TODO Auto-generated method stub
		return piDAO.saveBill(saveBill);
	}

	@Override
	public Map<String, Object> searchBills(BillSearchRequest req) {
		// TODO Auto-generated method stub
		return piDAO.searchBills(req);
	}

	@Override
	public Map<String, Object> deleteBill(Bill bill) {
		// TODO Auto-generated method stub
		return piDAO.deleteBill(bill);
	}

	@Override
	public Map<String, Object> getBill(int billId) {
		// TODO Auto-generated method stub
		return piDAO.getBill(billId);
	}

	@Override
	public Map<String, Object> getBillAttachment(BillAttachments bill) {
		return piDAO.getBillAttachment(bill);
	}

	@Override
	public Map<String, Object> savePurchaseInputOrder(PurchaseInputOrderRequest request) {
		return piDAO.savePurchaseInputOrder(request);
	}

	@Override
	public Map<String, Object> searchPurchaseInputOrders(PurchaseInputOrderRequest searchRequest) {
		return piDAO.searchPurchaseInputOrders(searchRequest);
	}

	@Override
	public Map<String, Object> getPurchaseInputOrder(int orderId) {
		return piDAO.getPurchaseInputOrder(orderId);
	}

	@Override
	public Map<String, Object> deletePurchaseInputOrder(PurchaseInputOrders order) {
		return piDAO.deletePurchaseInputOrder(order);
	}

	@Override
	public Map<String, Object> generatePurchaseInputOrdersPDF(PurchaseInputOrderRequest req) {
		Map<String, Object> resp = new HashMap<>();
		if (req.getTemplateName().equalsIgnoreCase("Purchase_Input_Order_Template_1")) {
			return piDAO.generatePurchaseInputOrdersPDF(req);
		} else if (req.getTemplateName().equalsIgnoreCase("Purchase_Input_Order_Template_2")) {
			return piDAO.generatePurchaseInputOrdersPDF(req);
		}
		return resp;
	}

	@Override
	public Map<String, Object> UploadGeneratedPurchaseInputOrdersPDF(int orderId, MultipartFile file) {
		return piDAO.UploadGeneratedPurchaseInputOrdersPDF(orderId, file);
	}

	@Override
	public Map<String, Object> savePurchaseInputsPayments(BillPayments billPayments) {
		return piDAO.savePurchaseInputsPayments(billPayments);
	}

	@Override
	public Map<String, Object> getPurchaseInputsBillPayments(int billId) {
		return piDAO.getPurchaseInputsBillPayments(billId);
	}

	@Override
	public Map<String, Object> deletePurchaseInputsPayments(BillPayments billPayments) {
		return piDAO.deletePurchaseInputsPayments(billPayments);
	}

	@Override
	public Map<String, Object> getPurchaseInputsPaymentsReport(BillSearchRequest req) {
		return piDAO.getPurchaseInputsPaymentsReport(req);
	}
	
	@Override
	public Map<String, Object> searchBillsPayment(BillPaymentSearchRequest req) {
		// TODO Auto-generated method stub
		return piDAO.searchBillsPayment(req);
	}

}
