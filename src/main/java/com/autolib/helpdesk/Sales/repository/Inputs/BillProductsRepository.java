package com.autolib.helpdesk.Sales.repository.Inputs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Inputs.BillProducts;

public interface BillProductsRepository extends JpaRepository<BillProducts, Integer> {

	void deleteByBillId(int billId);

	List<BillProducts> findByBillId(int billId);

}
