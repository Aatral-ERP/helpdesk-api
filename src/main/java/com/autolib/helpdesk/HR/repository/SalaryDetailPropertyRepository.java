package com.autolib.helpdesk.HR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.HR.entity.SalaryDetailProperty;

public interface SalaryDetailPropertyRepository extends JpaRepository<SalaryDetailProperty, Integer> {

	void deleteAllByEmployeeId(String employeeId);

	List<SalaryDetailProperty> findByEmployeeId(String employeeId);

}
