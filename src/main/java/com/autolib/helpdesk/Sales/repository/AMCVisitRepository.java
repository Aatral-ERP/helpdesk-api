package com.autolib.helpdesk.Sales.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisit;

public interface AMCVisitRepository extends JpaRepository<AMCVisit, Integer> {

	List<AMCVisit> findByDealId(int dealId);

	List<AMCVisit> findByVisitDate(Date visitDate);

}
