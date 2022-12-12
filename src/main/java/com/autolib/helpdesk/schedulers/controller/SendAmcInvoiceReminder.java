package com.autolib.helpdesk.schedulers.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Institutes.model.InstituteProductsRequest;
import com.autolib.helpdesk.schedulers.model.LogsSchedulerInvoice;

@RequestMapping("scheduler")
@Controller
@RestController
@CrossOrigin("*")
public class SendAmcInvoiceReminder {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	InvoiceSender invoiceSender;

	@GetMapping("sendScheduledInvoiceReminder")
	public ResponseEntity<?> AmcInvoiceReminder() {

		logger.info("AmcInvoiceReminder starts:::");
		Map<String, Object> resp = new HashMap<>();

		try {
			invoiceSender.sendScheduledInvoiceReminder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AmcInvoiceReminder ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("sendAMCReminder")
	public ResponseEntity<?> sendAMCReminder(@RequestHeader(value = "Authorization") String token,
			@RequestBody InstituteProductsRequest ips) {

		logger.info("sendAMCReminder starts:::");
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = invoiceSender.sendAMCReminder(ips.getInstituteProducts());

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("sendAMCReminder ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("resendAMCReminderMail")
	public ResponseEntity<?> resendAMCReminderMail(@RequestHeader(value = "Authorization") String token,
			@RequestBody LogsSchedulerInvoice log) {

		logger.info("resendAMCReminderMail starts:::" + log.toString());
		Map<String, Object> resp = new HashMap<>();

		try {

			resp = invoiceSender.resendAMCReminderMail(log);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("resendAMCReminderMail ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@Scheduled(cron = "0 11 8 * * *")
	void execute() {

		logger.info("execute stars:::");
		AmcInvoiceReminder();

	}

}
