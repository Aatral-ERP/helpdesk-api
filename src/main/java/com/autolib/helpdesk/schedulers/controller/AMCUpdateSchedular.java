package com.autolib.helpdesk.schedulers.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.autolib.helpdesk.Institutes.repository.InstituteProductRepository;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;

@Component
public class AMCUpdateSchedular {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	InstituteProductRepository instProdRepo;

	@Scheduled(cron = "0 1 1 * * *")
	void AmcInvoiceReminder() {

		logger.info("AmcInvoiceReminder starts:::");
		Map<String, Object> resp = new HashMap<>();

		try {
			instProdRepo.updateAMCStatus(ServiceUnder.ServiceCall, new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AmcInvoiceReminder ends:::" + resp);
	}

}
