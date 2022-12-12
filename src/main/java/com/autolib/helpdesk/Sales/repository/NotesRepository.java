/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Notes;

/**
 * @author Kannadasan
 *
 */
public interface NotesRepository extends JpaRepository<Notes, Integer> {

	List<Notes> findByDealId(int dealId);

	void deleteByDealId(int quoteId);

}
