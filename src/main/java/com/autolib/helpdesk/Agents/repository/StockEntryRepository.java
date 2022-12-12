/**
 * 
 */
package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Agents.entity.StockEntry;

/**
 * @author Kannadasan
 *
 */
public interface StockEntryRepository extends JpaRepository<StockEntry, Integer> {

	/**
	 * @param id
	 * @return
	 */
	List<StockEntry> findByProductId(int id);

}
