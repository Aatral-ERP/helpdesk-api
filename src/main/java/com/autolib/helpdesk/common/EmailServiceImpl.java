package com.autolib.helpdesk.common;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {
	@Autowired
	public static JavaMailSender emailSender;

	public static void main(String[] args) {

		Date date = new Date();
		System.out.println("=======inside sysout =========");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("kannadasantnp@gmail.com");
		message.setSubject("First Mail");
		message.setText("Hiiii..");
		message.setFrom("Anandhraj");
		message.setReplyTo("Kannadasan");
		message.setSentDate(date);
		System.out.println("===========message==============" + message);
		emailSender.send(message);
	}
}
