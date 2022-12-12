package com.autolib.helpdesk.Agents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Agents.entity.InfoDetails;

public interface InfoDetailsRepository extends JpaRepository<InfoDetails, Integer> {

	InfoDetails findById(int id);

	@Query(value = "select i.mailContent from InfoDetails i")
	String getMailContent();

	@Query(value = "select new com.autolib.helpdesk.Agents.entity.InfoDetails(i.id, i.cmpName, i.cmpAddress, i.gstNo, i.bankDetails, i.zipcode) from InfoDetails i where i.id = 1")
	InfoDetails getInfoMinDetails();
}
