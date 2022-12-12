package com.autolib.helpdesk.LeadManagement.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

public class LeadSearchRequest {

	public LeadSearchRequest() {
		// TODO Auto-generated constructor stub
	}

	private List<String> companies = new ArrayList<>();
	private List<Agent> owners = new ArrayList<>();
	private List<String> products = new ArrayList<>();
	private List<String> industryTypes = new ArrayList<>();
	private List<String> leadSources = new ArrayList<>();
	private List<String> status = new ArrayList<>();
	private List<String> categories = new ArrayList<>();
	private String title = "";
	private String city = "";
	private String state = "";
	private String country = "";
	private String pincode = "";
	private String activeStatus = "";
	private Date leadDateFrom = null;
	private Date leadDateTo = null;
	private Date lastupdatedatetimeFrom = null;
	private Date lastupdatedatetimeTo = null;

	public List<String> getCompanies() {
		return companies;
	}

	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}

	public List<Agent> getOwners() {
		return owners;
	}

	public void setOwners(List<Agent> owners) {
		this.owners = owners;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public List<String> getIndustryTypes() {
		return industryTypes;
	}

	public void setIndustryTypes(List<String> industryTypes) {
		this.industryTypes = industryTypes;
	}

	public List<String> getLeadSources() {
		return leadSources;
	}

	public void setLeadSources(List<String> leadSources) {
		this.leadSources = leadSources;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getLastupdatedatetimeFrom() {
		return lastupdatedatetimeFrom;
	}

	public void setLastupdatedatetimeFrom(Date lastupdatedatetimeFrom) {
		this.lastupdatedatetimeFrom = lastupdatedatetimeFrom;
	}

	public Date getLastupdatedatetimeTo() {
		return lastupdatedatetimeTo;
	}

	public void setLastupdatedatetimeTo(Date lastupdatedatetimeTo) {
		this.lastupdatedatetimeTo = lastupdatedatetimeTo;
	}

	public Date getLeadDateFrom() {
		return leadDateFrom;
	}

	public void setLeadDateFrom(Date leadDateFrom) {
		this.leadDateFrom = leadDateFrom;
	}

	public Date getLeadDateTo() {
		return leadDateTo;
	}

	public void setLeadDateTo(Date leadDateTo) {
		this.leadDateTo = leadDateTo;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "LeadSearchRequest [companies=" + companies + ", owners=" + owners + ", products=" + products
				+ ", industryTypes=" + industryTypes + ", leadSources=" + leadSources + ", status=" + status
				+ ", categories=" + categories + ", title=" + title + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", pincode=" + pincode + ", activeStatus=" + activeStatus + ", leadDateFrom="
				+ leadDateFrom + ", leadDateTo=" + leadDateTo + ", lastupdatedatetimeFrom=" + lastupdatedatetimeFrom
				+ ", lastupdatedatetimeTo=" + lastupdatedatetimeTo + "]";
	}

	

}
