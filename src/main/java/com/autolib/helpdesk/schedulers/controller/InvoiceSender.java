package com.autolib.helpdesk.schedulers.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.model.InstituteProducts;
import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteProductRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealEmail;
import com.autolib.helpdesk.Sales.model.DealProducts;
import com.autolib.helpdesk.Sales.model.DealProformaInvoice;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.repository.DealEmailRepository;
import com.autolib.helpdesk.Sales.service.DealService;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.schedulers.model.LogsSchedulerInvoice;
import com.autolib.helpdesk.schedulers.repository.LogsSchedulerInvoiceRepository;

@Service
public class InvoiceSender {

	@Autowired
	InstituteProductRepository ipRepo;

	@Autowired
	InstituteRepository instRepo;

	@Autowired
	DealEmailRepository dealEmailRepo;

	@Autowired
	InstituteContactRepository icRepo;

	@Autowired
	AgentRepository agentRepo;

	@Autowired
	ProductsRepository prodRepo;

	@Autowired
	LogsSchedulerInvoiceRepository ISILogRepo;

	@Autowired
	InfoDetailsRepository infoDetailRepo;

	@Autowired
	DealService dealService;

	@Autowired
	EmailSender emailSender;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	@SuppressWarnings("unchecked")
	void sendScheduledInvoiceReminder() {

		InfoDetails info = infoDetailRepo.findById(1);

		String datediffin = "";

		if (info.isDaysAfter30())
			datediffin = datediffin + ", -30 ";

		if (info.isDaysAfter15())
			datediffin = datediffin + ", -15 ";

		if (info.isDaysAfter7())
			datediffin = datediffin + ", -7 ";

		if (info.isDaysAfter1())
			datediffin = datediffin + ", -1 ";

		if (info.isDaysBefore0())
			datediffin = datediffin + ", 0 ";

		if (info.isDaysBefore1())
			datediffin = datediffin + ", -1 ";

		if (info.isDaysBefore7())
			datediffin = datediffin + ", 7 ";

		if (info.isDaysBefore15())
			datediffin = datediffin + ", 15 ";

		if (info.isDaysBefore30())
			datediffin = datediffin + ", 30 ";

		if (datediffin.length() > 0) {

			datediffin = datediffin.replaceFirst(",", "");

			if (info.isSendEmail()) {

				List<Institute> institutes = new ArrayList<>();

				Query query = entityManager.createQuery(
						"select distinct ip.institute from InstituteProducts ip "
								+ "where datediff(ip.amcExpiryDate , curdate()) in (" + datediffin + ") ",
						Institute.class);

				institutes = query.getResultList();
				sendInvoice(institutes);

			}
		}
	}

