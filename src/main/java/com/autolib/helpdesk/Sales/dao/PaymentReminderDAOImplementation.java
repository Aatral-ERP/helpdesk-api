package com.autolib.helpdesk.Sales.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Sales.model.PaymentReminder.PaymentReminder;
import com.autolib.helpdesk.Sales.model.PaymentReminder.PaymentReminderRequest;
import com.autolib.helpdesk.Sales.repository.PaymentReminderRepository;

@Repository
public class PaymentReminderDAOImplementation implements PaymentReminderDAO {

//	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	PaymentReminderRepository payReminderRepo;

	@Override
	public Map<String, Object> savePaymentReminder(PaymentReminderRequest request) {
		Map<String, Object> resp = new HashMap<>();
		try {

			request.setPaymentReminders(payReminderRepo.saveAll(request.getPaymentReminders()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> deletePaymentReminder(PaymentReminderRequest request) {
		Map<String, Object> resp = new HashMap<>();
		try {

			payReminderRepo.deleteAll(request.getPaymentReminders());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getPaymentReminder(int reminderId) {
		Map<String, Object> resp = new HashMap<>();
		PaymentReminder reminder = new PaymentReminder();
		try {

			reminder = payReminderRepo.findById(reminderId).get();

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.put("PaymentReminder", reminder);
		return resp;
	}

}
