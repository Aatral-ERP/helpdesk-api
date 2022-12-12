package com.autolib.helpdesk.Institutes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.autolib.helpdesk.Institutes.model.AMCDetails;
import com.autolib.helpdesk.Institutes.model.Institute;

public interface InstituteAmcRepository extends CrudRepository<AMCDetails, Integer> {
	
	List<AMCDetails> findByInstitute(Institute inst);

//	AMCDetails findByAmcId(String aid);

	AMCDetails findById(int aid);

	AMCDetails findByAmcIdAndProduct(String invoiceNo, int productId);
}
