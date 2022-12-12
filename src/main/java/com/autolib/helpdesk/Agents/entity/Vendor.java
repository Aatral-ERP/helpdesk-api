package com.autolib.helpdesk.Agents.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor {

	public Vendor() {

	}

	public Vendor(int id, String vendorName) {
		super();
		this.id = id;
		this.vendorName = vendorName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "vendor_name", length = 256, nullable = false)
	private String vendorName;

	@Column(name = "vendor_phone", length = 32)
	private String vendorPhone;

	@Column(name = "vendor_email", length = 64)
	private String vendorEmail;

	@Column(name = "vendor_gst_no", length = 32)
	private String gstNo;
	
	@Column(name = "vendor_landline", length = 128)
	private String landLine;
	
	
	@Column(name = "vendor_add1", length = 255)
	private String address1;
	
	@Column(name = "vendor_add2", length = 255)
	private String address2;
	
	@Column(name = "vendor_city", length = 128)
	private String city;
	
	@Column(name = "vendor_state", length = 128)
	private String state;
	
	@Column(name = "vendor_country", length = 128)
	private String country;
	
	@Column(name = "vendor_pincode", length = 10)
	private String pincode;
	
	@Column(name = "account_name", length = 128)
	private String accountName;
	
	@Column(name = "account_number", length = 128)
	private String accountNumber;
	
	@Column(name = "branch_name", length = 64)
	private String branchName;
	
	@Column(name = "ifsc_code", length = 32)
	private String ifscCode;
	
	@Column(name = "bank_name", length = 128)
	private String bankName;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorPhone() {
		return vendorPhone;
	}

	public void setVendorPhone(String vendorPhone) {
		this.vendorPhone = vendorPhone;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getGstNo() {
		return gstNo;
	}

	public String getLandLine() {
		return landLine;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", vendorName=" + vendorName + ", vendorPhone=" + vendorPhone + ", vendorEmail="
				+ vendorEmail + ", gstNo=" + gstNo + ", landLine=" + landLine + ", address1=" + address1 + ", address2="
				+ address2 + ", city=" + city + ", state=" + state + ", country=" + country + ", pincode=" + pincode
				+ ", accountName=" + accountName + ", accountNumber=" + accountNumber + ", branchName=" + branchName
				+ ", ifscCode=" + ifscCode + ", bankName=" + bankName + "]";
	}

	
}
