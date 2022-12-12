package com.autolib.helpdesk.Sales.repository.Invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceContacts;

public interface DealInvoiceContactsRepository extends JpaRepository<DealInvoiceContacts, Integer> {

	void deleteByInvoiceId(int id);

	List<DealInvoiceContacts> findByInvoiceId(int invoiceId);

}
