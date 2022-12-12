package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealDCProducts;

public interface DealDCProductsRepository extends JpaRepository<DealDCProducts, Integer> {

	List<DealDCProducts> findByDealId(int id);

}
