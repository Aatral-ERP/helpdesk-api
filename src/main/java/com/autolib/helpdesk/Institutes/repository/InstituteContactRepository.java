package com.autolib.helpdesk.Institutes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Institutes.model.InstituteContact;

public interface InstituteContactRepository extends JpaRepository<InstituteContact, Integer> {

	List<InstituteContact> findByInstituteId(String instituteId);
	
	List<InstituteContact> findMinDetailsByInstituteId(String instituteId);

	InstituteContact findById(int id);

	InstituteContact findByEmailId(String emailId);
}
