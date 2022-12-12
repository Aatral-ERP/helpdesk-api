package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Sales.dao.PaymentReminderDAO;
import com.autolib.helpdesk.Sales.model.PaymentReminder.PaymentReminderRequest;

@Service
public class PaymentReminderServiceImplementation implements PaymentReminderService {

	@Autowired
	PaymentReminderDAO paymentReminderDAO;

	@Override
	public Map<String, Object> savePaymentReminder(PaymentReminderRequest request) {
		return paymentReminderDAO.savePaymentReminder(request);
	}

	@Override
	public Map<String, Object> deletePaymentReminder(PaymentReminderRequest request) {
		return paymentReminderDAO.deletePaymentReminder(request);
	}
	
	@Override
	public Map<String, Object> getPaymentReminder(int reminderId) {
		return paymentReminderDAO.getPaymentReminder(reminderId);
	}

}
