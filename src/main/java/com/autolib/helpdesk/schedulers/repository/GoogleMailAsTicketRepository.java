/**
 * 
 */
package com.autolib.helpdesk.schedulers.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;

/**
 * @author Kannadasan
 *
 */
public interface GoogleMailAsTicketRepository extends JpaRepository<GoogleMailAsTicket, Integer> {

	GoogleMailAsTicket findByIdMail(String idMail);
	
	GoogleMailAsTicket findByCreateddatetimeBetween(String fromDate,String toDate);
	
	@Transactional
	@Modifying
	@Query(value = "delete from GoogleMailAsTicket g where g.createddatetime < ?1")
	void deleteOldMails(Date date);

}
