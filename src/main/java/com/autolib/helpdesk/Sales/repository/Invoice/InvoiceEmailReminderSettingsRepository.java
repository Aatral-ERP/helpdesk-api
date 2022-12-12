package com.autolib.helpdesk.Sales.repository.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;

public interface InvoiceEmailReminderSettingsRepository extends JpaRepository<InvoiceEmailReminderSettings, Integer> {

	InvoiceEmailReminderSettings findById(int id);

}
