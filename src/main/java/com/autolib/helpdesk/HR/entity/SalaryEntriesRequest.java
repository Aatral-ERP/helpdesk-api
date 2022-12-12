package com.autolib.helpdesk.HR.entity;

import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;

public class SalaryEntriesRequest {

	private SalaryEntries salaryEntry;

	private List<SalaryEntriesProperty> salaryEntriesProperties;

	private List<SalaryEntries> salaryEntries;

	private List<Agent> agents;
	private String salaryMonth;
	private String salaryYear;
	private String status;

	public SalaryEntriesRequest(SalaryEntries salaryEntry, List<SalaryEntriesProperty> salaryEntriesProperties) {
		super();
		this.salaryEntry = salaryEntry;
		this.salaryEntriesProperties = salaryEntriesProperties;
	}

	public List<SalaryEntries> getSalaryEntries() {
		return salaryEntries;
	}

	public void setSalaryEntries(List<SalaryEntries> salaryEntries) {
		this.salaryEntries = salaryEntries;
	}

	public SalaryEntries getSalaryEntry() {
		return salaryEntry;
	}

	public void setSalaryEntry(SalaryEntries salaryEntry) {
		this.salaryEntry = salaryEntry;
	}

	public List<SalaryEntriesProperty> getSalaryEntriesProperties() {
		return salaryEntriesProperties;
	}

	public void setSalaryEntriesProperties(List<SalaryEntriesProperty> salaryEntriesProperties) {
		this.salaryEntriesProperties = salaryEntriesProperties;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public String getSalaryMonth() {
		return salaryMonth;
	}

	public void setSalaryMonth(String salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public String getSalaryYear() {
		return salaryYear;
	}

	public void setSalaryYear(String salaryYear) {
		this.salaryYear = salaryYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SalaryEntriesRequest [salaryEntry=" + salaryEntry + ", salaryEntriesProperties="
				+ salaryEntriesProperties + ", salaryEntries=" + salaryEntries + ", agents=" + agents + ", salaryMonth="
				+ salaryMonth + ", salaryYear=" + salaryYear + ", status=" + status + "]";
	}

}
