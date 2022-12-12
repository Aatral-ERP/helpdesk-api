package com.autolib.helpdesk.Sales.model.PaymentReminder;

import java.util.List;

public class PaymentReminderRequest {

	private List<PaymentReminder> paymentReminders;

	public List<PaymentReminder> getPaymentReminders() {
		return paymentReminders;
	}

	public void setPaymentReminders(List<PaymentReminder> paymentReminders) {
		this.paymentReminders = paymentReminders;
	}

}
