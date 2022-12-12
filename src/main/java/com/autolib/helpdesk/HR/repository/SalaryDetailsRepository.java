package com.autolib.helpdesk.HR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.HR.entity.SalaryDetails;

public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails, String> {

	@Query(value = "select distinct s.bankName from SalaryDetails s")
	List<String> findBankName();

	SalaryDetails findByEmployeeId(String sid);

	List<SalaryDetails> findByEmployeeIdIn(List<String> agents);

	@Query(value = "select sd from SalaryDetails sd where sd.employeeId = (select a.employeeId from Agent a where a.emailId = ?1)")
	SalaryDetails findByEmployeeEmailId(String emailId);

}
