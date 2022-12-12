package com.autolib.helpdesk.HR.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "salary_entries")
public class SalaryEntries {

	public SalaryEntries() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "employee_id", nullable = false)
	private String employeeId;

	@Column(name = "employee_name")
	private String employeeName;

	@Column
	private String designation;

	@Column
	private String doj;

	@Column(name = "mode_of_payment")
	private String modeOfPayment;

	@Column(name = "pf_number")
	private String pfNumber = "";

	@Column(name = "pan_number")
	private String panNumber = "";

	@Column(name = "uan_number")
	private String uanNumber = "";

	@Column(name = "esic_number")
	private String esicNumber = "";

	@Column(name = "bank_name")
	private String bankName = "";

	@Column(name = "account_number")
	private String accountNumber = "";

	@Column
	private Double totalEarnings = 0.00;

	@Column
	private Double totalDeductions = 0.00;

	@Column
	private Double netPay = 0.00;

	@Column
	private String salaryDate;

	@Column
	private int noOfDaysLeave = 0;

	@Column
	private int noOfWorkingDays = 0;

	@Column(name = "salary_month")
	private String salaryMonth;

	@Column(name = "salary_year")
	private int salaryYear = 0;

	@Column(name = "salary_credited_date")
	@Temporal(TemporalType.DATE)
	private Date salaryCreditedDate;

	@Column
	private String filename;

	@Column
	private String status;

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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getSalaryMonth() {
		return salaryMonth;
	}

	public void setSalaryMonth(String salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public int getSalaryYear() {
		return salaryYear;
	}

	public void setSalaryYear(int salaryYear) {
		this.salaryYear = salaryYear;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
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

	public double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public double getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(Double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public Double getNetPay() {
		return netPay;
	}

	public void setNetPay(Double netPay) {
		this.netPay = netPay;
	}

	public String getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(String salaryDate) {
		this.salaryDate = salaryDate;
	}

	public int getNoOfWorkingDays() {
		return noOfWorkingDays;
	}

	public void setNoOfWorkingDays(int noOfWorkingDays) {
		this.noOfWorkingDays = noOfWorkingDays;
	}

	public int getNoOfDaysLeave() {
		return noOfDaysLeave;
	}

	public void setNoOfDaysLeave(int noOfDaysLeave) {
		this.noOfDaysLeave = noOfDaysLeave;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSalaryCreditedDate() {
		return salaryCreditedDate;
	}

	public void setSalaryCreditedDate(Date salaryCreditedDate) {
		this.salaryCreditedDate = salaryCreditedDate;
	}

	@Override
	public String toString() {
		return "SalaryEntries [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName
				+ ", designation=" + designation + ", doj=" + doj + ", modeOfPayment=" + modeOfPayment + ", pfNumber="
				+ pfNumber + ", panNumber=" + panNumber + ", uanNumber=" + uanNumber + ", esicNumber=" + esicNumber
				+ ", bankName=" + bankName + ", accountNumber=" + accountNumber + ", totalEarnings=" + totalEarnings
				+ ", totalDeductions=" + totalDeductions + ", netPay=" + netPay + ", salaryDate=" + salaryDate
				+ ", noOfDaysLeave=" + noOfDaysLeave + ", noOfWorkingDays=" + noOfWorkingDays + ", salaryMonth="
				+ salaryMonth + ", salaryYear=" + salaryYear + ", salaryCreditedDate=" + salaryCreditedDate
				+ ", filename=" + filename + ", status=" + status + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
