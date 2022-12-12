package com.autolib.helpdesk.LeadManagement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "leads", indexes = { @Index(name = "company_idx", columnList = "company"),
		@Index(name = "owner_email_id_idx", columnList = "owner_email_id"),
		@Index(name = "title_idx", columnList = "title"), @Index(name = "lead_source_idx", columnList = "lead_source"),
		@Index(name = "status_idx", columnList = "status"),
		@Index(name = "industry_type_idx", columnList = "industry_type"),
		@Index(name = "city_idx", columnList = "city"), @Index(name = "state_idx", columnList = "state"),
		@Index(name = "country_idx", columnList = "country"), @Index(name = "pincode_idx", columnList = "pincode"),
		@Index(name = "active_lead_idx", columnList = "active_lead"),
		@Index(name = "status_idx", columnList = "status"), @Index(name = "state_idx", columnList = "state"),
		@Index(name = "industry_types_idx", columnList = "industry_type"),
		@Index(name = "category_idx", columnList = "category") })
public class Lead {

	public Lead() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "owner_email_id")
	private String ownerEmailId = "";
	private String ownerName = "";
	private String company = "";
	private String fullName = "";
	private String title = "";
	@Column(name = "lead_source")
	private String leadSource = "";
	@Column(name = "category")
	private String category = "";
	@Column(name = "status")
	private String status = "Lead Created";

	@Column(name = "industry_type")
	private String industryType = "";
	private String noOfEmployees = "";
	private String annualRevenue = "";
	private String street = "";
	private String city = "";
	private String state = "";
	private String country = "";
	private String pincode = "";
	private String website = "";
	private String mailSetting = "";

	private boolean sendEmailUpdates = true;
	@Column(name = "active_lead")
	private boolean activeLead = true;
	@Lob
	private String description = "";
	@Lob
	private String products = "";
	@Lob
	private String files = "";

	@Temporal(TemporalType.DATE)
	@Column(name = "lead_date")
	private Date leadDate;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwnerEmailId() {
		return ownerEmailId;
	}

	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(String noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public String getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(String annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getAlternateEmail() {
//		return alternateEmail;
//	}
//
//	public void setAlternateEmail(String alternateEmail) {
//		this.alternateEmail = alternateEmail;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	public String getAlternatePhone() {
//		return alternatePhone;
//	}
//
//	public void setAlternatePhone(String alternatePhone) {
//		this.alternatePhone = alternatePhone;
//	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMailSetting() {
		return mailSetting;
	}

	public void setMailSetting(String mailSetting) {
		this.mailSetting = mailSetting;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSendEmailUpdates() {
		return sendEmailUpdates;
	}

	public void setSendEmailUpdates(boolean sendEmailUpdates) {
		this.sendEmailUpdates = sendEmailUpdates;
	}

	public boolean isActiveLead() {
		return activeLead;
	}

	public void setActiveLead(boolean activeLead) {
		this.activeLead = activeLead;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
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

	public Date getLeadDate() {
		return leadDate;
	}

	public void setLeadDate(Date leadDate) {
		this.leadDate = leadDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Lead [id=" + id + ", ownerEmailId=" + ownerEmailId + ", ownerName=" + ownerName + ", company=" + company
				+ ", fullName=" + fullName + ", title=" + title + ", leadSource=" + leadSource + ", category="
				+ category + ", status=" + status + ", industryType=" + industryType + ", noOfEmployees="
				+ noOfEmployees + ", annualRevenue=" + annualRevenue + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", pincode=" + pincode + ", website=" + website
				+ ", mailSetting=" + mailSetting + ", sendEmailUpdates=" + sendEmailUpdates + ", activeLead="
				+ activeLead + ", description=" + description + ", products=" + products + ", files=" + files
				+ ", leadDate=" + leadDate + ", createddatetime=" + createddatetime + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
