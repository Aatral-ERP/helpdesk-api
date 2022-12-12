/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealProjectImplementationComments;

/**
 * @author Kannadasan
 *
 */
public interface DealProjectImplementationCommentsRepository
		extends JpaRepository<DealProjectImplementationComments, Integer> {

	/**
	 * @param dealId
	 * @return
	 */
	List<DealProjectImplementationComments> findByDealId(int dealId);

}
