package com.autolib.helpdesk.HR.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "salary_entries_properties", indexes = { @Index(name = "employee_id_idx", columnList = "employee_id") })
public class SalaryEntriesProperty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "employee_id", nullable = false)
	private String employeeId;

	@Column(name = "salary_entry_id", nullable = false)
	private int salaryEntryId;

	@Column(name = "property")
	private String property;

	@Column(name = "amount", columnDefinition = "Decimal(10,2) default '0.00'")
	private Double amount = 0.00;

	@Column(name = "property_type")
	private String propertyType;

	public SalaryEntriesProperty() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalaryEntriesProperty(SalaryDetailProperty sdp) {
		this.employeeId = sdp.getEmployeeId();
		this.property = sdp.getProperty();
		this.amount = sdp.getAmount();
		this.propertyType = sdp.getPropertyType();
	}

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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public int getSalaryEntryId() {
		return salaryEntryId;
	}

	public void setSalaryEntryId(int salaryEntryId) {
		this.salaryEntryId = salaryEntryId;
	}

	@Override
	public String toString() {
		return "SalaryEntriesProperty [id=" + id + ", employeeId=" + employeeId + ", salaryEntryId=" + salaryEntryId
				+ ", property=" + property + ", amount=" + amount + ", propertyType=" + propertyType + "]";
	}

}
