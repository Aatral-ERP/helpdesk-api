package com.autolib.helpdesk.Sales.repository.Inputs;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Inputs.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

	Bill findById(int id);
}
