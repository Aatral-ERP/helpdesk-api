package com.autolib.helpdesk.Tickets.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.model.TicketReply;

public interface TicketReplyRepository extends CrudRepository<TicketReply, Integer> {

	List<TicketReply> findByTicket(Ticket ticket);
}
