/**
 * 
 */
package com.autolib.helpdesk.Tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Tickets.model.TicketRating;

/**
 * @author Kannadasan
 *
 */
public interface TicketRatingRepository extends JpaRepository<TicketRating, Integer> {

	TicketRating findByTicketId(int ticketId);

}
