package com.autolib.helpdesk.LeadManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.LeadManagement.model.LeadMeeting;

public interface LeadMeetingRepository extends JpaRepository<LeadMeeting, Integer> {

	List<LeadMeeting> findByLeadId(int leadId);

}
