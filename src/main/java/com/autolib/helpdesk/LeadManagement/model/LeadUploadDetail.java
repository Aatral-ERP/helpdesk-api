package com.autolib.helpdesk.LeadManagement.model;

import java.util.ArrayList;
import java.util.List;

public class LeadUploadDetail {

	public LeadUploadDetail(int rowNo) {
		super();
		this.rowNo = rowNo;
	}

	private int rowNo;
	private String sno;
	private String ownerEmailId;
	private String ownerName;
	private String company;
	private String contactPersonFullName;
	private String leadTitle;
	private String leadSource;
	private String category;
	private String status;
	private String industryType;
	private String noOfEmployees;
	private String annualRevenue;
	private String street;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String website;
	private String sendEmailUpdates;
	private String activeLead;
	private String description;
	private String products;
	private String leadDate;

	private List<LeadUploadContactDetail> contactDetails = new ArrayList<>();

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

	public String getContactPersonFullName() {
		return contactPersonFullName;
	}

	public void setContactPersonFullName(String contactPersonFullName) {
		this.contactPersonFullName = contactPersonFullName;
	}

	public String getLeadTitle() {
		return leadTitle;
	}

	public void setLeadTitle(String leadTitle) {
		this.leadTitle = leadTitle;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSendEmailUpdates() {
		return sendEmailUpdates;
	}

	public void setSendEmailUpdates(String sendEmailUpdates) {
		this.sendEmailUpdates = sendEmailUpdates;
	}

	public String getActiveLead() {
		return activeLead;
	}

	public void setActiveLead(String activeLead) {
		this.activeLead = activeLead;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getLeadDate() {
		return leadDate;
	}

	public void setLeadDate(String leadDate) {
		this.leadDate = leadDate;
	}

	@Override
	public String toString() {
		return "LeadUploadDetail [rowNo=" + rowNo + ", sno=" + sno + ", ownerEmailId=" + ownerEmailId + ", ownerName="
				+ ownerName + ", company=" + company + ", contactPersonFullName=" + contactPersonFullName
				+ ", leadTitle=" + leadTitle + ", leadSource=" + leadSource + ", category=" + category + ", status="
				+ status + ", industryType=" + industryType + ", noOfEmployees=" + noOfEmployees + ", annualRevenue="
				+ annualRevenue + ", street=" + street + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", pincode=" + pincode + ", website=" + website + ", sendEmailUpdates=" + sendEmailUpdates
				+ ", activeLead=" + activeLead + ", description=" + description + ", products=" + products
				+ ", leadDate=" + leadDate + "]";
	}

	public List<LeadUploadContactDetail> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<LeadUploadContactDetail> contactDetails) {
		this.contactDetails = contactDetails;
	}

}
