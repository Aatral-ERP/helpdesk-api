package com.autolib.helpdesk.Sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.PaymentReminder.PaymentReminder;

public interface PaymentReminderRepository extends JpaRepository<PaymentReminder, Integer> {

}
