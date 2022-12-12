package com.autolib.helpdesk.Reminder.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Reminder.model.Reminder;
import com.autolib.helpdesk.Reminder.model.ReminderRequest;
import com.autolib.helpdesk.Reminder.model.ReminderSearchRequest;
import com.autolib.helpdesk.Reminder.repository.ReminderRepository;
import com.autolib.helpdesk.common.Util;

@Repository
public class ReminderDAOImpl implements ReminderDAO {

	@Autowired
	ReminderRepository remRepo;

	@Autowired
	EntityManager em;

	@Override
	public Map<String, Object> addReminder(ReminderRequest request) {
		Map<String, Object> resp = new HashMap<>();

		try {
			String recurringId = String.valueOf(Util.generateRandomPassword());

			request.getReminders().forEach(rem -> rem.setRecurringId(recurringId));

			request.setReminders(remRepo.saveAll(request.getReminders()));

			resp.put("RecurringId", recurringId);
			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> editReminders(ReminderRequest request) {
		Map<String, Object> resp = new HashMap<>();

		try {

			request.setReminders(remRepo.saveAll(request.getReminders()));

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Reminders", request.getReminders());
		return resp;
	}

	@Override
	public Map<String, Object> getReminders(String recurringId) {
		Map<String, Object> resp = new HashMap<>();
		List<Reminder> reminders = new ArrayList<>();
		try {

			reminders = remRepo.findByRecurringId(recurringId);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Reminders", reminders);
		return resp;
	}

	@Override
	public Map<String, Object> deleteReminders(ReminderRequest request) {
		Map<String, Object> resp = new HashMap<>();
		List<Reminder> reminders = new ArrayList<>();
		try {

			remRepo.deleteAll(request.getReminders());

			reminders = remRepo.findByRecurringId(request.getReminders().get(0).getRecurringId());

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Reminders", reminders);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchReminders(ReminderSearchRequest request) {
		Map<String, Object> resp = new HashMap<>();
		List<Reminder> reminders = new ArrayList<>();
		try {

			String filterQuery = "";

			if (request.getAgentEmailId() != null && !request.getAgentEmailId().isEmpty()) {
				filterQuery = filterQuery + " and r.agentEmailId = '" + request.getAgentEmailId() + "'";
			}

			if (request.getEventDateTimeFrom() != null && request.getEventDateTimeTo() != null) {
				filterQuery = filterQuery + " and r.eventDateTime between '"
						+ Util.sdfFormatter(request.getEventDateTimeFrom()) + "' and '"
						+ Util.sdfFormatter(request.getEventDateTimeTo()) + " 23:59:59'";
			}

			Query query = em.createQuery("select r from Reminder r where 2 > 1  " + filterQuery, Reminder.class);

			reminders = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.put("Reminders", reminders);
		return resp;
	}

}
