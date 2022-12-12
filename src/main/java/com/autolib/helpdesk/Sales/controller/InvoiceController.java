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

import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceSearchRequest;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;
import com.autolib.helpdesk.Sales.service.InvoiceService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("invoice")
public class InvoiceController {
	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	InvoiceService invService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("save-deal-invoice")
	public ResponseEntity<?> saveDealInvoice(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceRequest dealInvReq) {

		logger.info("saveDealInvoice starts:::" + dealInvReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.saveDealInvoice(dealInvReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveDealInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-deal-invoice/{invoiceId}")
	public ResponseEntity<?> getDealInvoice(@RequestHeader(value = "Authorization") String token,
			@PathVariable("invoiceId") int invoiceId) {

		logger.info("getDealInvoice starts:::" + invoiceId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.getDealInvoice(invoiceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-next-invoice-no")
	public ResponseEntity<?> getNextInvoiceNo(@RequestHeader(value = "Authorization") String token) {

		logger.info("getNextInvoiceNo starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.getNextInvoiceNo();

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getNextInvoiceNo ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-delivery-challan")
	public ResponseEntity<?> generateDeliveryChallanPDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceRequest dealReq) {

		logger.info("generateDeliveryChallanPDF starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.generateDeliveryChallanPDF(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateDeliveryChallanPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-invoice-pdf")
	public ResponseEntity<?> generateInvoicePDF(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceRequest dealReq) {

		logger.info("generateInvoicePDF starts:::" + dealReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.generateInvoicePDF(dealReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("generateInvoicePDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-generated-invoice-pdf")
	public ResponseEntity<?> UploadGeneratedInvoicePDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealInvoiceId") int dealInvoiceId)
			throws Exception {
		logger.info("UploadGeneratedInvoicePDF starts:::" + dealInvoiceId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.UploadGeneratedInvoicePDF(dealInvoiceId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadGeneratedInvoicePDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-invoice-satisfactory-file")
	public ResponseEntity<?> UploadInvoiceSatisfactoyPDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealInvoiceId") int dealInvoiceId)
			throws Exception {
		logger.info("UploadInvoiceSatisfactoyPDF starts:::" + dealInvoiceId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.UploadInvoiceSatisfactoyPDF(dealInvoiceId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadInvoiceSatisfactoyPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-invoice-work-completion-file")
	public ResponseEntity<?> UploadInvoiceWorkCompletionPDF(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("dealInvoiceId") int dealInvoiceId)
			throws Exception {
		logger.info("UploadInvoiceWorkCompletionPDF starts:::" + dealInvoiceId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.UploadInvoiceWorkCompletionPDF(dealInvoiceId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("UploadInvoiceWorkCompletionPDF ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-deal-invoices-report")
	public ResponseEntity<?> getDealInvoicesReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceSearchRequest req) {

		logger.info("getDealInvoices starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.getDealInvoicesReport(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getDealInvoices ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

//	@PostMapping("save-deal-payment")
//	public ResponseEntity<?> saveDealPayments(@RequestHeader(value = "Authorization") String token,
//			@RequestBody DealPayments dealPayments) {
//
//		logger.info("saveDealPayments starts:::" + dealPayments);
//		jwtUtil.isValidToken(token);
//		Map<String, Object> resp = new HashMap<>();
//
//		try {
//			resp = invService.saveDealPayments(dealPayments);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("saveDealPayments ends:::");
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}

//	@GetMapping("/get-deal-payments/{invoiceId}")
//	public ResponseEntity<?> getDealPayments(@RequestHeader(value = "Authorization") String token,
//			@PathVariable("invoiceId") int invoiceId) {
//
//		logger.info("getInvoicePayments starts:::" + invoiceId);
//		jwtUtil.isValidToken(token);
//		Map<String, Object> resp = new HashMap<>();
//
//		try {
//			resp = invService.getDealPayments(invoiceId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("getDealPayments ends:::");
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}
//
//	@PostMapping("delete-deal-payment")
//	public ResponseEntity<?> deleteDealPayments(@RequestHeader(value = "Authorization") String token,
//			@RequestBody DealPayments dealPayments) {
//
//		logger.info("deleteDealPayments starts:::" + dealPayments);
//		jwtUtil.isValidToken(token);
//		Map<String, Object> resp = new HashMap<>();
//
//		try {
//			resp = invService.deleteDealPayments(dealPayments);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("deleteDealPayments ends:::");
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}
//
//	@PostMapping("create-deal-payment-receipt")
//	public ResponseEntity<?> createDealPaymentsReceipt(@RequestHeader(value = "Authorization") String token,
//			@RequestBody DealRequest dealReq) {
//
//		logger.info("createDealPaymentsReceipt starts:::" + dealReq);
//		jwtUtil.isValidToken(token);
//		Map<String, Object> resp = new HashMap<>();
//
//		try {
//			resp = invService.createDealPaymentsReceipt(dealReq);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("createDealPaymentsReceipt ends:::");
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}
//
//	@PostMapping("get-deal-payments-report")
//	public ResponseEntity<?> getDealPaymentsReport(@RequestHeader(value = "Authorization") String token,
//			@RequestBody DealInvoiceSearchRequest req) {
//
//		logger.info("getDealPayments starts:::" + req);
//		jwtUtil.isValidToken(token);
//		Map<String, Object> resp = new HashMap<>();
//
//		try {
//			resp = invService.getDealPaymentsReport(req);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("getDealPayments ends:::");
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}

	@PostMapping("get-pending-invoice-quantity-products")
	public ResponseEntity<?> getPendingInvoiceQuantityProducts(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceSearchRequest req) {

		logger.info("getPendingInvoiceQuantityProducts starts:::" + req);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.getPendingInvoiceQuantityProducts(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getPendingInvoiceQuantityProducts ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-invoice-email")
	public ResponseEntity<?> saveInvoiceEmail(@RequestHeader(value = "Authorization") String token,
			@RequestBody InvoiceEmail email) {

		logger.info("saveInvoiceEmail starts:::" + email.toString());
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = invService.saveInvoiceEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveInvoiceEmail ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-invoice-email-attachment")
	public ResponseEntity<?> addInvoiceEmailAttachment(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("invoiceEmailId") int invoiceEmailId)
			throws Exception {
		logger.info("addInvoiceEmailAttachment starts:::" + invoiceEmailId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.addInvoiceEmailAttachment(invoiceEmailId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("addInvoiceEmailAttachment ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("send-invoice-email")
	public ResponseEntity<?> sendInvoiceEmail(@RequestHeader(value = "Authorization") String token,
			@RequestBody InvoiceEmail email) throws Exception {
		logger.info("sendInvoiceEmail starts:::" + email);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.sendInvoiceEmail(email);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("sendInvoiceEmail ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("load-invoice-email-reminder-settings")
	public ResponseEntity<?> loadInvoiceEmailReminderSettings(@RequestHeader(value = "Authorization") String token)
			throws Exception {
		logger.info("loadInvoiceEmailReminderSettings starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.loadInvoiceEmailReminderSettings();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("loadInvoiceEmailReminderSettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-invoice-email-reminder-settings")
	public ResponseEntity<?> saveInvoiceEmailReminderSettings(@RequestHeader(value = "Authorization") String token,
			@RequestBody InvoiceEmailReminderSettings settings) throws Exception {
		logger.info("saveInvoiceEmailReminderSettings starts:::" + settings);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.saveInvoiceEmailReminderSettings(settings);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveInvoiceEmailReminderSettings ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-invoice-reminders")
	public ResponseEntity<?> saveInvoiceReminders(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceReminderRequest request) throws Exception {
		logger.info("saveInvoiceReminders starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.saveInvoiceReminders(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveInvoiceReminders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-invoice-reminders")
	public ResponseEntity<?> deleteInvoiceReminders(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceReminderRequest request) throws Exception {
		logger.info("deleteInvoiceReminders starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.deleteInvoiceReminders(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deleteInvoiceReminders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-invoice-reminders/{dealId}")
	public ResponseEntity<?> getInvoiceReminders(@RequestHeader(value = "Authorization") String token,
			@PathVariable("dealId") int dealId) throws Exception {
		logger.info("getInvoiceReminders starts:::" + dealId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.getInvoiceReminders(dealId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getInvoiceReminders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("search-invoice-reminders")
	public ResponseEntity<?> searchInvoiceReminders(@RequestHeader(value = "Authorization") String token,
			@RequestBody DealInvoiceReminderRequest request) throws Exception {
		logger.info("searchInvoiceReminders starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = invService.searchInvoiceReminders(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("searchInvoiceReminders ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
