package com.autolib.helpdesk.Institutes.model;

import java.util.List;

public class InstituteRequest {
	
	private List<Institute> institutes;
	private String instituteId;
	private String instituteType;
	private String city;
	private String phone;
	private String alternatePhone;
	private String emailId;
	private String alternateEmailId;
	private String shortTerm;
	
	
	public List<Institute> getInstitutes() {
		return institutes;
	}
	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}
	public String getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteType() {
		return instituteType;
	}
	public void setInstituteType(String instituteType) {
		this.instituteType = instituteType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAlternatePhone() {
		return alternatePhone;
	}
	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAlternateEmailId() {
		return alternateEmailId;
	}
	public void setAlternateEmailId(String alternateEmailId) {
		this.alternateEmailId = alternateEmailId;
	}
	public String getShortTerm() {
		return shortTerm;
	}
	public void setShortTerm(String shortTerm) {
		this.shortTerm = shortTerm;
	}
	
}
