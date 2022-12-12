package com.autolib.helpdesk.LeadManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplate;

public interface LeadMailTemplateRepository extends JpaRepository<LeadMailTemplate, Integer> {

	LeadMailTemplate findById(int id);

	List<LeadMailTemplate> findByStatusIn(List<String> status);

	List<LeadMailTemplate> findByFrequencyInAndEnabled(List<String> status, boolean b);

//	List<LeadMailTemplate> findByDaily(boolean daily);

}
