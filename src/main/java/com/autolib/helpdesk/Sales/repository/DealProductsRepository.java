/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealProducts;

/**
 * @author Kannadasan
 *
 */
public interface DealProductsRepository extends JpaRepository<DealProducts, Integer> {

	List<DealProducts> findByDealId(int dealId);

	void deleteByDealId(int quoteId);

	/**
	 * @param dealIds
	 * @return
	 */
	List<DealProducts> findAllByDealIdIn(List<Integer> dealIds);

}
