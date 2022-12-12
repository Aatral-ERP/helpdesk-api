/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.DealQuotation;

/**
 * @author Kannadasan
 *
 */
public interface DealQuotationRepository extends JpaRepository<DealQuotation, Integer> {

	DealQuotation findByDealId(int dealId);

	DealQuotation findByQuoteNo(String quoteNo);

	DealQuotation findTopByOrderByIdDesc();

	DealQuotation findById(int quoteId);

	void deleteByDealId(int quoteId);

	@Query(value = "SELECT * FROM deal_quotations WHERE deal_id IN "
			+ "(SELECT id FROM deals WHERE deal_type IN (SELECT dl.deal_type FROM deals dl WHERE dl.id = ?1 )) "
			+ " ORDER BY id DESC LIMIT 1", nativeQuery = true)
	DealQuotation findLastQuotationByType(int id);

}
