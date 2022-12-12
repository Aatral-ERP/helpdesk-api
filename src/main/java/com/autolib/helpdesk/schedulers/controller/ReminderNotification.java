package com.autolib.helpdesk.schedulers.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Agents.entity.PushNotification;
import com.autolib.helpdesk.Reminder.model.Reminder;
import com.autolib.helpdesk.Reminder.repository.ReminderRepository;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("reminders-notification")
public class ReminderNotification {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	ReminderRepository reminderRepo;

	@Autowired
	PushNotification notify;

	@GetMapping("SendReminderNotification")
	@Scheduled(cron = "1 * * * * *")
	void execute() {

		logger.info("SendReminderNotification starts:::" + Util.sdfFormatter(new Date(), "yyyy-MM-dd HH:mm"));

		List<Reminder> reminders = reminderRepo
				.findByReminderDateTime(Util.sdfFormatter(new Date(), "yyyy-MM-dd HH:mm"));

		System.out.println(reminders);

		reminders.forEach(reminder -> notify.sendProductsRawMaterialRequestNotify(reminder));

	}

}
