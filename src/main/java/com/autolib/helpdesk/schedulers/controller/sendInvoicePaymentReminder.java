package com.autolib.helpdesk.schedulers.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;
import com.autolib.helpdesk.Sales.repository.Invoice.InvoiceEmailReminderSettingsRepository;
import com.autolib.helpdesk.Sales.service.InvoiceService;
import com.autolib.helpdesk.common.Util;

@RequestMapping("scheduler")
@Controller
@RestController
@CrossOrigin("*")
public class sendInvoicePaymentReminder {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	InvoiceEmailReminderSettingsRepository reminderSettingRepo;

	@Autowired
	InvoiceService invServ;

	@Autowired
	InfoDetailsRepository infoRepo;

	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	@GetMapping("sendScheduledInvoicePaymentReminder")
	public ResponseEntity<?> sendScheduledInvoicePaymentReminder() {

		logger.info("sendScheduledInvoicePaymentReminder starts:::");
		Map<String, Object> resp = new HashMap<>();

		try {

			InvoiceEmailReminderSettings setting = reminderSettingRepo.findById(1);

			if (setting != null && setting.isSendReminderEmail()) {

				String datediffin = "";

				if (setting.isDaysAfter30())
					datediffin = datediffin + ", -30 ";

				if (setting.isDaysAfter15())
					datediffin = datediffin + ", -15 ";

				if (setting.isDaysAfter7())
					datediffin = datediffin + ", -7 ";

				if (setting.isDaysAfter1())
					datediffin = datediffin + ", -1 ";

				if (setting.isDaysBefore0())
					datediffin = datediffin + ", 0 ";

				if (setting.isDaysBefore7())
					datediffin = datediffin + ", 7 ";

				if (setting.isDaysBefore15())
					datediffin = datediffin + ", 15 ";

				if (datediffin.length() > 0) {

					datediffin = datediffin.replaceFirst(",", "");

					Query query = em.createQuery("select di from DealInvoice di "
							+ "where di.grandTotal > di.paidAmount and datediff(di.dueDate , curdate()) in ("
							+ datediffin + ") ", DealInvoice.class);

					List<DealInvoice> invoices = query.getResultList();

					InfoDetails info = infoRepo.findById(1);

					System.out.println(invoices.size());

					invoices.stream().filter(inv -> inv.getFilename() != null && !inv.getFilename().isEmpty())
							.filter(inv -> Util.validateEmailID(inv.getInstitute().getEmailId())
									|| Util.validateEmailID(inv.getInstitute().getAlternateEmailId()))
							.forEach(inv -> {

								logger.info("Sending Reminder to Invoice #" + inv.getInvoiceNo());

								String subject = setting.getReminderSubjectTemplate().replaceAll("\n", "<br>")
										.replaceAll("\\$\\{invoiceNo\\}", inv.getInvoiceNo())
										.replaceAll("\\$\\{invoiceDate\\}",
												Util.sdfFormatter(inv.getInvoiceDate(), "dd/MM/yyyy"))
										.replaceAll("\\$\\{grandTotal\\}",
												"Rs." + Util.decimalFormatter(inv.getGrandTotal()))
										.replaceAll("\\$\\{paymentLink\\}", info.getInstamojoPaymentURL())
										.replaceAll("\\$\\{cmpName\\}", info.getCmpName())
										.replaceAll("\\$\\{cmpAddress\\}", info.getCmpAddress())
										.replaceAll("\\$\\{cmpPhone\\}", info.getCmpPhone())
										.replaceAll("\\$\\{cmpLandLine\\}", info.getCmpLandLine())
										.replaceAll("\\$\\{cmpEmail\\}", info.getCmpEmail())
										.replaceAll("\\$\\{cmpWebsiteUrl\\}", info.getCmpWebsiteUrl());
								String message = setting.getReminderContentTemplate().replaceAll("\n", "<br>")
										.replaceAll("\\$\\{invoiceNo\\}", inv.getInvoiceNo())
										.replaceAll("\\$\\{invoiceDate\\}",
												Util.sdfFormatter(inv.getInvoiceDate(), "dd/MM/yyyy"))
										.replaceAll("\\$\\{grandTotal\\}",
												"Rs." + Util.decimalFormatter(inv.getGrandTotal()))
										.replaceAll("\\$\\{paymentLink\\}", info.getInstamojoPaymentURL())
										.replaceAll("\\$\\{cmpName\\}", info.getCmpName())
										.replaceAll("\\$\\{cmpAddress\\}", info.getCmpAddress())
										.replaceAll("\\$\\{cmpPhone\\}", info.getCmpPhone())
										.replaceAll("\\$\\{cmpLandLine\\}", info.getCmpLandLine())
										.replaceAll("\\$\\{cmpEmail\\}", info.getCmpEmail())
										.replaceAll("\\$\\{cmpWebsiteUrl\\}", info.getCmpWebsiteUrl());

								InvoiceEmail email = new InvoiceEmail();
								email.setInvoiceId(inv.getId());

								email.setSubject(subject);
								email.setMessage(message);
								email.setTab("Invoice");
								if (Util.validateEmailID(inv.getInstitute().getEmailId()))
									email.setMailIds(inv.getInstitute().getEmailId());
								else if (Util.validateEmailID(inv.getInstitute().getAlternateEmailId()))
									email.setMailIds(inv.getInstitute().getAlternateEmailId());

								if (Util.validateEmailID(inv.getInstitute().getAlternateEmailId()))
									email.setMailIdCC(inv.getInstitute().getAlternateEmailId());

								email.setCreatedBy("--system-generated--");
								email.setFilename(inv.getFilename());

								Map<String, Object> _save_email_resp = invServ.saveInvoiceEmail(email);
								if (_save_email_resp.get("StatusCode") == "00") {
									email = (InvoiceEmail) _save_email_resp.get("InvoiceEmail");

									invServ.sendInvoiceEmail(email);
								}

							});
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("sendScheduledInvoicePaymentReminder ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
