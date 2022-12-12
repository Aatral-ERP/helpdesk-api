package com.autolib.helpdesk.Sales.repository.Inputs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrders;

public interface PurchaseInputOrdersRepository extends JpaRepository<PurchaseInputOrders, Integer> {

	PurchaseInputOrders findById(int id);

	@Query(value = "select * from purchase_input_orders o where o.id != ?1 order by o.id desc limit 1 ", nativeQuery = true)
	PurchaseInputOrders findLastOrderById(int orderId);
}
