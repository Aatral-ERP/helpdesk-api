package com.autolib.helpdesk.Sales.repository.Invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailAttachments;

public interface InvoiceEmailAttachmentRepository extends JpaRepository<InvoiceEmailAttachments, Integer> {

	List<InvoiceEmailAttachments> findByEmailId(int invoiceEmailId);

	List<InvoiceEmailAttachments> findByInvoiceId(int invoiceId);

}
