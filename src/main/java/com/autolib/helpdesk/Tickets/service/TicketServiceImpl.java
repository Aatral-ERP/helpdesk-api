package com.autolib.helpdesk.Tickets.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Tickets.dao.TicketDAO;
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

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketDAO ticketDAO;

	@Override
	public Map<String, Object> saveTicket(Ticket ticket) {
		return ticketDAO.saveTicket(ticket);
	}

	@Override
	public Map<String, Object> getTicket(Ticket ticket) {
		return ticketDAO.getTicket(ticket);
	}

	@Override
	public Map<String, Object> addTicketReply(TicketReply ticketReply) {
		return ticketDAO.addTicketReply(ticketReply);
	}

	@Override
	public Map<String, Object> getAllTicketReply(TicketReply ticketReply) {
		return ticketDAO.getAllTicketReply(ticketReply);
	}

	@Override
	public Map<String, Object> deleteTicket(Ticket ticket) {
		return ticketDAO.deleteTicket(ticket);
	}

	@Override
	public Map<String, Object> addAttachment(String ticketId, MultipartFile file) {
		return ticketDAO.addAttachment(ticketId, file);
	}

	@Override
	public Map<String, Object> addAllInstituteTickets(Ticket ticket) {
		return ticketDAO.addAllInstituteTickets(ticket);
	}

	@Override
	public Map<String, Object> updateTicket(TicketRequest TicketRequest) {
		return ticketDAO.updateTicket(TicketRequest);
	}

	@Override
	public Map<String, Object> assignTicket(TicketRequest TicketRequest) {
		return ticketDAO.assignTicket(TicketRequest);
	}

	@Override
	public Map<String, Object> getTicketReportData() {
		return ticketDAO.getTicketReportData();
	}

	@Override
	public Map<String, Object> getTicketReport(TicketReportRequest ticketRequest) {
		if (ticketRequest.getRequestVersion() != null && ticketRequest.getRequestVersion().equalsIgnoreCase("v2"))
			return ticketDAO.getTicketReportV2(ticketRequest);
		else
			return ticketDAO.getTicketReport(ticketRequest);
	}

	@Override
	public Map<String, Object> sendTicketInvoice(TicketServiceInvoice tsi) {
		// TODO Auto-generated method stub
		return ticketDAO.sendTicketInvoice(tsi);
	}

	@Override
	public Map<String, Object> getTicketInvoice(TicketServiceInvoice tsi) {
		// TODO Auto-generated method stub
		return ticketDAO.getTicketInvoice(tsi);
	}

	@Override
	public Map<String, Object> addTicketRating(TicketRating tr) {
		// TODO Auto-generated method stub
		return ticketDAO.addTicketRating(tr);
	}

	@Override
	public Map<String, Object> saveGmailAsTicket(GmailAsTicketRequest req) {
		// TODO Auto-generated method stub
		return ticketDAO.saveGmailAsTicket(req);
	}

	@Override
	public Map<String, Object> saveCallReport(CallReport callrepo) {
		return ticketDAO.saveCallReport(callrepo);
	}

	public Map<String, Object> getCallReport(String instituteId) {
		return ticketDAO.getCallReport(instituteId);
	}

	@Override
	public Map<String, Object> saveCallReportAttachment(MultipartFile file, int id) {
		return ticketDAO.saveCallReportAttachment(file, id);
	}

	@Override
	public Map<String, Object> getCallReportPdfTemplate(CallReportRequest callRepoReq) {
		return ticketDAO.getCallReportPdfTemplate(callRepoReq);
	}

	@Override
	public Map<String, Object> deleteCallReport(CallReport callreport) {
		return ticketDAO.deleteCallReport(callreport);
	}

	@Override
	public Map<String, Object> getTicketDashboardDetails(TicketReportRequest ticketRequest) {
		return ticketDAO.getTicketDashboardDetails(ticketRequest);
	}

	@Override
	public Map<String, Object> getAgentPerformanceDetails(TicketRequest request) {
		return ticketDAO.getAgentPerformanceDetails(request);
	}

	@Override
	public Map<String, Object> getInstituteServiceReport(ServiceReportRequest ticket) {
		return ticketDAO.getInstituteServiceReport(ticket);
	}

}
