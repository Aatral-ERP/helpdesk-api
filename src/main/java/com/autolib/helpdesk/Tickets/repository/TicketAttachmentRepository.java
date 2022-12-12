package com.autolib.helpdesk.Tickets.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.model.TicketAttachment;

public interface TicketAttachmentRepository extends CrudRepository<TicketAttachment, Integer> {
	
	List<TicketAttachment> findByTicket(Ticket ticket);

}
