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

import com.autolib.helpdesk.Sales.model.TermsAndConditions;
import com.autolib.helpdesk.Sales.service.SalesService;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("sales")
public class SalesController {
	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	SalesService salesService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("get-sales-needed-data")
	public ResponseEntity<?> getSalesNeededData(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> needed) throws Exception {
		logger.info("getSalesNeededData starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.getSalesNeededData(needed);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getSalesNeededData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-sales-dashboard-data")
	public ResponseEntity<?> getSalesDashboardData(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> req) throws Exception {
		logger.info("getSalesDashboardData starts:::" + req.toString());
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.getSalesDashboardData(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getSalesDashboardData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("upload-preamble-document")
	public ResponseEntity<?> uploadPreambleDocuments(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file) throws Exception {
		logger.info("savePreambleDocuments starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.uploadPreambleDocuments(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("savePreambleDocuments ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-preamble-document-list")
	public ResponseEntity<?> getPreambleDocumentsList(@RequestHeader(value = "Authorization") String token)
			throws Exception {
		logger.info("getPreambleDocumentsList starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.getPreambleDocumentsList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getPreambleDocumentsList ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("delete-preamble-document/{filename}")
	public ResponseEntity<?> deletePreambleDocument(@RequestHeader(value = "Authorization") String token,
			@PathVariable("filename") String filename) throws Exception {
		logger.info("deletePreambleDocument starts:::" + filename);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.deletePreambleDocumentsList(filename);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deletePreambleDocument ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-terms-and-conditions")
	public ResponseEntity<?> saveTermsAndConditions(@RequestHeader(value = "Authorization") String token,
			@RequestBody TermsAndConditions terms) throws Exception {
		logger.info("saveTermsAndConditions starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.saveTermsAndConditions(terms);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveTermsAndConditions ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-terms-and-conditions")
	public ResponseEntity<?> deleteTermsAndConditions(@RequestHeader(value = "Authorization") String token,
			@RequestBody TermsAndConditions terms) throws Exception {
		logger.info("deleteTermsAndConditions starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.deleteTermsAndConditions(terms);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deleteTermsAndConditions ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-terms-and-conditions/{type}")
	public ResponseEntity<?> getTermsAndConditions(@RequestHeader(value = "Authorization") String token,
			@PathVariable("type") String type) throws Exception {
		logger.info("getTermsAndConditions starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = salesService.getTermsAndConditions(type);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getTermsAndConditions ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
