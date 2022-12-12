package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.TermsAndConditions;

public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditions, Integer> {

	List<TermsAndConditions> findByType(String type);

}
