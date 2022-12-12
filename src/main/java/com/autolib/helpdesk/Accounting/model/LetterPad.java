package com.autolib.helpdesk.Accounting.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="letterpad" ,indexes = { @Index(name = "institute_id_idx", columnList = "institute_id")})
public class LetterPad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	
	/*
	 * @Lob
	 * 
	 * @Column(name = "to_address") private String toAddress ="";
	 */
	
	
	@Lob
	@Column(name = "content")
	private String content = "";
	
	@Lob
	@Column(name = "subject")
	private String subject="";
	
	
	@Lob
	@Column(name = "regard_text")
	private String regardText="";
	

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date letterPadDate;
	
	
	@Column(name ="file_name")
	private String fileName;
	
	@Column(name ="file_size")
	private long fileSize;
	
	@Column(name="file_type" ,length=128)
	private String fileType;
	
	
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;
	
	
	@Column(name = "billing_to")
	private String billingTo = "";
	@Column(name = "billing_street1")
	private String billingStreet1 = "";
	

	public String getBillingTo() {
		return billingTo;
	}

	public void setBillingTo(String billingTo) {
		this.billingTo = billingTo;
	}

	public String getBillingStreet1() {
		return billingStreet1;
	}

	public void setBillingStreet1(String billingStreet1) {
		this.billingStreet1 = billingStreet1;
	}

	public String getBillingStreet2() {
		return billingStreet2;
	}

	public void setBillingStreet2(String billingStreet2) {
		this.billingStreet2 = billingStreet2;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	public String getBillingZIPCode() {
		return billingZIPCode;
	}

	public void setBillingZIPCode(String billingZIPCode) {
		this.billingZIPCode = billingZIPCode;
	}

	@Column(name = "billing_street2")
	private String billingStreet2 = "";
	@Column(name = "billing_city")
	private String billingCity = "";
	@Column(name = "billing_state")
	private String billingState = "";
	@Column(name = "billing_country")
	private String billingCountry = "";
	@Column(name = "billing_zipcode")
	private String billingZIPCode = "";
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRegardText() {
		return regardText;
	}

	public void setRegardText(String regardText) {
		this.regardText = regardText;
	}

	public Date getLetterPadDate() {
		return letterPadDate;
	}

	public void setLetterPadDate(Date letterPadDate) {
		this.letterPadDate = letterPadDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	@Override
	public String toString() {
		return "LetterPad [id=" + id + ", content=" + content + ", subject=" + subject + ", regardText=" + regardText
				+ ", letterPadDate=" + letterPadDate + ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileType=" + fileType + ", institute=" + institute + ", billingTo=" + billingTo
				+ ", billingStreet1=" + billingStreet1 + ", billingStreet2=" + billingStreet2 + ", billingCity="
				+ billingCity + ", billingState=" + billingState + ", billingCountry=" + billingCountry
				+ ", billingZIPCode=" + billingZIPCode + "]";
	}

	
	


		
}
