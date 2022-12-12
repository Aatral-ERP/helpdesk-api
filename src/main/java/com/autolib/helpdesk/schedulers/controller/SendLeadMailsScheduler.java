package com.autolib.helpdesk.schedulers.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Config.MailConfig.MailSenderConfigurations;
import com.autolib.helpdesk.LeadManagement.model.Lead;
import com.autolib.helpdesk.LeadManagement.model.LeadContact;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplate;
import com.autolib.helpdesk.LeadManagement.repository.LeadContactRepository;
import com.autolib.helpdesk.LeadManagement.repository.LeadMailTemplateRepository;
import com.autolib.helpdesk.LeadManagement.repository.LeadRepository;
import com.autolib.helpdesk.common.DirectoryUtil;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.schedulers.model.LeadMailSentStatus;
import com.autolib.helpdesk.schedulers.repository.LeadMailSentStatusRepository;

@RestController
@Component
public class SendLeadMailsScheduler {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	LeadMailTemplateRepository mailTempRepo;

	@Autowired
	LeadRepository leadRepo;

	@Autowired
	LeadContactRepository leadContactRepo;

	@Autowired
	LeadMailSentStatusRepository mailStatusRepo;

	@Autowired
	private EmailSender emailSender;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	@Autowired
	private MailSenderConfigurations mailSenderConfig;

