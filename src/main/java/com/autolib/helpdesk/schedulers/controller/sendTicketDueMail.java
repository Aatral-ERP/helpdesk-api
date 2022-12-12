package com.autolib.helpdesk.schedulers.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.repository.TicketRepository;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.Util;

@RequestMapping("scheduler")
@Controller
@RestController
@CrossOrigin("*")
public class sendTicketDueMail {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	TicketRepository ticketRepo;

	@Value("${al.ticket.view.rooturi}")
	private String viewTicketRootURI;

	@Autowired
	EmailSender emailSender;

	@GetMapping("sendTicketDueMail")
	@Scheduled(cron = "0 0 0/1 1/1 * ?")
	void execute() {
		logger.info("sendTicketDueMail stars:::");

		try {

			List<Ticket> tickets = ticketRepo.findDueTicketsIn1Hour();

			System.out.println(tickets);

			tickets.forEach(ticket -> {

				EmailModel emailModel = new EmailModel("Tickets");

				String mailList = ticket.getEmailUpdates();
				mailList = ticket.getCreatedBy() + ";";

				String[] emailUpdates = mailList.split(";");

				System.out.println(mailList.toString() + "  " + emailUpdates.length);

				emailModel.setMailTo(ticket.getAssignedTo());
				emailModel.setMailList(emailUpdates);
				emailModel.setOtp(String.valueOf(Util.generateOTP()));
				emailModel.setMailSub("Ticket ID : #" + ticket.getTicketId() + ", About to reach deadline in an hour.");

				StringBuffer sb = new StringBuffer();

				sb.append("Hi, <br><br>");
				sb.append("<p> Following ticket has the deadline in an hour, close before the deadline. - "
						+ (Util.sdfFormatter(ticket.getDueDateTime(), "dd/MM/yyyy hh:mm a")) + "</p><br>");

				sb.append("<hr>");

				sb.append("<table><tr><td>Ticket Id #:</td> <td>:</td> <td>" + ticket.getTicketId() + "</td></tr>");

				sb.append("<tr><td>Institute Name</td><td>:</td><td>" + ticket.getInstitute().getInstituteName()
						+ "</td></tr>");
				sb.append("<tr><td>Product</td><td>:</td><td>" + ticket.getProduct() + "</td></tr>");
				sb.append("<tr><td>Status</td><td>:</td><td>" + ticket.getStatus() + "</td></tr>");

				sb.append("<tr><td>View Ticket</td><td>:</td><td> <a target='_blank' href='" + viewTicketRootURI
						+ ticket.getTicketId() + "'> View Ticket </a>" + "</td></tr>");

				sb.append("</table>");

				emailModel.setMailText(sb.toString());

				emailSender.sendmail(emailModel);

			});

		} catch (Exception e) {

		}

	}

	@GetMapping("sendTicketDueMailAfterExpire")
	@Scheduled(cron = "0 0 0/1 1/1 * ?")
	void execute2() {
		logger.info("sendTicketDueMailAfterExpire stars:::");

		try {

			List<Ticket> tickets = ticketRepo.findExperiedDueTicketsAfter1Hour();

			System.out.println(tickets);

			tickets.forEach(ticket -> {

				EmailModel emailModel = new EmailModel("Tickets");

				String mailList = ticket.getEmailUpdates();
				mailList = ticket.getCreatedBy() + ";";

				String[] emailUpdates = mailList.split(";");

				System.out.println(mailList.toString() + "  " + emailUpdates.length);

				emailModel.setMailTo(ticket.getAssignedTo());
				emailModel.setMailList(emailUpdates);
				emailModel.setOtp(String.valueOf(Util.generateOTP()));
				emailModel.setMailSub("Ticket ID : #" + ticket.getTicketId() + ", reached deadline an hour ago.");

				StringBuffer sb = new StringBuffer();

				sb.append("Hi, <br><br>");
				sb.append("<p> Following ticket reached deadline an hour ago. - "
						+ (Util.sdfFormatter(ticket.getDueDateTime(), "dd/MM/yyyy hh:mm a")) + "</p><br>");

				sb.append("<hr>");

				sb.append("<table><tr><td>Ticket Id #:</td> <td>:</td> <td>" + ticket.getTicketId() + "</td></tr>");

				sb.append("<tr><td>Institute Name</td><td>:</td><td>" + ticket.getInstitute().getInstituteName()
						+ "</td></tr>");
				sb.append("<tr><td>Product</td><td>:</td><td>" + ticket.getProduct() + "</td></tr>");
				sb.append("<tr><td>Status</td><td>:</td><td>" + ticket.getStatus() + "</td></tr>");

				sb.append("<tr><td>View Ticket</td><td>:</td><td> <a target='_blank' href='" + viewTicketRootURI
						+ ticket.getTicketId() + "'> View Ticket </a>" + "</td></tr>");

				sb.append("</table>");

				emailModel.setMailText(sb.toString());

				emailSender.sendmail(emailModel);

			});

		} catch (Exception e) {

		}

	}

}
