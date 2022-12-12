package com.autolib.helpdesk.HR.entity;

import java.util.Date;
import java.util.List;

public class SalaryDetailsRequest {

	private SalaryDetails salaryDetail;

	private List<SalaryDetailProperty> salaryDetailProperty;

	private List<SalaryDetails> salaryDetails;

	private Boolean isSalaryCredited;
	private String salaryMonth;
	private int salaryYear;
	private Date salaryCreditedDate;

	public Date getSalaryCreditedDate() {
		return salaryCreditedDate;
	}

	public void setSalaryCreditedDate(Date salaryCreditedDate) {
		this.salaryCreditedDate = salaryCreditedDate;
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

	public Boolean getIsSalaryCredited() {
		return isSalaryCredited;
	}

	public void setIsSalaryCredited(Boolean isSalaryCredited) {
		this.isSalaryCredited = isSalaryCredited;
	}

	public List<SalaryDetails> getSalaryDetails() {
		return salaryDetails;
	}

	public void setSalaryDetails(List<SalaryDetails> salaryDetails) {
		this.salaryDetails = salaryDetails;
	}

	public SalaryDetails getSalaryDetail() {
		return salaryDetail;
	}

	public void setSalaryDetail(SalaryDetails salaryDetail) {
		this.salaryDetail = salaryDetail;
	}

	public List<SalaryDetailProperty> getSalaryDetailProperty() {
		return salaryDetailProperty;
	}

	public void setSalaryDetailProperty(List<SalaryDetailProperty> salaryDetailProperty) {
		this.salaryDetailProperty = salaryDetailProperty;
	}

	@Override
	public String toString() {
		return "SalaryDetailsRequest [salaryDetail=" + salaryDetail + ", salaryDetailProperty=" + salaryDetailProperty
				+ ", salaryDetails=" + salaryDetails + ", isSalaryCredited=" + isSalaryCredited + ", salaryMonth="
				+ salaryMonth + ", salaryYear=" + salaryYear + "]";
	}

}
