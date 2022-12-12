package com.autolib.helpdesk.Reminder.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Reminder.dao.ReminderDAO;
import com.autolib.helpdesk.Reminder.model.ReminderRequest;
import com.autolib.helpdesk.Reminder.model.ReminderSearchRequest;

@Service
public class ReminderServiceImpl implements ReminderService {

	@Autowired
	ReminderDAO reminderDAO;

	@Override
	public Map<String, Object> addReminder(ReminderRequest request) {
		return reminderDAO.addReminder(request);
	}

	@Override
	public Map<String, Object> editReminders(ReminderRequest request) {
		return reminderDAO.editReminders(request);
	}

	@Override
	public Map<String, Object> deleteReminders(ReminderRequest request) {
		return reminderDAO.deleteReminders(request);
	}

	@Override
	public Map<String, Object> getReminders(String recurringId) {
		return reminderDAO.getReminders(recurringId);
	}

	@Override
	public Map<String, Object> searchReminders(ReminderSearchRequest request) {
		return reminderDAO.searchReminders(request);
	}

}
