/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.DealProformaInvoice;

/**
 * @author Kannadasan
 *
 */
public interface DealProformaInvoiceRepository extends JpaRepository<DealProformaInvoice, Integer> {

	DealProformaInvoice findByDealId(int dealId);

	DealProformaInvoice findTopByOrderByIdDesc();

	DealProformaInvoice findById(int Id);

	DealProformaInvoice findByProformaInvoiceNo(String invoiceNo);

	void deleteByDealId(int quoteId);

	@Query(value = "SELECT * FROM deal_proforma_invoices WHERE deal_id IN "
			+ "(SELECT id FROM deals WHERE deal_type IN (SELECT dl.deal_type FROM deals dl WHERE dl.id = ?1 )) "
			+ " ORDER BY id DESC LIMIT 1", nativeQuery = true)
	DealProformaInvoice findLastProformaInvoiceByType(int dealId);

}
