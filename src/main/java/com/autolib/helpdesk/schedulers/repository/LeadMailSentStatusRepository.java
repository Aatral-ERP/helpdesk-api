package com.autolib.helpdesk.schedulers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.schedulers.model.LeadMailSentStatus;

public interface LeadMailSentStatusRepository extends JpaRepository<LeadMailSentStatus, Integer> {

}
