package com.autolib.helpdesk.Tickets.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Tickets.model.CallReport;
import com.autolib.helpdesk.Tickets.model.CallReportRequest;
import com.autolib.helpdesk.Tickets.model.GmailAsTicketRequest;
import com.autolib.helpdesk.Tickets.model.ServiceReportRequest;
import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.model.TicketRating;
import com.autolib.helpdesk.Tickets.model.TicketReply;
import com.autolib.helpdesk.Tickets.model.TicketReportRequest;
import com.autolib.helpdesk.Tickets.model.TicketRequest;
import com.autolib.helpdesk.Tickets.model.TicketServiceInvoice;
import com.autolib.helpdesk.Tickets.service.TicketService;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@RequestMapping("ticket")
@RestController
@CrossOrigin("*")
public class TicketController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	TicketService ticketService;
	@Autowired
	ProductsRepository productRepo;
	@Autowired
	JwtTokenUtil jwtUtil;

	@Value("${al.ticket.self.instituteId}")
	String selfTicketInstituteId;

	@PostMapping("add-simple-ticket")
	public ResponseEntity<?> saveSimpleTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody Ticket ticket) {
		logger.info("saveSimpleTicket starts:::" + ticket);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			System.out.println(ticket);

			if (ticket.getInstitute() == null || ticket.getInstituteId().isEmpty()) {
				System.out.println("selfTicketInstituteId:::" + selfTicketInstituteId);
				ticket.setInstitute(new Institute(selfTicketInstituteId));
				ticket.setInstituteId(selfTicketInstituteId);
			}

			resp = ticketService.saveTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("saveSimpleTicket ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-ticket")
	public ResponseEntity<?> saveTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody Ticket ticket) {
		logger.info("saveTicket starts:::" + ticket);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			System.out.println(ticket);
			resp = ticketService.saveTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("saveTicket ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-ticket")
	public ResponseEntity<?> getTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody Ticket ticket) {
		logger.info("getTicket starts:::" + ticket);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			System.out.println(ticket);
			resp = ticketService.getTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getTicket ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-ticket")
	public ResponseEntity<?> deleteTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody Ticket ticket) {
		logger.info("deleteTicket starts:::" + ticket);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = ticketService.deleteTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteTicket ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-ticket-reply")
	public ResponseEntity<?> addTicketReply(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketReply ticketReply) {
		logger.info("deleteTicket starts:::" + ticketReply);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = ticketService.addTicketReply(ticketReply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteTicket ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-ticket-reply")
	public ResponseEntity<?> getAllTicketReply(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketReply ticketReply) {
		logger.info("deleteTicket starts:::" + ticketReply);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = ticketService.getAllTicketReply(ticketReply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteTicket ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-ticket-attachment")
	public ResponseEntity<?> addAttachment(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("ticketId") String ticketId) throws Exception {
		logger.info("addAttachment starts:::" + ticketId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.addAttachment(ticketId, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("addAttachment ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-institute-tickets")
	public ResponseEntity<?> addAllInstituteTickets(@RequestHeader(value = "Authorization") String token,
			@RequestBody Ticket ticket) throws Exception {
		logger.info("addAllInstituteTickets starts:::" + ticket);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.addAllInstituteTickets(ticket);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("addAllInstituteTickets ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("update-ticket")
	public ResponseEntity<?> updateTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketRequest TicketRequest) throws Exception {
		logger.info("updateTicket starts:::" + TicketRequest);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.updateTicket(TicketRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("updateTicket ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("assign-ticket")
	public ResponseEntity<?> assignTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketRequest TicketRequest) throws Exception {
		logger.info("assignTicket starts:::" + TicketRequest);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.assignTicket(TicketRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("assignTicket ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-ticket-report-data")
	public ResponseEntity<?> getTicketReportData(@RequestHeader(value = "Authorization") String token)
			throws Exception {
		logger.info("getTicketReportData starts:::");
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.getTicketReportData();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getTicketReportData ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-ticket-report")
	public ResponseEntity<?> getTicketReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketReportRequest ticketRequest) throws Exception {
		logger.info("getTicketReport starts:::" + ticketRequest);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.getTicketReport(ticketRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getTicketReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("send-ticket-invoice")
	public ResponseEntity<?> sendTicketInvoice(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketServiceInvoice tsi) throws Exception {
		logger.info("sendTicketInvoice starts:::" + tsi);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.sendTicketInvoice(tsi);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("sendTicketInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-ticket-invoice")
	public ResponseEntity<?> getTicketInvoice(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketServiceInvoice tsi) throws Exception {
		logger.info("getTicketInvoice starts:::" + tsi);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.getTicketInvoice(tsi);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getTicketInvoice ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-ticket-rating")
	public ResponseEntity<?> addTicketRating(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketRating tr) throws Exception {
		logger.info("addTicketRating starts:::" + tr);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.addTicketRating(tr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("addTicketRating ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-gmail-as-ticket")
	public ResponseEntity<?> saveGmailAsTicket(@RequestHeader(value = "Authorization") String token,
			@RequestBody GmailAsTicketRequest gmailTicket) throws Exception {
		logger.info("saveGmailAsTicket starts:::" + gmailTicket);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			resp = ticketService.saveGmailAsTicket(gmailTicket);

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		logger.info("saveGmailAsTicket ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-call-report")
	public ResponseEntity<?> saveCallReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody CallReport callrepo) throws Exception {
		logger.info("saveCallReport starts:::" + callrepo);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			resp = ticketService.saveCallReport(callrepo);

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		logger.info("saveCallReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-call-report/{instituteId}")
	public ResponseEntity<?> getCallReport(@RequestHeader(value = "Authorization") String token,
			@PathVariable("instituteId") String instituteId) throws Exception {
		logger.info("getCallReport starts:::" + instituteId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			resp = ticketService.getCallReport(instituteId);

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		logger.info("getCallReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-call-report-pdf")
	public ResponseEntity<?> getCallReportPdfTemplate(@RequestHeader(value = "Authorization") String token,
			@RequestBody CallReportRequest callRepoReq) {

		logger.info("getCallReportPdfTemplate starts:::" + callRepoReq);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = ticketService.getCallReportPdfTemplate(callRepoReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getCallReportPdfTemplate ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-call-report-attachment")
	public ResponseEntity<?> saveCallReportAttachment(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file, @RequestParam("id") int id) throws Exception {

		logger.info("saveCallReportAttachment starts:::" + id);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			resp = ticketService.saveCallReportAttachment(file, id);

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		logger.info("saveCallReportAttachment ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-call-report")
	public ResponseEntity<?> deleteCallReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody CallReport callreport) {
		System.out.println("deleteCallReport req starts::" + callreport);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = ticketService.deleteCallReport(callreport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-ticket-dashboard-details")
	public ResponseEntity<?> getTicketDashboardDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketReportRequest ticketRequest) throws Exception {
		logger.info("getTicketDashboardDetails starts:::" + ticketRequest);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.getTicketDashboardDetails(ticketRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getTicketDashboardDetails ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-agent-performance-details")
	public ResponseEntity<?> getAgentPerformanceDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody TicketRequest request) throws Exception {
		logger.info("getAgentPerformanceDetails starts:::" + request);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.getAgentPerformanceDetails(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getAgentPerformanceDetails ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("service-report")
	public ResponseEntity<?> getInstituteServiceReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody ServiceReportRequest ticket) {
		logger.info("Instiute ServiceReport Starts:::::::::" + ticket.toString());
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = ticketService.getInstituteServiceReport(ticket);
		} catch (Exception Ex) {
			logger.error("Instiute ServiceReport Starts:::::::::" + Ex);
			Ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
