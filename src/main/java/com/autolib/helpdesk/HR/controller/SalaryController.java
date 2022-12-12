package com.autolib.helpdesk.HR.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.HR.entity.SalaryDetails;
import com.autolib.helpdesk.HR.entity.SalaryDetailsRequest;
import com.autolib.helpdesk.HR.entity.SalaryEntries;
import com.autolib.helpdesk.HR.entity.SalaryEntriesRequest;
import com.autolib.helpdesk.HR.service.SalaryService;

@RestController
@Controller
@RequestMapping("salary")
@CrossOrigin("*")
public class SalaryController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired

	private SalaryService salaryService;

	@SuppressWarnings("unchecked")
	@PostMapping("salary-needed-details")
	public ResponseEntity<?> getSalaryNeededDetails(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("Salary Details Starts:::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();

		List<String> bankname = new ArrayList<>();

		try {
			List<String> needed = (List<String>) req.get("needed");
			for (String need : needed) {

				if (need.equalsIgnoreCase("bankname")) {
					bankname = salaryService.bankname(need);
				}

			}
		} catch (Exception ex) {
			logger.error("Error needed:::::::" + ex);
			ex.printStackTrace();
		}

		resp.put("bankname", bankname);

		logger.info("Salary Details ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("save-salary-details")
	public ResponseEntity<?> saveSalaryDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryDetailsRequest req) {
		logger.info("Save_Salary Details Starts::::::" + req.toString());
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.saveSalaryDetails(req);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("get-salary-details")
	public ResponseEntity<?> getStaffDetails(@RequestHeader(value = "Authorization") String token) {
		logger.info("Get Staff_Salary Details Starts::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.getStaffDetails();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-salary-details-edit/{sid}")
	public ResponseEntity<?> getStaffDetailsEdit(@RequestHeader(value = "Authorization") String token,
			@PathVariable("sid") String sid) {
		logger.info("getStaffDetailsEdit starts:::" + sid);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			resp = salaryService.getStaffDetailsEdit(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getStaffDetailsEdit ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("delete-salary-details")
	public ResponseEntity<?> getStaffDetailsEdit(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryDetails salaryDetail) {
		logger.info("getStaffDetailsEdit starts:::" + salaryDetail);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			resp = salaryService.deleteStaffDetails(salaryDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getStaffDetailsEdit ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("get-salary-entries")
	public ResponseEntity<?> getSalaryEntries(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryEntriesRequest request) {
		logger.info("Get Staff_Salary Details Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.getSalaryEntries(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-salary-entries-edit/{sid}")
	public ResponseEntity<?> getStaffEntriesEdit(@RequestHeader(value = "Authorization") String token,
			@PathVariable("sid") String sid) {
		logger.info("getStaffDetailsEdit starts:::" + sid);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			resp = salaryService.getStaffEntriesEdit(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getStaffDetailsEdit ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-salary-entries")
	public ResponseEntity<?> deleteStaffEntries(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryEntries salaryEntry) {
		logger.info("getStaffEntriesEdit starts:::" + salaryEntry);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			resp = salaryService.deleteStaffEntries(salaryEntry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getStaffEntriesEdit ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-salary-entries")
	public ResponseEntity<?> generateSalaryEntries(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryDetailsRequest request) {
		logger.info("Get generateSalaryEntries Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.generateSalaryEntries(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-salary-entries-payslip")
	public ResponseEntity<?> generateSalaryEntryPayslip(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryEntries request) {
		logger.info("Get generateSalaryEntries Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.generateSalaryEntryPayslip(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("generate-payslips")
	public ResponseEntity<?> generatePayslips(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryEntriesRequest request) {
		logger.info("Get generatePayslips Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.generatePayslips(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("Get generatePayslips Ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("send-payslips-mail")
	public ResponseEntity<?> sendPayslipsMail(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryEntriesRequest request) {
		logger.info("Get sendPayslipsMail Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.sendPayslipsMail(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("Get sendPayslipsMail Ends::::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("mark-salary-entries-credited")
	public ResponseEntity<?> updateSalaryEntries(@RequestHeader(value = "Authorization") String token,
			@RequestBody SalaryEntriesRequest request) {
		logger.info("Get generateSalaryEntries Starts::::::" + request);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = salaryService.updateSalaryEntries(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
