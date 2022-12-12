package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealEmail;

public interface DealEmailRepository extends JpaRepository<DealEmail, Integer> {

	DealEmail findById(int dealEmailId);

	List<DealEmail> findByDealId(int dealId);

	DealEmail findOneByDealIdAndCreatedByOrderByCreateddatetimeDesc(int dealId, String createdBy);

}