	public Map<String, Object> sendAMCReminder(List<InstituteProducts> ips) {
		Map<String, Object> resp = new HashMap<>();
		try {
			Set<String> instituteIds = new HashSet<>();
			ips.stream().forEach(ip -> instituteIds.add(ip.getInstitute().getInstituteId()));

			List<Institute> institutes = new ArrayList<>();
			institutes = instRepo.findByInstituteIdIn(instituteIds);

			System.out.println(institutes.size());
			if (institutes.size() > 0) {
				sendInvoice(institutes);
			}
			resp.put("NoOfInstitutes", institutes.size());
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	private void sendInvoice(List<Institute> institutes) {

		try {

			InfoDetails info = infoDetailRepo.findById(1);

			List<Product> products = prodRepo.findAll();
			Agent agent = agentRepo.findByEmailId(info.getSignatureAgent());

			System.out.println("Sending AMC Reminders :::: " + institutes.size());
			LogsSchedulerInvoice invoiceSchedulelog = new LogsSchedulerInvoice();

			for (Institute inst : institutes) {

				try {

					invoiceSchedulelog.setInstitute(inst);

					List<String> mailIds = new ArrayList<>();
					if (Util.validateEmailID(inst.getEmailId())) {
						mailIds.add(inst.getEmailId());
					}

					List<InstituteContact> instContacts = icRepo.findByInstituteId(inst.getInstituteId());
					instContacts.stream().forEach(ic -> {
						if (Util.validateEmailID(inst.getEmailId())) {
							mailIds.add(ic.getEmailId());
						}
					});

					if (Util.validateEmailID(info.getReminderEmailCC()))
						mailIds.add(info.getReminderEmailCC());

					if (mailIds.size() > 0) {

						List<InstituteProducts> ipps = ipRepo.findByInstituteAndCurrentServiceUnderNotIn(inst,
								Arrays.asList(ServiceUnder.valueOf("NotInAnyService")));

						DealRequest dealReq = prepareDealRequest(inst, ipps, products);

						Map<String, Object> saveDealResp = dealService.saveDeal(dealReq);

						if (String.valueOf(saveDealResp.get("StatusCode")) == "00") {

							invoiceSchedulelog.setCreateDeal(true);
							Deal deal = (Deal) saveDealResp.get("Deal");

							invoiceSchedulelog.setDealId(deal.getId());

							DealProformaInvoice proInv = prepareDealProformaInvoice(deal);

							Map<String, Object> proInvResp = dealService.saveDealProformaInvoice(proInv);

							if (String.valueOf(proInvResp.get("StatusCode")) == "00") {
								invoiceSchedulelog.setCreateProformaInvoice(true);

								proInv = (DealProformaInvoice) proInvResp.get("DealProformaInvoice");

								DealRequest proInvGenDealReq = prepareProformaInvoiceGenerationDealReq(proInv, agent);
								Map<String, Object> proInvGenDealResp = dealService
										.generateProformaInvoicePDF(proInvGenDealReq);

								if (String.valueOf(proInvGenDealResp.get("StatusCode")) == "00") {
									invoiceSchedulelog.setGenerateProformaInvoice(true);

									DealEmail dealEmail = prepareDealEmail(mailIds,
											(DealProformaInvoice) proInvGenDealResp.get("DealProformaInvoice"), info);

									invoiceSchedulelog.setInvoiceURL(dealEmail.getFilename());

									Map<String, Object> dealEmailResp = dealService.saveDealEmail(dealEmail);

									if (String.valueOf(dealEmailResp.get("StatusCode")) == "00") {
										invoiceSchedulelog.setCreateDealEmail(true);
										dealEmail = (DealEmail) dealEmailResp.get("DealEmail");

										invoiceSchedulelog.setMailTo(dealEmail.getMailIds());
										invoiceSchedulelog.setMailList(dealEmail.getMailIdCC());
										invoiceSchedulelog.setMailSubject(dealEmail.getSubject());

										Map<String, Object> sendDealEmailResp = dealService.sendDealEmail(dealEmail);
										if (String.valueOf(sendDealEmailResp.get("StatusCode")) == "00") {
											invoiceSchedulelog.setSendEmail(true);

											ipps.forEach(ip -> {
												ip.setLastAMCReminderDealId(String.valueOf(deal.getId()));
												ip.setLastAMCReminderSentDateTime(new Date());
											});

											ipRepo.saveAll(ipps);

										}

									}
								}
							}
						}
					}
				} catch (Exception e) {
					invoiceSchedulelog.setException(e.getMessage());
				}
				System.out.println(invoiceSchedulelog.toString());
				// Logging the Invoice Sending
				ISILogRepo.save(invoiceSchedulelog);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	DealEmail prepareDealEmail(List<String> mailIds, DealProformaInvoice proInv, InfoDetails info) {

		DealEmail email = new DealEmail();

		email.setCreatedBy("--system-generated--");

		email.setMailIds(mailIds.get(0));

		String mailIdCC = "";
		for (String mailId : mailIds) {
			mailIdCC = mailIdCC + mailId + ";";
		}
		email.setMailIdCC(mailIdCC);

		email.setSubject("AMC Reminder - " + info.getCmpName());
		email.setDealId(proInv.getDealId());
		email.setFilename(proInv.getFilename());
		email.setTab("Proforma Invoice");
		email.setMessage("Dear Sir/Mam,<br><br>			Please see the enclosed AMC Proforma Invoice. <br><br>"
				+ "			Kindly pay before the AMC Expires for uninterrupted services.<br>"
				+ "			Kindly Ignore this mail if already paid.  <br><br><br>"
				+ "<small>*** This is computer generated Invoice ***</small>");

		System.out.println(email);

		return email;

	}

	DealRequest prepareProformaInvoiceGenerationDealReq(DealProformaInvoice proInv, Agent agent) {

		DealRequest proInvGenDealReq = new DealRequest();
		proInvGenDealReq.setAddFullSeal(true);
		proInvGenDealReq.setAddRoundSeal(true);
		proInvGenDealReq.setAddSign(true);
		proInvGenDealReq.setDesignation(agent.getDesignation());
		proInvGenDealReq.setSignatureBy(agent.getEmailId());

		proInvGenDealReq.setTemplateName("Proforma_Invoice_Template_1");

		proInvGenDealReq.setDealProformaInvoice(proInv);

		return proInvGenDealReq;

	}

	DealProformaInvoice prepareDealProformaInvoice(Deal deal) {
		DealProformaInvoice proInv = new DealProformaInvoice();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 30);

		proInv.setCreatedBy("--system-generated--");
		proInv.setModifiedBy("--system-generated--");
		proInv.setDealId(deal.getId());
		proInv.setExciseDuty(0.00);
		proInv.setDueDate(c.getTime());
		proInv.setInvoiceDate(new Date());
		proInv.setInvoiceStatus("Proforma Invoice");
		proInv.setPaidAmount(0.00);
		String proformaInvoiceNo = sdf.format(new Date()) + "/" + deal.getInstitute().getInstituteId()
				+ String.valueOf(Util.generateOTP());
		proInv.setProformaInvoiceNo(proformaInvoiceNo);
		proInv.setSalesCommission(0.00);
		proInv.setShippingCost(0.00);
		proInv.setSubject("AMC Reminder - Regarding");
		proInv.setTerms("*** This is a computer generated invoice ***");

		return proInv;
	}

	DealRequest prepareDealRequest(Institute inst, List<InstituteProducts> ipps, List<Product> products) {

		DealRequest dealReq = new DealRequest();
		Deal deal = new Deal();

		deal.setInstitute(inst);
		deal.setDealType("AMC");

		deal.setBillingStreet1(String.valueOf(inst.getStreet1()));
		deal.setBillingStreet2(String.valueOf(inst.getStreet1()));
		deal.setBillingCity(String.valueOf(inst.getCity()));
		deal.setBillingState(String.valueOf(inst.getState()));
		deal.setBillingCountry(String.valueOf(inst.getCountry()));
		deal.setBillingZIPCode(String.valueOf(inst.getZipcode()));

		deal.setShippingStreet1(String.valueOf(inst.getStreet1()));
		deal.setShippingStreet2(String.valueOf(inst.getStreet1()));
		deal.setShippingCity(String.valueOf(inst.getCity()));
		deal.setShippingState(String.valueOf(inst.getState()));
		deal.setShippingCountry(String.valueOf(inst.getCountry()));
		deal.setShippingZIPCode(String.valueOf(inst.getZipcode()));

		deal.setNoOfProducts(ipps.size());
		deal.setGstType("CGST/SGST");
		deal.setCreatedBy("--system-generated--");
		deal.setModifiedBy("--system-generated--");

		Double subTotal = 0.00;
		Double tax = 0.00;
		Double grandTotal = 0.00;

		List<DealProducts> dealProducts = new ArrayList<>();

		for (InstituteProducts ip : ipps) {
			Product prod = new Product();
			for (Product _p : products) {
				if (ip.getProduct().getId() == _p.getId()) {
					prod = _p;
				}
			}

			DealProducts dp = new DealProducts();
			dp.setName(ip.getProduct().getName());
			dp.setDescription("HSN Code : " + prod.getHsn() + "\n" + prod.getAmcDescription());
			dp.setPrice(ip.getAmcAmount());
			dp.setProductId(ip.getProduct().getId());
			dp.setGstPercentage(prod.getGst());
			dp.setQuantity(1);
			dealProducts.add(dp);

			Double _tax = 0.00;
			if (prod.getGst() > 0.00) {
				_tax = (dp.getPrice() * ((double) prod.getGst() / 100));
			}

			subTotal = subTotal + dp.getPrice();
			tax = tax + _tax;

			System.out.println(dp.getPrice() + " :::: " + prod.getGst() + " :::: " + _tax);

		}

		grandTotal = subTotal + tax;

		deal.setSubTotal(subTotal);
		deal.setTax(tax);
		deal.setGrandTotal(grandTotal);

		dealReq.setDeal(deal);
		dealReq.setDealProducts(dealProducts);
		dealReq.setInstituteContacts(new ArrayList<>());

		return dealReq;
	}

	public Map<String, Object> resendAMCReminderMail(LogsSchedulerInvoice log) {
		Map<String, Object> resp = new HashMap<>();

		try {
			DealEmail dealEmail = dealEmailRepo.findOneByDealIdAndCreatedByOrderByCreateddatetimeDesc(log.getDealId(),
					"--system-generated--");
			System.out.println(dealEmail);
			if (dealEmail != null)
				resp = dealService.sendDealEmail(dealEmail);
			else
				resp.putAll(Util.invalidMessage("No Email Found"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}

//	void createInvoice2(InstituteProducts ipp) {
//		
//		try {
//			ipp.
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	void createInvoice(InstituteProducts ipp) {
//
//		try {
//
//			final InputStream stream = this.getClass().getResourceAsStream("/reports/Amc_Quote.jrxml");
//
//			final JasperReport report = JasperCompileManager.compileReport(stream);
//
////			final JasperReport report = (JasperReport) JRLoader.loadObject(stream);
//
//			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(new ArrayList<>());
//
//			final Map<String, Object> parameters = new HashMap<>();
//			parameters.put("amc_amount", "Rs." + ipp.getAmcAmount());
//			parameters.put("institute_name", ipp.getInstitute().getInstituteName());
//			parameters.put("address", ipp.getInstitute().getCity() + ", " + ipp.getInstitute().getState() + " - "
//					+ ipp.getInstitute().getZipcode());
//			parameters.put("gst", "18%");
//
//			Double totalAmount = ipp.getAmcAmount() + (ipp.getAmcAmount() * (18 / 100));
//			parameters.put("total_amount", "Rs." + totalAmount);
//			parameters.put("gst_amount", "Rs." + String.valueOf(totalAmount - ipp.getAmcAmount()));
//			parameters.put("service_type", ipp.getProduct().getName()
//					+ " - Annual Maintanence Charges - Online Support with UPDATES for 1 year");
//			parameters.put("title", ipp.getProduct().getName()
//					+ " - Annual Maintanence Charges - Online Support with UPDATES for 1 year");
//			parameters.put("invoice_no", "");
//
//			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
//
//			File directory = new File(contentPath + "_amc_invoices" + "/");
//			System.out.println(directory.getAbsolutePath());
//			if (!directory.exists()) {
//				System.out.println("Directory created ::" + directory.getAbsolutePath());
//				directory.mkdir();
//			}
//			final String filePath = directory.getAbsolutePath() + "/" + ipp.getId() + ".pdf";
//			System.out.println(filePath);
//			// Export the report to a PDF file.
//			JasperExportManager.exportReportToPdfFile(print, filePath);
//
////			sendInvoiceMail(ipp);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	void sendInvoiceMail(InstituteProducts ipp) {
//		try {
//			EmailModel emailModel = new EmailModel();
//
//			String[] emailUpdates = ipp.getInstitute().getAlternateEmailId().split(";");
//
//			emailModel.setMailTo(ipp.getInstitute().getEmailId());
//			emailModel.setMailList(emailUpdates);
//			emailModel.setMailSub("AMC Quote : " + ipp.getProduct().getName());
//
//			StringBuffer sb = new StringBuffer();
//			sb.append("Dear Sir/Mam, <br><br>");
//			sb.append("Please find the attached AMC Quote.: <br><br>");
//
//			sb.append("<br><b>Amc Quote Details:</b>");
//			sb.append(
//					"<table>Institute Name</td><td>:</td><td>" + ipp.getInstitute().getInstituteName() + "</td></tr>");
//			sb.append("<tr><td>Product</td><td>:</td><td>" + ipp.getProduct() + "</td></tr>");
//			sb.append("<tr><td>Current Service Under</td><td>:</td><td>" + ipp.getInstitute().getServiceUnder()
//					+ "</td></tr>");
//			sb.append("<tr><td>AMC Amount</td> <td>:</td> <td>Rs." + ipp.getAmcAmount() + "</td></tr>");
//			sb.append("<tr><td>GST(%)</td> <td>:</td> <td> 18% </td></tr>");
//			Double totalAmount = ipp.getAmcAmount() + (ipp.getAmcAmount() * (18 / 100));
//			sb.append("<tr><td>Total Payable Amount</td> <td>:</td> <td>Rs." + totalAmount + "</td></tr>");
//			sb.append("</table>");
//
//			emailModel.setMailText(sb.toString());
//
//			File directory = new File(contentPath + "_amc_invoices" + "/" + ipp.getId() + ".pdf");
//
//			emailModel.setContent_path(directory.getAbsolutePath());
//
//			emailSender.sendmail(emailModel);
//
//			ISILogRepo.save(new LogsSchedulerInvoice(emailModel));// Logging the Invoice Sending
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

}
