package com.autolib.helpdesk.LeadManagement.model;

public class LeadUploadContactDetail {

	public LeadUploadContactDetail(int rowNo) {
		super();
		this.rowNo = rowNo;
	}

	private int rowNo;
	private String sno;
	private String name;
	private String designation;
	private String emailId;
	private String phoneNo;
	private String alternateEmail;
	private String alternatePhoneNo;
	private String active;

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "LeadUploadContactDetail [rowNo=" + rowNo + ", sno=" + sno + ", name=" + name + ", designation="
				+ designation + ", emailId=" + emailId + ", phoneNo=" + phoneNo + ", alternateEmail=" + alternateEmail
				+ ", alternatePhoneNo=" + alternatePhoneNo + ", active=" + active + "]";
	}

}
