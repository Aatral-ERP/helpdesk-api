package com.autolib.helpdesk.Institutes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "institute_contact", uniqueConstraints = {
		@UniqueConstraint(name = "uc_instid_email", columnNames = { "email_id" }) }, indexes = {
				@Index(name = "institute_id_idx", columnList = "institute_id"),
				@Index(name = "email_id_idx", columnList = "email_id") })
public class InstituteContact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "institute_id", length = 45)
	private String instituteId;
	@Column(name = "first_name", length = 128)
	private String firstName;
	@Column(name = "last_name", length = 128)
	private String lastName;
	@Column(length = 16)
	private String phone;
	@Column(name = "email_id", length = 45)
	private String emailId;
	@Column(updatable = false, nullable = false)
	private String password;
	@Column(name = "is_blocked", nullable = false)
	private Short isBlocked = 0;
	@Column(length = 128)
	private String street1;
	@Column(length = 128)
	private String street2;
	@Column(length = 45)
	private String city;
	@Column(length = 45)
	private String state;
	@Column(length = 45)
	private String country;
	@Column(name = "zipcode", length = 16)
	private String zipcode;

	public InstituteContact() {
		super();
		this.isBlocked = 0;
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Short getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Short isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "InstituteContact [id=" + id + ", instituteId=" + instituteId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phone=" + phone + ", emailId=" + emailId + ", street1=" + street1
				+ ", street2=" + street2 + ", city=" + city + ", state=" + state + ", country=" + country + ", zipcode="
				+ zipcode + ", isBlocked=" + isBlocked + "]";
	}

}
