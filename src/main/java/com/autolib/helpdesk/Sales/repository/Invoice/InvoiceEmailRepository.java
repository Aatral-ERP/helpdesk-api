package com.autolib.helpdesk.Sales.repository.Invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;

public interface InvoiceEmailRepository extends JpaRepository<InvoiceEmail, Integer> {

	InvoiceEmail findById(int invoiceEmailId);

	List<InvoiceEmail> findByInvoiceId(int invoiceId);

	InvoiceEmail findOneByInvoiceIdAndCreatedByOrderByCreateddatetimeDesc(int invoiceId, String createdBy);

}
