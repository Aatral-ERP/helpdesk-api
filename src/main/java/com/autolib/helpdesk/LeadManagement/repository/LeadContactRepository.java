package com.autolib.helpdesk.LeadManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.autolib.helpdesk.LeadManagement.model.LeadContact;

public interface LeadContactRepository extends JpaRepository<LeadContact, Integer> {

	@Modifying
	void deleteByLeadId(int id);

	List<LeadContact> findByLeadId(int leadId);

	List<LeadContact> findByLeadIdIn(List<Integer> array);

}
