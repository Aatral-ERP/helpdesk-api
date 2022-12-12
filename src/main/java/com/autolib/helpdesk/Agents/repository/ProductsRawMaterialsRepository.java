package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Agents.entity.ProductsRawMaterials;

public interface ProductsRawMaterialsRepository extends JpaRepository<ProductsRawMaterials, Integer> {

	void deleteByProductId(int id);

	List<ProductsRawMaterials> findByProductId(int id);

}
