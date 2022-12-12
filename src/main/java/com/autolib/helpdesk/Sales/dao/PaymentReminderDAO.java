package com.autolib.helpdesk.Sales.dao;

import java.util.Map;

import com.autolib.helpdesk.Sales.model.PaymentReminder.PaymentReminderRequest;

public interface PaymentReminderDAO {

	Map<String, Object> savePaymentReminder(PaymentReminderRequest request);

	Map<String, Object> deletePaymentReminder(PaymentReminderRequest request);

	Map<String, Object> getPaymentReminder(int reminderId);

}
