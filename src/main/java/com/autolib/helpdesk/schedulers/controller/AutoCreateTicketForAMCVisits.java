package com.autolib.helpdesk.schedulers.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisit;
import com.autolib.helpdesk.Sales.repository.AMCVisitRepository;
import com.autolib.helpdesk.Sales.repository.DealsRepository;
import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.service.TicketService;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;
import com.autolib.helpdesk.common.EnumUtils.TicketPriority;
import com.autolib.helpdesk.common.EnumUtils.TicketStatus;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("auto-create-ticket-amc-visit")
public class AutoCreateTicketForAMCVisits {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	AMCVisitRepository amcVisitRepo;

	@Autowired
	DealsRepository dealsRepo;

	@Autowired
	TicketService ticketService;

	@GetMapping("SendReminderNotification")
	@Scheduled(cron = "0 12,18,23 * * * *")
	void execute() {

		logger.info("SendReminderNotification starts:::");

		List<AMCVisit> visits = amcVisitRepo.findByVisitDate(new Date());

		List<Deal> deals = dealsRepo.findAllById(visits.stream().filter(visit -> visit.getTicketId() == 0)
				.map(AMCVisit::getDealId).distinct().collect(Collectors.toList()));

		System.out.println(visits);

		visits.stream().filter(visit -> visit.getTicketId() == 0).forEach(visit -> {
			
			Deal deal = deals.stream().filter(_deal -> _deal.getId() == visit.getDealId()).findFirst().get();
			Ticket ticket = new Ticket();
			ticket.setInstitute(deal.getInstitute());
			ticket.setInstituteId(deal.getInstitute().getInstituteId());
			ticket.setSubject(visit.getSubject());
			ticket.setServiceType("Others");
			ticket.setServiceUnder(ServiceUnder.AMC);
			ticket.setPriority(TicketPriority.High);
			ticket.setStatus(TicketStatus.Raised);
			ticket.setDueDateTime(visit.getVisitDate());

			ticket.setCreatedBy("system.generated@domain.com");
			ticket.setLastUpdatedBy("system.generated@domain.com");
			ticket.setEmailId("system.generated@domain.com");

			if (visit.getAgentEmailId() != null && visit.getAgentEmailId().isEmpty()) {
				ticket.setAssignedTo(visit.getAgentEmailId());
				ticket.setAssignedBy("system.generated@domain.com");
				ticket.setAssignedDateTime(new Date());
				ticket.setStatus(TicketStatus.Assigned);
			}

			Map<String, Object> resp = ticketService.saveTicket(ticket);

			if (resp.get("StatusCode").equals("00")) {
				ticket = (Ticket) resp.get("Ticket");
				visit.setTicketId(ticket.getTicketId());
				amcVisitRepo.save(visit);
			}

		});

	}

}
