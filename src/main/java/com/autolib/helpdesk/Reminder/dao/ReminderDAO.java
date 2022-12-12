package com.autolib.helpdesk.Reminder.dao;

import java.util.Map;

import com.autolib.helpdesk.Reminder.model.ReminderRequest;
import com.autolib.helpdesk.Reminder.model.ReminderSearchRequest;

public interface ReminderDAO {

	Map<String, Object> addReminder(ReminderRequest request);

	Map<String, Object> editReminders(ReminderRequest request);

	Map<String, Object> getReminders(String recurringId);

	Map<String, Object> deleteReminders(ReminderRequest request);

	Map<String, Object> searchReminders(ReminderSearchRequest request);
}
