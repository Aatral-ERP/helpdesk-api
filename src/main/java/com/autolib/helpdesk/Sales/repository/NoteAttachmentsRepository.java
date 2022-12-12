/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.NoteAttachments;

/**
 * @author Kannadasan
 *
 */
public interface NoteAttachmentsRepository extends JpaRepository<NoteAttachments, Integer> {

	List<NoteAttachments> findByDealId(int dealId);

	void deleteByDealId(int quoteId);

}