	@GetMapping("SendLeadMailsScheduler")
	@Scheduled(cron = "0 30 11 * * *")
	void execute() {

		logger.info("SendLeadMailsSchedulerDaily starts:::");
		Map<String, Object> resp = new HashMap<>();
		List<LeadMailTemplate> templates = new ArrayList<>();

		try {

			Calendar calendar = Calendar.getInstance();

			int day = calendar.get(Calendar.DAY_OF_WEEK);
			int month = calendar.get(Calendar.MONTH) + 1;

			System.out.println("Day:: " + day);
			System.out.println("Month:: " + month + "::" + String.valueOf(month % 2 == 0));

			System.out.println("DayOfWeek:: " + LocalDate.now().getDayOfWeek().name());
			System.out.println("FirstDayOfWeek:: " + calendar.getFirstDayOfWeek());

			List<String> frequencies = new ArrayList<>();

			frequencies.add("Daily");

			if (LocalDate.now().getDayOfWeek().name().equalsIgnoreCase("MONDAY")) {
				frequencies.add("Weekly");
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) == 1 || calendar.get(Calendar.DAY_OF_MONTH) == 15) {
				frequencies.add("Bi-Weekly");
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
				frequencies.add("Monthly");
			}
			if (month % 2 == 0 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
				frequencies.add("Bi-Monthly");
			}
			if (month % 6 == 0 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
				frequencies.add("Bi-Yearly");
			}
			if (month == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
				frequencies.add("Yearly");
			}

			System.out.println("frequencies::" + frequencies);

			templates = mailTempRepo.findByFrequencyInAndEnabled(frequencies, true);

			templates.forEach(template -> System.out.println(template));

			templates.stream().forEach(_template -> {

				List<Lead> leads = leadRepo.findBySendEmailUpdatesAndStatusInAndCategoryInAndIndustryTypeInAndStateIn(
						true, Arrays.asList(_template.getStatus().split(",")),
						Arrays.asList(_template.getCategory().split(",")),
						Arrays.asList(_template.getIndustryType().split(",")),
						Arrays.asList(_template.getState().split(",")));

				List<LeadContact> leadContacts = leadContactRepo.findByLeadIdIn(leads.stream().mapToInt(Lead::getId)
						.collect(ArrayList::new, ArrayList::add, ArrayList::addAll));

				leads.forEach(lead -> System.out.println(lead));

				leads.stream()
						// Check if any valid and active lead contacts
						.filter(_lead -> getActiveAndValidLeadContacts(_lead, leadContacts)
								.anyMatch(_lc -> _lc.getLeadId() == _lead.getId()))
						// Check if lead is active & SendEmailUpdates is enabled
						.filter(_lead -> _lead.isActiveLead() && _lead.isSendEmailUpdates()).forEach(_lead -> {
							sendMail(_lead,
									getActiveAndValidLeadContacts(_lead, leadContacts).collect(Collectors.toList()),
									_template);
						});
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("SendLeadMailsScheduler ends:::" + resp);
	}

	private Stream<LeadContact> getActiveAndValidLeadContacts(Lead lead, List<LeadContact> leadContacts) {
		return leadContacts.stream().filter(_lc -> _lc.getLeadId() == lead.getId()).filter(_lc -> _lc.isActive())
				.filter(_lc -> Util.validateEmailID(_lc.getEmailId()) || Util.validateEmailID(_lc.getAlternateEmail()));
	}

	public LeadMailSentStatus sendMail(Lead _lead, List<LeadContact> contacts, LeadMailTemplate template) {
		LeadMailSentStatus mailSent = new LeadMailSentStatus();
		try {

			EmailModel emailModel = new EmailModel(getLeadSenderConfig(_lead));
			List<String> emailIds = new ArrayList<>();

			contacts.forEach(contact -> {
				if (Util.validateEmailID(contact.getEmailId()))
					emailIds.add(contact.getEmailId());
				if (Util.validateEmailID(contact.getAlternateEmail()))
					emailIds.add(contact.getAlternateEmail());
			});

			emailModel.setMailTo(emailIds.get(0));
			emailModel.setMailList(emailIds.stream().toArray(String[]::new));
			emailModel.setMailSub(template.getSubject());
			emailModel.setMailText(template.getMessage());

			File directory = null;
			List<String> attachs = new ArrayList<>();
			if (template.getFiles() != null && !template.getFiles().isEmpty()) {
				for (String fileName : Arrays.asList(template.getFiles().split(";"))) {
					directory = new File(
							contentPath + DirectoryUtil.leadMailTemplateDirectory + template.getId() + "/" + fileName);
					attachs.add(directory.getAbsolutePath());
				}
			}

			if (_lead.getFiles() != null && !_lead.getFiles().isEmpty()) {
				for (String fileName : Arrays.asList(_lead.getFiles().split(";"))) {
					directory = new File(
							contentPath + DirectoryUtil.leadRootDirectory + _lead.getId() + "/" + fileName);
					attachs.add(directory.getAbsolutePath());
				}
			}
			emailModel.setContentPaths(attachs);

			int i = emailSender.sendmail(emailModel);

			System.out.println("iiiiiiiiiii  " + i);

			if (i == 1) {
				mailSent.setLeadId(_lead.getId());
				mailSent.setTemplateId(template.getId());
				mailSent.setMailTo(emailModel.getMailTo());
				mailSent.setMailCC(Arrays.stream(emailModel.getMailList()).collect(Collectors.joining(";")));
				mailSent.setSubject(emailModel.getMailSub());
				mailSent.setMessage(emailModel.getMailText());
				mailSent.setFiles(attachs.stream().map(path -> path.substring(path.lastIndexOf("\\") + 1))
						.collect(Collectors.joining(";")));
				mailSent.setStatus("Sent");
			} else if (i == 2) {
				mailSent.setLeadId(_lead.getId());
				mailSent.setTemplateId(template.getId());
				mailSent.setMailTo(emailModel.getMailTo());
				mailSent.setMailCC(Arrays.stream(emailModel.getMailList()).collect(Collectors.joining(";")));
				mailSent.setSubject(emailModel.getMailSub());
				mailSent.setMessage(emailModel.getMailText());
				mailSent.setFiles(attachs.stream().map(path -> path.substring(path.lastIndexOf("\\") + 1))
						.collect(Collectors.joining(";")));
				mailSent.setStatus("JavaMailSender/MailProperties not Found");
			} else if (i == 0) {
				mailSent.setLeadId(_lead.getId());
				mailSent.setTemplateId(template.getId());
				mailSent.setMailTo(emailModel.getMailTo());
				mailSent.setMailCC(Arrays.stream(emailModel.getMailList()).collect(Collectors.joining(";")));
				mailSent.setSubject(emailModel.getMailSub());
				mailSent.setMessage(emailModel.getMailText());
				mailSent.setFiles(attachs.stream().map(path -> path.substring(path.lastIndexOf("\\") + 1))
						.collect(Collectors.joining(";")));
				mailSent.setStatus("Exception Occured");
			}

			mailStatusRepo.save(mailSent);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailSent;
	}

	private String getLeadSenderConfig(Lead _lead) {
		List<String> temp = Arrays.asList(_lead.getMailSetting().split(" "));
		int id = Integer.parseInt(temp.get(0).replace("#", "").trim());

		return mailSenderConfig.getLeadProperties("Leads").stream().filter(prop -> prop.getId() == id).findFirst().get()
				.getConfigName();
	}

}
