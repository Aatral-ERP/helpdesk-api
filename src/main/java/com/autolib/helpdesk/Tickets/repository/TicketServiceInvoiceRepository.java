package com.autolib.helpdesk.Tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.model.TicketServiceInvoice;

public interface TicketServiceInvoiceRepository extends JpaRepository<TicketServiceInvoice, Integer> {

	TicketServiceInvoice findByTicket(Ticket ticket);

}
