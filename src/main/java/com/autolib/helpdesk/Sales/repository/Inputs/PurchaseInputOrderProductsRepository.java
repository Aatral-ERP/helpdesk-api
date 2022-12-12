package com.autolib.helpdesk.Sales.repository.Inputs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrderProduct;

public interface PurchaseInputOrderProductsRepository extends JpaRepository<PurchaseInputOrderProduct, Integer> {

	void deleteByOrderId(int id);

	List<PurchaseInputOrderProduct> findByOrderId(int id);

}
