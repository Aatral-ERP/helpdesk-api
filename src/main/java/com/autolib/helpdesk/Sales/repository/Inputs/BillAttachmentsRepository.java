package com.autolib.helpdesk.Sales.repository.Inputs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Inputs.BillAttachments;

public interface BillAttachmentsRepository extends JpaRepository<BillAttachments, Integer> {

	void deleteByBillId(int billId);

	List<BillAttachments> findByBillId(int billId);

}
