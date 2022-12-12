/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealPayments;

/**
 * @author Kannadasan
 *
 */
public interface DealPaymentsRepository extends JpaRepository<DealPayments, Integer> {

	List<DealPayments> findByDealId(int dealId);

	List<DealPayments> findByInvoiceId(int invoiceId);

	DealPayments findById(int id);

	void deleteByDealId(int dealId);

}
