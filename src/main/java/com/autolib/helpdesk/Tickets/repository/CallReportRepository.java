package com.autolib.helpdesk.Tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Tickets.model.CallReport;

public interface CallReportRepository extends JpaRepository<CallReport,Integer>{
	
	CallReport findById(int id);
	
	List<CallReport> findByInstituteId(String instituteId);

}
