/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.DealProjectImplementation;

/**
 * @author Kannadasan
 *
 */
public interface DealProjectImplementationRepository extends JpaRepository<DealProjectImplementation, Integer> {

	DealProjectImplementation findById(int id);

	/**
	 * @param dealId
	 * @return
	 */
	DealProjectImplementation findByDealId(int dealId);

	@Query(value = "select dpim from DealProjectImplementation as dpim "
			+ "where dpim.createddatetime BETWEEN ?2 AND ?3 AND ( dpim.manufacturingAgent = ?1 or dpim.deliveryAgent = ?1 "
			+ "or dpim.installedAgent = ?1 ) ")
	List<DealProjectImplementation> findMyAssignedProjectImplementations(String mailId, Date fromDate, Date toDate);

	/**
	 * @param dealId
	 */
	void deleteByDealId(int dealId);

}
