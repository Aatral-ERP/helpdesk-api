package com.autolib.helpdesk.HR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.HR.entity.SalaryEntriesProperty;

public interface SalaryEntriesPropertyRepository extends JpaRepository<SalaryEntriesProperty, Integer> {

	void deleteAllBySalaryEntryId(int salaryEntryId);

	List<SalaryEntriesProperty> findBySalaryEntryId(int salaryEntryId);

	List<SalaryEntriesProperty> findAllBySalaryEntryIdIn(List<Integer> ids);
}
