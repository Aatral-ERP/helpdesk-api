package com.autolib.helpdesk.Tickets.dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

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

public interface TicketDAO {
	Map<String, Object> saveTicket(Ticket ticket);

	Map<String, Object> getTicket(Ticket ticket);

	Map<String, Object> deleteTicket(Ticket ticket);

	Map<String, Object> addTicketReply(TicketReply ticketReply);

	Map<String, Object> getAllTicketReply(TicketReply ticketReply);

	Map<String, Object> addAttachment(String ticketId, MultipartFile file);

	Map<String, Object> addAllInstituteTickets(Ticket ticket);

	Map<String, Object> updateTicket(TicketRequest TicketRequest);

	Map<String, Object> assignTicket(TicketRequest TicketRequest);

	Map<String, Object> getTicketReport(TicketReportRequest ticketRequest);

	Map<String, Object> getTicketReportV2(TicketReportRequest ticketRequest);

	Map<String, Object> getTicketReportData();

	Map<String, Object> sendTicketInvoice(TicketServiceInvoice tsi);

	Map<String, Object> getTicketInvoice(TicketServiceInvoice tsi);

	Map<String, Object> addTicketRating(TicketRating tr);

	Map<String, Object> saveGmailAsTicket(GmailAsTicketRequest req);

	Map<String, Object> saveCallReport(CallReport callrepo);

	Map<String, Object> getCallReport(String instituteId);

	Map<String, Object> saveCallReportAttachment(MultipartFile file, int id);

	Map<String, Object> getCallReportPdfTemplate(CallReportRequest callRepoReq);

	Map<String, Object> deleteCallReport(CallReport callreport);
	
	Map<String, Object> getTicketDashboardDetails(TicketReportRequest ticketRequest);

	Map<String, Object> getAgentPerformanceDetails(TicketRequest request);

	Map<String, Object> getInstituteServiceReport(ServiceReportRequest ticket);
}
