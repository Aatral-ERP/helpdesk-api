/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealContacts;

/**
 * @author Kannadasan
 *
 */
public interface DealContactsRepository extends JpaRepository<DealContacts, Integer> {

	List<DealContacts> findByDealId(int quoteId);

	void deleteByDealId(int dealId);

}
