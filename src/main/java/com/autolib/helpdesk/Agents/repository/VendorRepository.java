package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Agents.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	Vendor findById(int id);

	@Query(value = "select new Vendor(v.id, v.vendorName) from Vendor v")
	List<Vendor> findAllMinDetails();

}
