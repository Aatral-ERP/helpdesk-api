package com.autolib.helpdesk.Sales.model.Invoice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "settings_invoice_email_reminder")
public class InvoiceEmailReminderSettings {

	public InvoiceEmailReminderSettings() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column
	private int id;

	@Column(name = "send_reminder_email", nullable = false)
	private boolean sendReminderEmail = false;

	@Lob
	@Column
	private String reminderSubjectTemplate;

	@Lob
	@Column
	private String reminderContentTemplate;

	@Column
	private boolean daysBefore0 = true;

	@Column
	private boolean daysBefore1 = true;

	@Column
	private boolean daysBefore7 = false;

	@Column
	private boolean daysBefore15 = false;

	@Column
	private boolean daysAfter1 = false;

	@Column
	private boolean daysAfter7 = false;

	@Column
	private boolean daysAfter15 = false;

	@Column
	private boolean daysAfter30 = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSendReminderEmail() {
		return sendReminderEmail;
	}

	public void setSendReminderEmail(boolean sendReminderEmail) {
		this.sendReminderEmail = sendReminderEmail;
	}

	public String getReminderSubjectTemplate() {
		return reminderSubjectTemplate;
	}

	public void setReminderSubjectTemplate(String reminderSubjectTemplate) {
		this.reminderSubjectTemplate = reminderSubjectTemplate;
	}

	public String getReminderContentTemplate() {
		return reminderContentTemplate;
	}

	public void setReminderContentTemplate(String reminderContentTemplate) {
		this.reminderContentTemplate = reminderContentTemplate;
	}

	public boolean isDaysBefore0() {
		return daysBefore0;
	}

	public void setDaysBefore0(boolean daysBefore0) {
		this.daysBefore0 = daysBefore0;
	}

	public boolean isDaysBefore1() {
		return daysBefore1;
	}

	public void setDaysBefore1(boolean daysBefore1) {
		this.daysBefore1 = daysBefore1;
	}

	public boolean isDaysBefore7() {
		return daysBefore7;
	}

	public void setDaysBefore7(boolean daysBefore7) {
		this.daysBefore7 = daysBefore7;
	}

	public boolean isDaysBefore15() {
		return daysBefore15;
	}

	public void setDaysBefore15(boolean daysBefore15) {
		this.daysBefore15 = daysBefore15;
	}

	public boolean isDaysAfter1() {
		return daysAfter1;
	}

	public void setDaysAfter1(boolean daysAfter1) {
		this.daysAfter1 = daysAfter1;
	}

	public boolean isDaysAfter7() {
		return daysAfter7;
	}

	public void setDaysAfter7(boolean daysAfter7) {
		this.daysAfter7 = daysAfter7;
	}

	public boolean isDaysAfter15() {
		return daysAfter15;
	}

	public void setDaysAfter15(boolean daysAfter15) {
		this.daysAfter15 = daysAfter15;
	}

	public boolean isDaysAfter30() {
		return daysAfter30;
	}

	public void setDaysAfter30(boolean daysAfter30) {
		this.daysAfter30 = daysAfter30;
	}

}
