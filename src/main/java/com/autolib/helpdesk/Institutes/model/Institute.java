package com.autolib.helpdesk.Institutes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;

@Entity
@Table(name = "institutes", indexes = { @Index(name = "email_id_idx", columnList = "email_id", unique = false) })
public class Institute implements Serializable {

	public Institute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Institute(String instituteId) {
		super();
		this.instituteId = instituteId;
	}

	public Institute(String instituteId, String instituteName, String logourl, String city, String zipcode) {
		super();
		this.instituteId = instituteId;
		this.instituteName = instituteName;
		this.logourl = logourl;
		this.city = city;
		this.zipcode = zipcode;
	}

	public Institute(String instituteId, String instituteName, String logourl, String city, String zipcode,
			ServiceUnder serviceUnder, String emailId) {
		super();
		this.instituteId = instituteId;
		this.instituteName = instituteName;
		this.logourl = logourl;
		this.city = city;
		this.zipcode = zipcode;
		this.serviceUnder = serviceUnder;
		this.emailId = emailId;
	}

	public Institute(String instituteId, String instituteName, String logourl, String street1, String street2,
			String city, String state, String country, String zipcode) {
		super();
		this.instituteId = instituteId;
		this.instituteName = instituteName;
		this.logourl = logourl;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public Institute(String instituteId, String instituteName, String shortTerm, String logourl, String street1,
			String street2, String city, String state, String country, String zipcode) {
		super();
		this.instituteId = instituteId;
		if (shortTerm != null && !shortTerm.isEmpty())
			this.instituteName = shortTerm + " (" + instituteName + ")";
		else
			this.instituteName = instituteName;
		this.shortTerm = shortTerm;
		this.logourl = logourl;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public Institute(String instituteId, String instituteName, String shortTerm, String logourl, String street1,
			String street2, String city, String state, String country, String zipcode, String emailId,
			String alternateEmailId) {
		super();
		this.instituteId = instituteId;
		if (shortTerm != null && !shortTerm.isEmpty())
			this.instituteName = shortTerm + " (" + instituteName + ")";
		else
			this.instituteName = instituteName;
		this.shortTerm = shortTerm;
		this.logourl = logourl;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.emailId = emailId;
		this.alternateEmailId = alternateEmailId;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "institute_id", nullable = false, updatable = false)
	private String instituteId;
	@Column(name = "institute_name", length = 512, nullable = false)
	private String instituteName;
	@Column(name = "institute_type", nullable = false)
	private String instituteType;
	@Enumerated(EnumType.STRING)
	@Column(name = "service_under")
	private ServiceUnder serviceUnder;
	@Column
	private String shortTerm;
	@Column
	private String street1;
	@Column
	private String street2;
	@Column(length = 128)
	private String city;
	@Column(length = 128)
	private String state;
	@Column(length = 128)
	private String country;
	@Column(length = 15)
	private String zipcode;
	@Column(length = 45)
	private String phone;
	
	
	@Column(name = "alternate_phone", length = 45)
	private String alternatePhone;
	@Column(name = "email_id", length = 128)
	private String emailId;
	@Column(name = "alternate_email_id", length = 128)
	private String alternateEmailId;
	@Column(length = 45)
	private String gstno;
	@Column(length = 512)
	private String logourl;

	@Lob
	@Column
	private String keyInfo;
	@Lob
	@Column
	private String remarks;
	@Column
	private String agents;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
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

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	public String getInstituteType() {
		return instituteType;
	}

	public void setInstituteType(String instituteType) {
		this.instituteType = instituteType;
	}

	public ServiceUnder getServiceUnder() {
		return serviceUnder;
	}

	public void setServiceType(ServiceUnder serviceUnder) {
		this.serviceUnder = serviceUnder;
	}

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAgents() {
		return agents;
	}

	public String getShortTerm() {
		return shortTerm;
	}

	public void setShortTerm(String shortTerm) {
		this.shortTerm = shortTerm;
	}

	@Transient
	public String getFullAddress() {
		return street1 + ", " + street2 + ", " + city + ", " + state + ", " + country + " - " + zipcode;
	}

	@Override
	public String toString() {
		return "Institute [instituteId=" + instituteId + ", instituteName=" + instituteName + ", instituteType="
				+ instituteType + ", serviceUnder=" + serviceUnder + ", shortTerm=" + shortTerm + ", street1=" + street1
				+ ", street2=" + street2 + ", city=" + city + ", state=" + state + ", country=" + country + ", zipcode="
				+ zipcode + ", phone=" + phone + ", alternatePhone=" + alternatePhone + ", emailId=" + emailId
				+ ", alternateEmailId=" + alternateEmailId + ", gstno=" + gstno + ", logourl=" + logourl + ", keyInfo="
				+ keyInfo + ", remarks=" + remarks + ", agents=" + agents + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
