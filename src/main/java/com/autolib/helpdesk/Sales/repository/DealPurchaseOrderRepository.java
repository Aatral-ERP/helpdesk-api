/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealPurchaseOrder;

/**
 * @author Kannadasan
 *
 */
public interface DealPurchaseOrderRepository extends JpaRepository<DealPurchaseOrder, Integer> {

	DealPurchaseOrder findByDealId(int dealId);
	
	DealPurchaseOrder findTopByOrderByIdDesc();

	DealPurchaseOrder findByPurchaseOrderNo(String poNo);

	DealPurchaseOrder findById(int Id);

	void deleteByDealId(int quoteId);

}
