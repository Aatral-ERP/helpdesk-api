package com.autolib.helpdesk.Institutes.model;

import java.util.List;

public class InstituteContactRequest {
	
	
	private List<Institute> institutes;
	public String firstName;
	public String phone;	
	public String city;
	public String emailId;
	private Short isBlocked = 0;
	
	public List<Institute> getInstitutes() {
		return institutes;
	}
	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Short getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(Short isBlocked) {
		this.isBlocked = isBlocked;
	}
	

}
