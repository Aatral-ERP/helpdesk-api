package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Agents.entity.RawMaterialRequestProducts;

public interface RawMaterialRequestProductsRepository extends JpaRepository<RawMaterialRequestProducts, Integer> {

	List<RawMaterialRequestProducts> findByRequestIdIn(List<Integer> requestId);

	void deleteByRequestId(int id);

}
