//package com.autolib.helpdesk.jwt.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "institute_contact")
//public class UserLoginDetails {
//
//	@Id
//	private Integer id;
//
//	@Column(name = "email_id")
//	protected String emailId;
//	@Column
//	protected String password;
//	@Column(name = "institute_id")
//	protected String instituteId;
//	@Column(name = "is_blocked")
//	protected Short isBlocked;
//
//	public UserLoginDetails() {
//		super();
//	}
//
//	public UserLoginDetails(String emailId, String password, String instituteId, Short isBlocked) {
//		super();
//		this.emailId = emailId;
//		this.password = password;
//		this.instituteId = instituteId;
//		this.isBlocked = isBlocked;
//	}
//
//	public String getUserName() {
//		return emailId;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public String getInstituteId() {
//		return instituteId;
//	}
//
//	public int getIsBlocked() {
//		return isBlocked;
//	}
//
//	@Override
//	public String toString() {
//		return "UserLoginDetails [emailId=" + emailId + ", password=" + password + ", instituteId=" + instituteId
//				+ ", isBlocked=" + isBlocked + "]";
//	}
//
//}
