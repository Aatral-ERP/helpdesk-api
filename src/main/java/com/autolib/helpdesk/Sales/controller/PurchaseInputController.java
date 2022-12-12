package com.autolib.helpdesk.Sales.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Sales.model.Inputs.Bill;
import com.autolib.helpdesk.Sales.model.Inputs.BillAttachments;
import com.autolib.helpdesk.Sales.model.Inputs.BillPaymentSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillPayments;
import com.autolib.helpdesk.Sales.model.Inputs.BillRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrderRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrders;
import com.autolib.helpdesk.Sales.service.PurchaseInputService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("purchase-inputs")
public class PurchaseInputController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	PurchaseInputService purchaseInputService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("save-bill")
	public ResponseEntity<?> saveBill(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillRequest billRequest) throws Exception {
		logger.info("getSalesNeededData starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.saveBill(billRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getSalesNeededData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-bill/{billId}")
	public ResponseEntity<?> getBill(@RequestHeader(value = "Authorization") String token,
			@PathVariable("billId") int billId) throws Exception {
		logger.info("getBill starts:::" + billId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.getBill(billId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getBill ends:::" + billId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-bill")
	public ResponseEntity<?> deleteBill(@RequestHeader(value = "Authorization") String token, @RequestBody Bill bill)
			throws Exception {
		logger.info("deleteBill starts:::" + bill);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.deleteBill(bill);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deleteBill ends:::" + bill);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-bills")
	public ResponseEntity<?> searchBills(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillSearchRequest billSearchRequest) throws Exception {
		logger.info("searchBills starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.searchBills(billSearchRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("searchBills ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-bill-attachment")
	public ResponseEntity<?> getBillAttachment(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillAttachments attach) {

		logger.info("getNoteAttachment starts:::" + attach);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = purchaseInputService.getBillAttachment(attach);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getNoteAttachment ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-purchase-input-order")
	public ResponseEntity<?> savePurchaseInputOrder(@RequestHeader(value = "Authorization") String token,
			@RequestBody PurchaseInputOrderRequest request) throws Exception {
		logger.info("getSalesNeededData starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.savePurchaseInputOrder(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getSalesNeededData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-purchase-input-order/{orderId}")
	public ResponseEntity<?> getPurchaseInputOrder(@RequestHeader(value = "Authorization") String token,
			@PathVariable("orderId") int orderId) throws Exception {
		logger.info("getPurchaseInputOrder starts:::" + orderId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.getPurchaseInputOrder(orderId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getPurchaseInputOrder ends:::" + orderId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-purchase-input-order")
	public ResponseEntity<?> deletePurchaseInputOrder(@RequestHeader(value = "Authorization") String token,
			@RequestBody PurchaseInputOrders order) throws Exception {
		logger.info("deletePurchaseInputOrder starts:::" + order);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.deletePurchaseInputOrder(order);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deletePurchaseInputOrder ends:::" + order);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-purchase-input-orders")
	public ResponseEntity<?> searchPurchaseInputOrders(@RequestHeader(value = "Authorization") String token,
			@RequestBody PurchaseInputOrderRequest searchRequest) throws Exception {
		logger.info("searchPurchaseInputOrders starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.searchPurchaseInputOrders(searchRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("searchPurchaseInputOrders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-purchase-input-order-pdf")
	public ResponseEntity<?> generatePurchaseInputOrdersPDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody PurchaseInputOrderRequest req) {

		logger.info("generatePurchaseInputOrdersPDF starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = purchaseInputService.generatePurchaseInputOrdersPDF(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generatePurchaseInputOrdersPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-generated-purchase-input-order-pdf")
	public ResponseEntity<?> UploadGeneratedPurchaseInputOrdersPDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("orderId") int orderId) throws Exception {
		logger.info("UploadGeneratedPurchaseInputOrdersPDF starts:::" + orderId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.UploadGeneratedPurchaseInputOrdersPDF(orderId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadGeneratedPurchaseInputOrdersPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-purchase-inputs-payment")
	public ResponseEntity<?> savePurchaseInputsPayments(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillPayments billPayments) {

		logger.info("savePurchaseInputsPayments starts:::" + billPayments);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = purchaseInputService.savePurchaseInputsPayments(billPayments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("savePurchaseInputsPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-purchase-inputs-payments/{billId}")
	public ResponseEntity<?> getPurchaseInputsBillPayments(@RequestHeader(value = "Authorization") String token,
			@PathVariable("billId") int billId) {

		logger.info("getPurchaseInputsBillPayments starts:::" + billId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = purchaseInputService.getPurchaseInputsBillPayments(billId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getPurchaseInputsBillPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-purchase-inputs-payment")
	public ResponseEntity<?> deletePurchaseInputsPayments(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillPayments billPayments) {

		logger.info("deletePurchaseInputsPayments starts:::" + billPayments);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = purchaseInputService.deletePurchaseInputsPayments(billPayments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deletePurchaseInputsPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-purchase-inputs-payments-report")
	public ResponseEntity<?> getPurchaseInputsPaymentsReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillSearchRequest req) {

		logger.info("getPurchaseInputsPaymentsReport starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = purchaseInputService.getPurchaseInputsPaymentsReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getPurchaseInputsPaymentsReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("get-bills-payments")
	public ResponseEntity<?> searchBillsPayments(@RequestHeader(value = "Authorization") String token,
			@RequestBody BillPaymentSearchRequest BillPaymentSearchRequest) throws Exception {
		logger.info("searchBillsPayments starts:::"+BillPaymentSearchRequest);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = purchaseInputService.searchBillsPayment(BillPaymentSearchRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("searchBillsPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
