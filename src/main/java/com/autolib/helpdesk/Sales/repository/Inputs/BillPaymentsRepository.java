package com.autolib.helpdesk.Sales.repository.Inputs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Inputs.BillPayments;

public interface BillPaymentsRepository extends JpaRepository<BillPayments, Integer> {

	List<BillPayments> findByBillId(int billId);

}
