
package com.autolib.helpdesk.Sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.SalesInvoice;

public interface InvoiceRepository extends JpaRepository<SalesInvoice, String> {

	SalesInvoice findByInvoiceNo(String invoiceNo);

}
