package com.autolib.helpdesk.Institutes.controller;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Institutes.model.AMCDetails;
import com.autolib.helpdesk.Institutes.model.AMCReminderRequest;
import com.autolib.helpdesk.Institutes.model.AMCSearchRequest;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.model.InstituteContactRequest;
import com.autolib.helpdesk.Institutes.model.InstituteImportReq;
import com.autolib.helpdesk.Institutes.model.InstituteProducts;
import com.autolib.helpdesk.Institutes.model.InstituteProductsRequest;
import com.autolib.helpdesk.Institutes.model.InstituteRequest;
import com.autolib.helpdesk.Institutes.model.InvoiceRequest;
import com.autolib.helpdesk.Institutes.service.InstituteService;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("institute")
public class InstituteController {
	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	InstituteService instService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("save-institute")
	public ResponseEntity<?> saveInstitute(@RequestHeader(value = "Authorization") String token,
			@RequestBody Institute institute) {

		logger.info("saveInstitute starts:::" + institute);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = instService.saveInstitute(institute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveInstitute ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("edit-institute")
	public ResponseEntity<?> editInstitute(@RequestHeader(value = "Authorization") String token,
			@RequestBody Institute institute) {
		logger.info("saveInstitute starts:::" + institute);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = instService.editInstitute(institute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("editInstitute ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-institute-details")
	public ResponseEntity<?> getInstituteDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody Institute institute) {
		logger.info("getInstituteDetails starts:::" + institute);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.getInstituteDetails(institute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getInstituteDetails ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-institute-pre-delete-data")
	public ResponseEntity<?> getInstitutePreDeleteData(@RequestHeader(value = "Authorization") String token,
			@RequestBody Institute institute) throws Exception {
		logger.info("getInstitutePreDeleteData Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.getInstitutePreDeleteData(institute);
		} catch (Exception Ex) {
			logger.error("getInstitutePreDeleteDate:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstitutePreDeleteData ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("delete-institute")
	public ResponseEntity<?> deleteInstitute(@RequestHeader(value = "Authorization") String token,
			@RequestBody Institute institute) {
		logger.info("deleteInstitute starts:::" + institute);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.deleteInstitute(institute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteInstitute ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-institute-contact")
	public ResponseEntity<?> saveInstituteContact(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteContact ic) {
		logger.info("saveInstituteContact starts:::" + ic);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.saveInstituteContact(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("saveInstituteContact ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("send-login-details")
	public ResponseEntity<?> sendInstituteContact(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteContact ic) {
		logger.info("sendInstituteContact starts:::" + ic);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.sendInstituteContact(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("sendInstituteContact ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-institute-contacts")
	public ResponseEntity<?> getInstituteContacts(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteContact ic) {
		logger.info("getInstituteContacts starts:::" + ic);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.getInstituteContacts(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getInstituteContacts ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-institute-contact")
	public ResponseEntity<?> deleteInstituteContact(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteContact ic) {
		logger.info("deleteInstituteContact starts:::" + ic);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.deleteInstituteContact(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteInstituteContact ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("changePassword")
	public ResponseEntity<?> changePassword(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> req) throws Exception {
		logger.info("changePassword starts:::" + req);
		System.out.println("changePassword req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.changePassword(req);
		} catch (Exception Ex) {

			Ex.printStackTrace();
		}
		logger.info("changePassword ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("forgetPassword starts:::" + req);
		System.out.println("forgetPassword req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.forgetPassword(req);
		} catch (Exception Ex) {

			Ex.printStackTrace();
		}
		logger.info("forgetPassword ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("resetPassword starts:::" + req);
		System.out.println("resetPassword req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.resetPassword(req);
		} catch (Exception Ex) {

			Ex.printStackTrace();
		}
		logger.info("checkOTP req ends::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("checkOTP")
	public ResponseEntity<?> checkOTP(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("checkOTP req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.checkOTP(req);

		} catch (Exception Ex) {
			System.out.println("Error checkUserExist:::::::" + Ex);
			Ex.printStackTrace();
		}
		logger.info("checkOTP req ends::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-amc-details")
	public ResponseEntity<?> saveAmcDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody AMCDetails amc) throws Exception {
		logger.info("saveAmc Details starts:::" + amc);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.saveAmcDetails(amc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveAmc Details Ends:::" + amc);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("load-institute")
	public ResponseEntity<?> loadInstitute() throws Exception {
		logger.info("load-institute starts:::");
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			respMap = instService.loadInstitute();

		} catch (Exception Ex) {
			logger.error("Error load-institute:::::::" + Ex);
			Ex.printStackTrace();
		}
		logger.debug("load-institute req ends::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("amc-report")
	public ResponseEntity<?> amcReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> req) throws Exception {
		logger.info("Amc-Report Starts::::::" + req.toString());
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {

			respMap = instService.amcReport(req);

		} catch (Exception Ex) {
			logger.error("Error Amc-Report:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("Amc-Report ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("save-institute-products")
	public ResponseEntity<?> saveInstituteProducts(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteProducts ip) throws Exception {
		logger.info("saveInstituteProducts Starts::::::" + ip);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.saveInstituteProducts(ip);
		} catch (Exception Ex) {
			logger.error("saveInstituteProducts:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("saveInstituteProducts ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("remove-institute-products")
	public ResponseEntity<?> removeInstituteProducts(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteProducts ip) throws Exception {
		logger.info("saveInstituteProducts Starts::::::" + ip);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.removeInstituteProducts(ip);
		} catch (Exception Ex) {
			logger.error("saveInstituteProducts:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("saveInstituteProducts ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("get-institute-products")
	public ResponseEntity<?> getInstituteProducts(@RequestBody InstituteProducts ip) throws Exception {
		logger.info("getInstituteProducts Starts::::::" + ip);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.getInstituteProducts(ip);
		} catch (Exception Ex) {
			logger.error("getInstituteProducts:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstituteProducts ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("get-institute-products-all-report-data")
	public ResponseEntity<?> getInstituteProductsAllReportData(@RequestHeader(value = "Authorization") String token)
			throws Exception {
		logger.info("getInstituteProductsAllReportData Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.getInstituteProductsAllReportData();
		} catch (Exception Ex) {
			logger.error("getInstituteProductsAllReportData:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstituteProductsAll ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("get-institute-products-all")
	public ResponseEntity<?> getInstituteProductsAll(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteProductsRequest ipr) throws Exception {
		logger.info("getInstituteProductsAll Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.getInstituteProductsAll(ipr);
		} catch (Exception Ex) {
			logger.error("getInstituteProductsAll:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstituteProductsAll ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("save-institute-products-all")
	public ResponseEntity<?> saveInstituteProductsAll(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteProductsRequest ipr) throws Exception {
		logger.info("saveInstituteProductsAll Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.saveInstituteProductsAll(ipr);
		} catch (Exception Ex) {
			logger.error("saveInstituteProductsAll:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstituteProductsAll ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("getInstituteAmc")
	public ResponseEntity<?> getInstituteAmc(@RequestHeader(value = "Authorization") String token,
			@RequestBody AMCDetails amc) throws Exception {
		logger.info("getInstituteAmc Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.InstituteAmc(amc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getInstituteAmc ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("create-invoice")
	public ResponseEntity<?> createInvoice(@RequestHeader(value = "Authorization") String token,
			@RequestBody InvoiceRequest ir) throws Exception {
		logger.info("createInvoice Starts::::::" + ir);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = instService.createInvoice(ir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("createInvoice ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("load-amc-details")
	public ResponseEntity<?> loadAMCDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody AMCSearchRequest amcSearchReq) throws Exception {
		logger.error("Get AMC_Details:::::::" + amcSearchReq);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.loadAMCDetails(amcSearchReq);

		} catch (Exception Ex) {
			logger.error("Error AMC_Details:::::::" + Ex);
			Ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@GetMapping("/get-amc-details-edit/{aid}")
	public ResponseEntity<?> getAmcDetailsEdit(@RequestHeader(value = "Authorization") String token,
			@PathVariable("aid") int aid) {
		logger.info("getAmcDetailsEdit starts:::" + aid);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			resp = instService.getAmcDetailsEdit(aid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAmcDetailsEdit ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("get-institute-products-expiry-reminder")
	public ResponseEntity<?> getInstituteProductsExpiryReminder(@RequestHeader(value = "Authorization") String token)
			throws Exception {
		logger.info("getInstituteProductsAll Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.getInstituteProductsExpiryReminder();
		} catch (Exception Ex) {
			logger.error("getInstituteProductsAll:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstituteProductsAll ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("get-institute-amc-reminder-sent-report")
	public ResponseEntity<?> getInstituteAMCReminderSentReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody AMCReminderRequest amcReminderReq) throws Exception {
		logger.info("getInstituteAMCReminderSentReport Starts::::::");
		jwtUtil.isValidToken(token);
		Map<String, Object> respMap = new HashMap<String, Object>();

		try {
			respMap = instService.getInstituteAMCReminderSentReport(amcReminderReq);
		} catch (Exception Ex) {
			logger.error("getInstituteProductsAll:::::::" + Ex);
			Ex.printStackTrace();
		}

		logger.info("getInstituteAMCReminderSentReport ends::::::");
		return new ResponseEntity<>(respMap, HttpStatus.OK);
	}

	@PostMapping("get-institute-contact")
	public ResponseEntity<?> getInstContact(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteContactRequest instContactReq) throws Exception {
		logger.error("Get Institute Contact:::::::" + instContactReq);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.getInstContact(instContactReq);

		} catch (Exception Ex) {
			logger.error("Error Institute Contact:::::::" + Ex);
			Ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("institute-report")
	public ResponseEntity<?> getInstituteReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteRequest institute) {
		logger.info("Instiute Report Starts:::::::::" + institute);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.getInstituteReport(institute);
		} catch (Exception Ex) {
			logger.error("Instiute Report Starts:::::::::" + Ex);
			Ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("logo-upload")
	public ResponseEntity<?> saveInstituteLogo(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("instituteId") String instituteId) {
		logger.info("saveInstituteLogo:::::::::" + instituteId);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			jwtUtil.isValidToken(token);
			resp = instService.saveInstituteLogo(file, instituteId);
		} catch (Exception Ex) {
			logger.error("saveInstituteLogo Ends:::::::::" + Ex);
			Ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-invoice-amc-entries")
	public ResponseEntity<?> getInvoiceAMCEntries(@RequestHeader(value = "Authorization") String token,
			@RequestBody String req) throws Exception {
		logger.error("Get Invoice AMC_Details:::::::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = instService.getInvoiceAMCEntries(req);

		} catch (Exception Ex) {
			logger.error("Error Invoice AMC_Details:::::::" + Ex);
			Ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("save-institute-import")
	public ResponseEntity<?> saveInstituteImport(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteImportReq request) {
		System.out.println("saveInstituteImport req starts::" + request);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = instService.saveInstituteImport(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("update-institute-import")
	public ResponseEntity<?> updatewInstituteImport(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteImportReq request) {
		System.out.println("updateInstituteImport req starts::" + request);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = instService.updateInstituteImport(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-address-from-pincode/{pincode}")
	public ResponseEntity<?> getAddressFromPincode(@RequestHeader(value = "Authorization") String token,
			@PathVariable("pincode") int pincode) {
		logger.info("getAddressFromPincode starts:::" + pincode);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			RestTemplate restTemp = new RestTemplate();

			ResponseEntity<String> response = restTemp
					.getForEntity("http://www.postalpincode.in/api/pincode/" + pincode, String.class);

			ObjectMapper mapper = new ObjectMapper();
			resp.put("HttpResponse", mapper.readTree(response.getBody()));

			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		logger.info("getAddressFromPincode ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

}
