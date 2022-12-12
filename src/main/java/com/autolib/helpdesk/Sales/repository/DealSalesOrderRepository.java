/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.DealSalesOrder;

/**
 * @author Kannadasan
 *
 */
public interface DealSalesOrderRepository extends JpaRepository<DealSalesOrder, Integer> {

	DealSalesOrder findByDealId(int dealId);

	DealSalesOrder findTopByOrderByIdDesc();

	DealSalesOrder findById(int Id);

	DealSalesOrder findBySalesOrderNo(String soNo);

	void deleteByDealId(int quoteId);

	@Query(value = "SELECT * FROM deal_sales_order WHERE deal_id IN "
			+ "(SELECT id FROM deals WHERE deal_type IN (SELECT dl.deal_type FROM deals dl WHERE dl.id = ?1 )) "
			+ " ORDER BY id DESC LIMIT 1", nativeQuery = true)
	DealSalesOrder findLastSalesOrderByType(int dealId);

}
