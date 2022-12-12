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
import com.autolib.helpdesk.Sales.service.DealService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("deals")
public class DealsController {
	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	DealService dealService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("save-deal")
	public ResponseEntity<?> saveDeal(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest dealReq) {

		logger.info("saveDeal starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDeal(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDeal ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deals")
	public ResponseEntity<?> getDeals(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest dealSearchReq) {

		logger.info("dealSearchReq starts:::" + dealSearchReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDeals(dealSearchReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("dealReq ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal/{dealId}")
	public ResponseEntity<?> getDeal(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDeal starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDeal(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDeal ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/delete-deal/{dealId}")
	public ResponseEntity<?> deleteDeal(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDeal starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.deleteDeal(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDeal ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-notes")
	public ResponseEntity<?> saveNotes(@RequestHeader(value = "Authorization") String token,
			@RequestBody NoteRequest noteRequest) {

		logger.info("saveNotes starts:::" + noteRequest);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveNotes(noteRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveNotes ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-notes")
	public ResponseEntity<?> deleteNotes(@RequestHeader(value = "Authorization") String token,
			@RequestBody Notes note) {

		logger.info("deleteNotes starts:::" + note);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.deleteNotes(note);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteNotes ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-notes")
	public ResponseEntity<?> getAllNotes(@RequestHeader(value = "Authorization") String token,
			@RequestBody Notes note) {

		logger.info("getAllNotes starts:::" + note);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getAllNotes(note);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAllNotes ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-note-attachment")
	public ResponseEntity<?> getNoteAttachment(@RequestHeader(value = "Authorization") String token,
			@RequestBody NoteAttachments noteAttach) {

		logger.info("getNoteAttachment starts:::" + noteAttach);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getNoteAttachment(noteAttach);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getNoteAttachment ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-quotation")
	public ResponseEntity<?> saveDealQuotation(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealQuotation dealReq) {

		logger.info("saveQuotation starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealQuotation(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveQuotation ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-quotation/{dealId}")
	public ResponseEntity<?> getDealQuotation(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getQuotation starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealQuotation(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getQuotation ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-quotation-pdf")
	public ResponseEntity<?> generateQuotationPDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest dealReq) {

		logger.info("generateQuotationPDF starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.generateQuotationPDF(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateQuotationPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-generated-quotation-pdf")
	public ResponseEntity<?> UploadGeneratedQuotationPDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealQuoteId") int dealQuoteId) throws Exception {
		logger.info("UploadGeneratedQuotationPDF starts:::" + dealQuoteId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.UploadGeneratedQuotationPDF(dealQuoteId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadGeneratedQuotationPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-proforma-invoice")
	public ResponseEntity<?> saveDealProformaInvoice(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealProformaInvoice dealInvReq) {

		logger.info("saveDealProformaInvoice starts:::" + dealInvReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealProformaInvoice(dealInvReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealProformaInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-proforma-invoice/{dealId}")
	public ResponseEntity<?> getDealProformaInvoice(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealProformaInvoice starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealProformaInvoice(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealProformaInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-proforma-invoice-pdf")
	public ResponseEntity<?> generateProformaInvoicePDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest dealReq) {

		logger.info("generateProformaInvoicePDF starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.generateProformaInvoicePDF(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateProformaInvoicePDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-generated-proforma-invoice-pdf")
	public ResponseEntity<?> UploadGeneratedProformaInvoicePDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealProformaInvoiceId") int dealInvoiceId)
			throws Exception {
		logger.info("UploadGeneratedProformaInvoicePDF starts:::" + dealInvoiceId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.UploadGeneratedProformaInvoicePDF(dealInvoiceId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadGeneratedProformaInvoicePDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-invoice-preview/{dealId}")
	public ResponseEntity<?> getDealInvoicesPreview(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealInvoice starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealInvoicesPreview(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-purchase-order")
	public ResponseEntity<?> saveDealPurchaseOrder(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealPurchaseOrder dealPOReq) {

		logger.info("saveDealPurchaseOrder starts:::" + dealPOReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealPurchaseOrder(dealPOReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealPurchaseOrder ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-purchase-order/{dealId}")
	public ResponseEntity<?> getDealPurchaseOrder(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealInvoice starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealPurchaseOrder(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-sales-order-pdf")
	public ResponseEntity<?> generateSalesOrderPDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest dealReq) {

		logger.info("generateSalesOrderPDF starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.generateSalesOrderPDF(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateSalesOrderPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-generated-purchase-order-pdf")
	public ResponseEntity<?> UploadPurchaseOrderFile(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealPurchaseOrderId") int dealPurchaseOrderId)
			throws Exception {
		logger.info("UploadPurchaseOrderFile starts:::" + dealPurchaseOrderId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.UploadPurchaseOrderFile(dealPurchaseOrderId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadPurchaseOrderFile ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-sales-order")
	public ResponseEntity<?> saveDealSalesOrder(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSalesOrder dealSOReq) {

		logger.info("saveDealSalesOrder starts:::" + dealSOReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealSalesOrder(dealSOReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealSalesOrder ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-sales-order/{dealId}")
	public ResponseEntity<?> getDealSalesOrder(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealSalesOrder starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealSalesOrder(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealSalesOrder ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-generated-sales-order-pdf")
	public ResponseEntity<?> UploadSalesOrderFile(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealSalesOrderId") int dealSalesOrderId)
			throws Exception {
		logger.info("UploadSalesOrderFile starts:::" + dealSalesOrderId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.UploadSalesOrderFile(dealSalesOrderId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadSalesOrderFile ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-payment")
	public ResponseEntity<?> saveDealPayments(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealPayments dealPayments) {

		logger.info("saveDealPayments starts:::" + dealPayments);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealPayments(dealPayments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-payments/{dealId}")
	public ResponseEntity<?> getDealPayments(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealPayments starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealPayments(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-deal-payment")
	public ResponseEntity<?> deleteDealPayments(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealPayments dealPayments) {

		logger.info("deleteDealPayments starts:::" + dealPayments);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.deleteDealPayments(dealPayments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteDealPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("create-deal-payment-receipt")
	public ResponseEntity<?> createDealPaymentsReceipt(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest dealReq) {

		logger.info("deleteDealPayments starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.createDealPaymentsReceipt(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteDealPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-delivery-challan")
	public ResponseEntity<?> saveDealDeliveryChallan(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealDeliveryChallan dc) {

		logger.info("saveDealDeliveryChallan starts:::" + dc);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealDeliveryChallan(dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealDeliveryChallan ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-deal-delivery-challan/{dealId}")
	public ResponseEntity<?> getDealDeliveryChallan(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealDeliveryChallan starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealDeliveryChallan(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealDeliveryChallan ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-deal-delivery-challan")
	public ResponseEntity<?> deleteDealDeliveryChallan(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealDeliveryChallan dc) {

		logger.info("deleteDealDeliveryChallan starts:::" + dc);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.deleteDealDeliveryChallan(dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteDealDeliveryChallan ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-pending-dc-quantity-products")
	public ResponseEntity<?> getPendingDCQuantityProducts(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest req) {

		logger.info("getPendingInvoiceQuantityProducts starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getPendingDCQuantityProducts(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getPendingInvoiceQuantityProducts ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-quotations-report")
	public ResponseEntity<?> getDealQuotationsReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealQuotations starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealQuotationsReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealQuotations ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-purchase-orders-report")
	public ResponseEntity<?> getDealPurchaseOrdersReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealPurchaseOrders starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealPurchaseOrdersReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealPurchaseOrders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-project-implementations-report")
	public ResponseEntity<?> getDealProjectImplementationsReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealProjectImplementationsReport starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealProjectImplementationsReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealProjectImplementationsReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-sales-orders-report")
	public ResponseEntity<?> getDealSalesOrdersReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealSalesOrders starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealSalesOrdersReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealSalesOrders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-proforma-invoices-report")
	public ResponseEntity<?> getDealProformaInvoicesReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealProformaInvoices starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealProformaInvoicesReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealProformaInvoices ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-delivery-challans-report")
	public ResponseEntity<?> getDealDeliveryChallansReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealQuotations starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealDeliveryChallansReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealQuotations ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-payments-report")
	public ResponseEntity<?> getDealPaymentsReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealSearchRequest req) {

		logger.info("getDealPayments starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealPaymentsReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-email")
	public ResponseEntity<?> saveDealEmail(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealEmail email) {

		logger.info("saveDealEmail starts:::" + email.toString());
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealEmail ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-deal-email-attachment")
	public ResponseEntity<?> addDealEmailAttachment(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealEmailId") int dealEmailId) throws Exception {
		logger.info("addDealEmailAttachment starts:::" + dealEmailId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.addDealEmailAttachment(dealEmailId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("addDealEmailAttachment ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("send-deal-email")
	public ResponseEntity<?> sendDealEmail(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealEmail email) throws Exception {
		logger.info("sendDealEmail starts:::" + email);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.sendDealEmail(email);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("sendDealEmail ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-email")
	public ResponseEntity<?> getDealEmail(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealEmail email) throws Exception {
		logger.info("getDealEmail starts:::" + email);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = dealService.getDealEmail(email);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getDealEmail ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-amc-payments/{id}")
	public ResponseEntity<?> getAmcPayment(@RequestHeader(value = "Authorization") String token,
			@PathVariable("id") int id) {

		logger.info("getAmcPayments starts:::" + id);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getAmcPayment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAmcPayments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-project-implementation")
	public ResponseEntity<?> saveDealProjectImplementation(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealProjectImplementation dealProjectImplementation) {

		logger.info("saveDealProjectImplementation starts:::" + dealProjectImplementation);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealProjectImplementation(dealProjectImplementation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealProjectImplementation ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-project-implementation/{dealId}")
	public ResponseEntity<?> getDealProjectImplementation(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) {

		logger.info("getDealProjectImplementation starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealProjectImplementation(dealId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealProjectImplementation ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-ticket-project-implementations")
	public ResponseEntity<?> getDealProjectImplementation(@RequestHeader(value = "Authorization") String token,
			@RequestBody ProjectImplemantationRequest projectReq) {

		logger.info("getDealProjectImplementation starts:::" + projectReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getDealProjectImplementations(projectReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealProjectImplementation ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-deal-project-implementation-comments")
	public ResponseEntity<?> saveDealProjectImplementationComments(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealProjectImplementationComments comment) {

		logger.info("saveDealProjectImplementationComments starts:::" + comment);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.saveDealProjectImplementationComments(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealProjectImplementationComments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-deal-project-implementation-comments")
	public ResponseEntity<?> deleteDealProjectImplementationComments(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody DealProjectImplementationComments comment) {

		logger.info("deleteDealProjectImplementationComments starts:::" + comment);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.deleteDealProjectImplementationComments(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteDealProjectImplementationComments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-deal-project-implementation-comments")
	public ResponseEntity<?> getAllDealProjectImplementationComments(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody DealProjectImplementationComments comment) {

		logger.info("getAllDealProjectImplementationComments starts:::" + comment);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.getAllDealProjectImplementationComments(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAllDealProjectImplementationComments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-deal-instamojo-payment-url")
	public ResponseEntity<?> generateDealInstamojoPaymentURL(@RequestHeader(value = "Authorization") String token,
			@RequestBody Deal deal) {

		logger.info("generateDealInstamojoPaymentURL starts:::" + deal);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.generateDealInstamojoPaymentURL(deal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateDealInstamojoPaymentURL ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-delivery-challan")
	public ResponseEntity<?> generateDeliveryChallanPDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealRequest dealReq) {

		logger.info("generateDeliveryChallanPDF starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = dealService.generateDeliveryChallanPDF(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateDeliveryChallanPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
