package com.autolib.helpdesk.HR.entity;

import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

public class SalaryDetailsResponse {

	private SalaryDetails salaryDetail;
	private Agent agent;

	public SalaryDetailsResponse(SalaryDetails salaryDetail, Agent agent) {
		super();
		this.salaryDetail = salaryDetail;
		this.agent = agent;
	}

	public SalaryDetailsResponse(SalaryDetails salaryDetail) {
		super();
		this.salaryDetail = salaryDetail;
	}

	private List<SalaryDetailProperty> salaryDetailProperty;

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
				+ "]";
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
