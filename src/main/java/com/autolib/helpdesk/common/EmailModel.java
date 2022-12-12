package com.autolib.helpdesk.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailModel {

	public EmailModel(String senderConf) {
		this.senderConf = senderConf;
	}

	private String mailFrom = "", mailTo = "", mailSub = "", mailText = "", emailUid = "";
	private String replyTo = "", content_path = "";
	private String otp = "";

	private String[] mailList = new String[100];
	private String phone_number = "";
	private String fromName = "";

	private String senderConf = "";

	private List<String> contentPaths = new ArrayList<>();

	public String getSenderConf() {
		return senderConf;
	}

	public void setSenderConf(String senderConf) {
		this.senderConf = senderConf;
	}

	public List<String> getContentPaths() {
		return contentPaths;
	}

	public void setContentPaths(List<String> contentPaths) {
		this.contentPaths = contentPaths;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailSub() {
		return mailSub;
	}

	public void setMailSub(String mailSub) {
		this.mailSub = mailSub;
	}

	public String getMailText() {
		return mailText;
	}

	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	public String getEmailUid() {
		return emailUid;
	}

	public void setEmailUid(String emailUid) {
		this.emailUid = emailUid;
	}

	public String[] getMailList() {
		return mailList;
	}

	public void setMailList(String[] mailList) {
		this.mailList = mailList;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getContent_path() {
		return content_path;
	}

	public void setContent_path(String content_path) {
		this.content_path = content_path;
	}

	@Override
	public String toString() {
		return "EmailModel [mailFrom=" + mailFrom + ", mailTo=" + mailTo + ", mailSub=" + mailSub + ", mailText="
				+ mailText + ", emailUid=" + emailUid + ", replyTo=" + replyTo + ", content_path=" + content_path
				+ ", otp=" + otp + ", mailList=" + Arrays.toString(mailList) + ", phone_number=" + phone_number
				+ ", fromName=" + fromName + ", senderConf=" + senderConf + ", contentPaths=" + contentPaths + "]";
	}

}
