package com.autolib.helpdesk.Reminder.model;

import java.util.Date;
import java.util.List;

public class ReminderRequest {

	private Reminder reminder;
	private List<Reminder> reminders;

	private Date eventDateTimeFrom;
	private Date eventDateTimeTo;

	public Reminder getReminder() {
		return reminder;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	public Date getEventDateTimeFrom() {
		return eventDateTimeFrom;
	}

	public void setEventDateTimeFrom(Date eventDateTimeFrom) {
		this.eventDateTimeFrom = eventDateTimeFrom;
	}

	public Date getEventDateTimeTo() {
		return eventDateTimeTo;
	}

	public void setEventDateTimeTo(Date eventDateTimeTo) {
		this.eventDateTimeTo = eventDateTimeTo;
	}

	@Override
	public String toString() {
		return "ReminderRequest [reminder=" + reminder + ", reminders=" + reminders + ", eventDateTimeFrom="
				+ eventDateTimeFrom + ", eventDateTimeTo=" + eventDateTimeTo + "]";
	}

}
