package com.autolib.helpdesk.HR.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salary_details")
public class SalaryDetails {

	public SalaryDetails() {
	}

	@Id
	@Column(name = "employee_id", nullable = false)
	private String employeeId = "";

	@Column(name = "pf_number")
	private String pfNumber = "";

	@Column(name = "pan_number")
	private String panNumber = "";

	@Column(name = "uan_number")
	private String uanNumber = "";

	@Column(name = "esic_number")
	private String esicNumber = "";

	@Column(name = "mode_of_payment")
	private String modeOfPayment = "";

	@Column(name = "bank_name")
	private String bankName = "";

	@Column(name = "account_number")
	private String accountNumber = "";

	@Column(name = "ifsc_code")
	private String ifscCode = "";

	@Column
	private Double totalEarnings = 0.00;

	@Column
	private Double lopPerDay = 0.00;

	@Column
	private Double totalDeductions = 0.00;

	@Column
	private Double netPay = 0.00;

	@Column
	private int casualLeave;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public double getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public Double getLopPerDay() {
		return lopPerDay;
	}

	public void setLopPerDay(Double lopPerDay) {
		this.lopPerDay = lopPerDay;
	}

	public int getCasualLeave() {
		return casualLeave;
	}

	public void setCasualLeave(int casualLeave) {
		this.casualLeave = casualLeave;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public String getEsicNumber() {
		return esicNumber;
	}

	public void setEsicNumber(String esicNumber) {
		this.esicNumber = esicNumber;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public Double getNetPay() {
		return netPay;
	}

	public void setNetPay(Double netPay) {
		this.netPay = netPay;
	}

	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public void setTotalDeductions(Double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	@Override
	public String toString() {
		return "SalaryDetails [employeeId=" + employeeId + ", pfNumber=" + pfNumber + ", panNumber=" + panNumber
				+ ", uanNumber=" + uanNumber + ", esicNumber=" + esicNumber + ", modeOfPayment=" + modeOfPayment
				+ ", bankName=" + bankName + ", accountNumber=" + accountNumber + ", ifscCode=" + ifscCode
				+ ", totalEarnings=" + totalEarnings + ", lopPerDay=" + lopPerDay + ", totalDeductions="
				+ totalDeductions + ", netPay=" + netPay + ", casualLeave=" + casualLeave + "]";
	}

}
