package com.autolib.helpdesk.Reminder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Reminder.model.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

	List<Reminder> findByRecurringId(String recurringId);

	@Query(value = "select * from reminders where DATE_FORMAT(event_date_time, '%Y-%m-%d %H:%i') = ?1", nativeQuery = true)
	List<Reminder> findByReminderDateTime(String dateTime);

}
