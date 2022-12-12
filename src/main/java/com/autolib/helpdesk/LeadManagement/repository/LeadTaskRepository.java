package com.autolib.helpdesk.LeadManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.LeadManagement.model.LeadTask;

public interface LeadTaskRepository extends JpaRepository<LeadTask, Integer> {

	List<LeadTask> findByLeadId(int leadId);

}
