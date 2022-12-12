package com.autolib.helpdesk.Sales.repository.Invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminder;

public interface DealInvoiceReminderRepository extends JpaRepository<DealInvoiceReminder, Integer> {

	List<DealInvoiceReminder> findByDealId(int dealId);

}
