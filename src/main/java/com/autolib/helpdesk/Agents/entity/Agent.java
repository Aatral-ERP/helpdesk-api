package com.autolib.helpdesk.Agents.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "agents", indexes = { @Index(name = "email_id_idx", columnList = "email_id") })
public class Agent {

	public Agent() {
	}

	public Agent(String employeeId, String firstName, String lastName, String emailId) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	public Agent(String employeeId, String firstName, String lastName, String emailId, boolean isBlocked,
			String workingStatus, String photoFileName, String designation) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.isBlocked = isBlocked;
		this.workingStatus = workingStatus;
		this.photoFileName = photoFileName;
		this.designation = designation;
	}

	@Id
	@Column(name = "employee_id", nullable = false, updatable = false, unique = true)
	private String employeeId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email_id", nullable = false, unique = true)
	private String emailId;

	@Column(name = "password", nullable = false, updatable = false)
	private String password;

	@Column(name = "category")
	private String category;

	@Column(name = "agent_type", columnDefinition = "SMALLINT default 2", nullable = false)
	private int agentType;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "leave_master_id", columnDefinition = "SMALLINT default 1", nullable = false)
	private int leaveMasterId;

	@JsonProperty
	@Column(name = "is_blocked")
	private boolean isBlocked;

	@Column(name = "designation")
	private String designation;

	@Column(name = "full_address", length = 512)
	private String fullAddress;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(columnDefinition = "LONGBLOB", updatable = false)
	private byte[] photo;

	@Column(columnDefinition = "LONGBLOB", updatable = false)
	private byte[] signature;

	@Column
	private String photoFileName;

	@Column
	private String signatureFileName;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "experience_year", columnDefinition = "SMALLINT default 0", nullable = false)
	private int experienceYear;

	@Column(name = "experience_month", columnDefinition = "SMALLINT default 0", nullable = false)
	private int experienceMonth;

	@Column(name = "joining_date")
	@Temporal(TemporalType.DATE)
	private Date dateOfJoining;

	@Column(name = "account_details")
	private String accountDetails;

	@Column(name = "working_status")
	private String workingStatus;

	@Column(name = "notes")
	private String notes;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getExperienceYear() {
		return experienceYear;
	}

	public void setExperienceYear(int experienceYear) {
		this.experienceYear = experienceYear;
	}

	public int getExperienceMonth() {
		return experienceMonth;
	}

	public void setExperienceMonth(int experienceMonth) {
		this.experienceMonth = experienceMonth;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLeaveMasterId() {
		return leaveMasterId;
	}

	public void setLeaveMasterId(int leaveMasterId) {
		this.leaveMasterId = leaveMasterId;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAgentType() {
		return agentType;
	}

	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getSignatureFileName() {
		return signatureFileName;
	}

	public void setSignatureFileName(String signatureFileName) {
		this.signatureFileName = signatureFileName;
	}

	@Transient
	public File getSignatureAsFile() {

		try {
			if (this.signature != null) {
				File tempFile = File.createTempFile("signature", null, null);
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(this.signature);
				fos.close();
				return tempFile;
			} else
				return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String toString() {
		return "Agent [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId="
				+ emailId + ", password=" + password + ", agentType=" + agentType + ", roleName=" + roleName
				+ ", isBlocked=" + isBlocked + ", designation=" + designation + ", fullAddress=" + fullAddress
				+ ", gender=" + gender + ", phone=" + phone + ", experienceYear=" + experienceYear
				+ ", experienceMonth=" + experienceMonth + ", dateOfJoining=" + dateOfJoining + ", accountDetails="
				+ accountDetails + ", workingStatus=" + workingStatus + ", notes=" + notes + "]";
	}

}
