package com.autolib.helpdesk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;
import com.autolib.helpdesk.Sales.repository.Invoice.InvoiceEmailReminderSettingsRepository;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class HelpdeskApiApplication implements CommandLineRunner {

	@Autowired
	JavaMailSender jms;

	@Autowired
	InvoiceEmailReminderSettingsRepository reminderSettingRepo;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApiApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.autolib.helpdesk")).paths(PathSelectors.any()).build();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(jms);

		persistDefaultInvoiceEmailReminderSettings();

	}

	void persistDefaultInvoiceEmailReminderSettings() {

		InvoiceEmailReminderSettings setting = null;
		setting = reminderSettingRepo.findById(1);

		if (setting == null) {

			setting = new InvoiceEmailReminderSettings();

			setting.setId(1);
			setting.setSendReminderEmail(true);
			setting.setReminderSubjectTemplate("Payment for Invoice #${invoiceNo} is pending.");

			String mailContent = "Hi,\n\n";

			mailContent = mailContent + "This is just a reminder that payment on invoice #${invoiceNo} "
					+ "(total ${grandTotal}), which we sent on ${invoiceDate} is on due . \n"
					+ "You can make payment to the bank account specified on the invoice.\n"
					+ "If you have any questions please let us know.";

			mailContent = mailContent + "Please see the attached Invoice \n\nInvoice No : #${invoiceNo}\n"
					+ "Payable Amount : ${grandTotal}";

			mailContent = mailContent + "\n\n${paymentLink}";

			mailContent = mailContent + "\n\nThanks\n${cmpName} \n${cmpAddress} \n${cmpPhone}/${cmpLandLine}"
					+ "\n${cmpEmail}\n${cmpWebsiteUrl}";

			setting.setReminderContentTemplate(mailContent);

			setting.setDaysBefore0(true);
			setting.setDaysBefore1(true);
			setting.setDaysBefore7(true);
			setting.setDaysBefore15(true);

			setting.setDaysAfter1(true);
			setting.setDaysAfter7(true);
			setting.setDaysAfter15(true);
			setting.setDaysAfter30(true);

			reminderSettingRepo.save(setting);

		}
	}

}
