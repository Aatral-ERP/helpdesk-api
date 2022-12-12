package com.autolib.helpdesk.Tickets.model;

import com.autolib.helpdesk.Institutes.model.Institute;

public class CallReportRequest {
	
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Institute institute;
	
	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public CallReport getCallReport() {
		return callReport;
	}

	public void setCallReport(CallReport callReport) {
		this.callReport = callReport;
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

	private CallReport callReport;
	
	private String templateName = "";
	
	private boolean addRoundSeal = false;
	
	private boolean addFullSeal = false;
	
	private boolean addSign = false;
	
	private String signatureBy = "";
	
	private String designation = "";
	
	private String fileAsBase64 = "";
	
	private String filename = "";
	
	private String receiptContent = "";

	@Override
	public String toString() {
		return "CallReportRequest [id=" + id + ", institute=" + institute + ", callReport=" + callReport
				+ ", templateName=" + templateName + ", addRoundSeal=" + addRoundSeal + ", addFullSeal=" + addFullSeal
				+ ", addSign=" + addSign + ", signatureBy=" + signatureBy + ", designation=" + designation
				+ ", fileAsBase64=" + fileAsBase64 + ", filename=" + filename + ", receiptContent=" + receiptContent
				+ "]";
	}

	

}
