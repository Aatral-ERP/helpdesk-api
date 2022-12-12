package com.autolib.helpdesk.Accounting.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.autolib.helpdesk.Institutes.model.Institute;

public class LetterpadRequest {

	private int id;

	private List<Institute> institutes ;

	private String subject = "";

	private Date latterpadDateFrom = null;

	private Date latterpadDateTo = null;

	private LetterPad letterpad;

	private String templateName = "";

	private String signatureBy = "";

	private String designation = "";

	private String fileAsBase64 = "";

	private String filename = "";

	private String receiptContent = "";

	private boolean addRoundSeal = false;

	private boolean addFullSeal = false;

	private boolean addLetterHead = false;

	private boolean addLogo = false;

	private boolean addSign = false;

	private String header = "";


	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = "Letterpad";
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LetterPad getLetterpad() {
		return letterpad;
	}

	public void setLetterpad(LetterPad letterpad) {
		this.letterpad = letterpad;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isAddRoundSeal() {
		return addRoundSeal;
	}

	public void setAddRoundSeal(boolean addRoundSeal) {
		this.addRoundSeal = addRoundSeal;
	}

	public boolean isAddFullSeal() {
		return addFullSeal;
	}

	public void setAddFullSeal(boolean addFullSeal) {
		this.addFullSeal = addFullSeal;
	}

	public boolean isAddSign() {
		return addSign;
	}

	public void setAddSign(boolean addSign) {
		this.addSign = addSign;
	}

	public String getSignatureBy() {
		return signatureBy;
	}

	public void setSignatureBy(String signatureBy) {
		this.signatureBy = signatureBy;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFileAsBase64() {
		return fileAsBase64;
	}

	public void setFileAsBase64(String fileAsBase64) {
		this.fileAsBase64 = fileAsBase64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getReceiptContent() {
		return receiptContent;
	}

	public void setReceiptContent(String receiptContent) {
		this.receiptContent = receiptContent;
	}


	public boolean isAddLetterHead() {
		return addLetterHead;
	}

	public void setAddLetterHead(boolean addLetterHead) {
		this.addLetterHead = addLetterHead;
	}

	public boolean isAddLogo() {
		return addLogo;
	}

	public void setAddLogo(boolean addLogo) {
		this.addLogo = addLogo;
	}

	public Date getLatterpadDateFrom() {
		return latterpadDateFrom;
	}

	public void setLatterpadDateFrom(Date latterpadDateFrom) {
		this.latterpadDateFrom = latterpadDateFrom;
	}

	public Date getLatterpadDateTo() {
		return latterpadDateTo;
	}

	public void setLatterpadDateTo(Date latterpadDateTo) {
		this.latterpadDateTo = latterpadDateTo;
	}

	@Override
	public String toString() {
		return "LetterpadRequest [id=" + id + ", institutes=" + institutes + ", subject=" + subject
				+ ", latterpadDateFrom=" + latterpadDateFrom + ", latterpadDateTo=" + latterpadDateTo + ", letterpad="
				+ letterpad + ", templateName=" + templateName + ", signatureBy=" + signatureBy + ", designation="
				+ designation + ", fileAsBase64=" + fileAsBase64 + ", filename=" + filename + ", receiptContent="
				+ receiptContent + ", addRoundSeal=" + addRoundSeal + ", addFullSeal=" + addFullSeal
				+ ", addLetterHead=" + addLetterHead + ", addLogo=" + addLogo + ", addSign=" + addSign + ", header="
				+ header + "]";
	}

	

}
