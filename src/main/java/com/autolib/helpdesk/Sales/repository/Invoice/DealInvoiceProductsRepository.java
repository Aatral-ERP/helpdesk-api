package com.autolib.helpdesk.Sales.repository.Invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceProducts;

public interface DealInvoiceProductsRepository extends JpaRepository<DealInvoiceProducts, Integer> {

	void deleteByInvoiceId(int id);

	List<DealInvoiceProducts> findByInvoiceId(int invoiceId);

	List<DealInvoiceProducts> findByInvoiceIdIn(List<Integer> ids);

	@Modifying
	@Query("delete from DealInvoiceProducts dip where dip.invoiceId in ?1")
	void deleteByInvoiceIdsIn(List<Integer> invoiceIds);

}
