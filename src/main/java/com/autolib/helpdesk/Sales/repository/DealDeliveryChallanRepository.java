/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealDeliveryChallan;

/**
 * @author Kannadasan
 *
 */
public interface DealDeliveryChallanRepository extends JpaRepository<DealDeliveryChallan, Integer> {

//	DealDeliveryChallan findByDealId(int dealId);

	List<DealDeliveryChallan> findByDealId(int dealId);

	DealDeliveryChallan findById(int Id);

	void deleteByDealId(int quoteId);

}
