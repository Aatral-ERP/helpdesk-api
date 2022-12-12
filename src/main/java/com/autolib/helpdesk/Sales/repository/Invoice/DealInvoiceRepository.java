/**
 * 
 */
package com.autolib.helpdesk.Sales.repository.Invoice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;

/**
 * @author Kannadasan
 *
 */
public interface DealInvoiceRepository extends JpaRepository<DealInvoice, Integer> {

//	DealInvoice findByDealId(int dealId);

	List<DealInvoice> findByDealId(int dealId);

	DealInvoice findTopByOrderByIdDesc();

	DealInvoice findById(int Id);

	DealInvoice findByInvoiceNo(String invoiceNo);

	void deleteByDealId(int dealId);

	@Query("select d as deal,di as dealInvoice from DealInvoice di left join Deal d on (di.dealId = d.id) where di.invoiceDate between ?1 and ?2")
	List<Map<String, Object>> findAllDealInvoicesByDate(Date fromDate, Date toDate);

	@Query(value = "SELECT * FROM deal_invoices ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
	DealInvoice findLastDealInvoice();

	List<DealInvoice> findByPurchaseOrderNo(String poNo);

}
