package com.autolib.helpdesk.LeadManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "lead_contacts", indexes = { @Index(name = "lead_id_idx", columnList = "lead_id") })
public class LeadContact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "lead_id")
	private int leadId;

	@Column
	private String name;

	@Column
	private String designation;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "phone_no", length = 45)
	private String phoneNo;

	@Column(name = "alternate_email_id")
	private String alternateEmail;

	@Column(name = "alternate_phone_no", length = 45)
	private String alternatePhoneNo;

	@Column(columnDefinition = "tinyint(1) default 1", nullable = false)
	private boolean active = true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeadId() {
		return leadId;
	}

	public void setLeadId(int leadId) {
		this.leadId = leadId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getAlternatePhoneNo() {
		return alternatePhoneNo;
	}

	public void setAlternatePhoneNo(String alternatePhoneNo) {
		this.alternatePhoneNo = alternatePhoneNo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "LeadContact [id=" + id + ", leadId=" + leadId + ", name=" + name + ", designation=" + designation
				+ ", emailId=" + emailId + ", phoneNo=" + phoneNo + ", alternateEmail=" + alternateEmail
				+ ", alternatePhoneNo=" + alternatePhoneNo + ", active=" + active + "]";
	}

}
