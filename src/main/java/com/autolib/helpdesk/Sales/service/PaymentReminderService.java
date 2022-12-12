package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import com.autolib.helpdesk.Sales.model.PaymentReminder.PaymentReminderRequest;

public interface PaymentReminderService {

	Map<String, Object> savePaymentReminder(PaymentReminderRequest request);

	Map<String, Object> deletePaymentReminder(PaymentReminderRequest request);

	Map<String, Object> getPaymentReminder(int reminderId);

}
