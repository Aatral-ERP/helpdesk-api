package com.autolib.helpdesk.Accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Accounting.model.LetterPad;

public interface LetterpadRepository extends JpaRepository<LetterPad,Integer> {
	
	LetterPad findById(int id);

}
