package com.autolib.helpdesk.Agents.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "info_details")
public class InfoDetails {

	public InfoDetails() {
	}

	public InfoDetails(int id, String cmpName, String cmpAddress, String gstNo, String bankDetails, String zipcode) {
		super();
		this.id = id;
		this.cmpName = cmpName;
		this.cmpAddress = cmpAddress;
		this.gstNo = gstNo;
		this.bankDetails = bankDetails;
		this.zipcode = zipcode;
	}

	@Id
	@Column
	private int id = 1;

	@Column(name = "cmp_name", nullable = false)
	private String cmpName = "";

	@Lob
	@Column(name = "cmp_address")
	private String cmpAddress = "";

	@Column(name = "cmp_phone")
	private String cmpPhone = "";

	@Column(name = "cmp_website")
	private String cmpWebsiteUrl = "";

	@Lob
	@Column(name = "mail_footer")
	private String mailFooter = "";

	@Column(name = "cmp_email")
	private String cmpEmail = "";

	@Column(name = "cmp_landline")
	private String cmpLandLine = "";

	@Column(name = "cmp_logo", columnDefinition = "LONGBLOB", updatable = false)
	private byte[] cmpLogo;

	@Column(name = "round_seal", columnDefinition = "LONGBLOB", updatable = false)
	private byte[] roundSeal;

	@Column(name = "full_seal", columnDefinition = "LONGBLOB", updatable = false)
	private byte[] fullSeal;

	@Column(name = "gst_no")
	private String gstNo = "";

	@Column(name = "instamojo_api_key")
	private String instamojoApiKey = "";

	@Column(name = "instamojo_auth_token")
	private String instamojoAuthToken = "";

	@Column(name = "instamojo_payment_url")
	private String instamojoPaymentURL = "";

	@Lob
	@Column(name = "terms")
	private String terms = "";

	@Lob
	@Column(name = "bank_details")
	private String bankDetails = "";

	@Lob
	@Column(name = "mail_content")
	private String mailContent;

	@Column
	private String zipcode;

	@Column(name = "send_email", nullable = false)
	private boolean sendEmail = false;

	@Column(name = "days_before_0", nullable = false)
	private boolean daysBefore0 = false;

	@Column(name = "days_before_1", nullable = false)
	private boolean daysBefore1 = false;

	@Column(name = "days_before_7", nullable = false)
	private boolean daysBefore7 = false;

	@Column(name = "days_before_15", nullable = false)
	private boolean daysBefore15 = false;

	@Column(name = "days_before_30", nullable = false)
	private boolean daysBefore30 = false;

	@Column(name = "days_after_1", nullable = false)
	private boolean daysAfter1 = false;

	@Column(name = "days_after_7", nullable = false)
	private boolean daysAfter7 = false;

	@Column(name = "days_after_15", nullable = false)
	private boolean daysAfter15 = false;

	@Column(name = "days_after_30", nullable = false)
	private boolean daysAfter30 = false;

	@Column(name = "reminder_email_cc", nullable = false)
	private String reminderEmailCC = "";

	@Column(name = "reminder_template", nullable = false)
	private String reminderTemplate = "";

	@Column(name = "signature_agent", nullable = false)
	private String signatureAgent = "";
	
	@Column(name = "background_image", columnDefinition = "LONGBLOB", updatable = false)
	private byte[] backGroundImage;

	@Transient
	public File getRoundSealAsFile() {

		try {
			if (this.roundSeal != null) {
				File tempFile = File.createTempFile("roundSeal", null, null);
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(this.roundSeal);
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

	@Transient
	public File getFullSealAsFile() {

		try {
			if (this.fullSeal != null) {
				File tempFile = File.createTempFile("fullSeal", null, null);
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(this.fullSeal);
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

	@Transient
	public File getLogoAsFile() {

		try {
			if (this.cmpLogo != null) {
				File tempFile = File.createTempFile("logo", null, null);
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(this.cmpLogo);
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
	
	
	@Transient
	public File getBackgroundImageAsFile() {

		try {
			if (this.backGroundImage != null) {
				File tempFile = File.createTempFile("background_image", null, null);
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(this.backGroundImage);
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

	public byte[] getBackGroundImage() {
		return backGroundImage;
	}

	public void setBackGroundImage(byte[] backGroundImage) {
		this.backGroundImage = backGroundImage;
	}

	@Transient
	public String getCompanyAddressHTML1() {
		String address = "";

		address = address + this.cmpAddress.replaceAll("\n", "<br>") + "<br>";

		address = address + this.cmpPhone + "<br>";

		address = address + this.cmpEmail + "<br>";

		address = address + "GSTIN : " + this.gstNo + "<br>";

		return address;
	}

	@Transient
	public String getCompanyAddressHTML2() {
		String address = "";

		address = address + this.cmpAddress.replaceAll("\n", "<br>") + "<br>";

		address = address + this.cmpPhone + "&nbsp;&nbsp;&nbsp;";

		address = address + this.cmpEmail + "&nbsp;&nbsp;&nbsp;";

		address = address + "GSTIN : " + this.gstNo;

		return address;
	}

	@Transient
	public String getCompanyAddress() {
		String address = "";

		address = address + this.cmpAddress.replaceAll("\n", "<br>");

		return address;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getCmpLogo() {
		return cmpLogo;
	}

	public void setCmpLogo(byte[] cmpLogo) {
		this.cmpLogo = cmpLogo;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	public String getCmpAddress() {
		return cmpAddress;
	}

	public void setCmpAddress(String cmpAddress) {
		this.cmpAddress = cmpAddress;
	}

	public String getCmpPhone() {
		return cmpPhone;
	}

	public void setCmpPhone(String cmpPhone) {
		this.cmpPhone = cmpPhone;
	}

	public String getCmpWebsiteUrl() {
		return cmpWebsiteUrl;
	}

	public void setCmpWebsiteUrl(String cmpWebsiteUrl) {
		this.cmpWebsiteUrl = cmpWebsiteUrl;
	}

	public String getCmpEmail() {
		return cmpEmail;
	}

	public void setCmpEmail(String cmpEmail) {
		this.cmpEmail = cmpEmail;
	}

	public String getCmpLandLine() {
		return cmpLandLine;
	}

	public void setCmpLandLine(String cmpLandLine) {
		this.cmpLandLine = cmpLandLine;
	}

	public byte[] getRoundSeal() {
		return roundSeal;
	}

	public void setRoundSeal(byte[] roundSeal) {
		this.roundSeal = roundSeal;
	}

	public byte[] getFullSeal() {
		return fullSeal;
	}

	public void setFullSeal(byte[] fullSeal) {
		this.fullSeal = fullSeal;
	}

	public boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public boolean isDaysBefore0() {
		return daysBefore0;
	}

	public void setDaysBefore0(boolean daysBefore0) {
		this.daysBefore0 = daysBefore0;
	}

	public boolean isDaysBefore1() {
		return daysBefore1;
	}

	public void setDaysBefore1(boolean daysBefore1) {
		this.daysBefore1 = daysBefore1;
	}

	public boolean isDaysBefore7() {
		return daysBefore7;
	}

	public void setDaysBefore7(boolean daysBefore7) {
		this.daysBefore7 = daysBefore7;
	}

	public boolean isDaysBefore15() {
		return daysBefore15;
	}

	public void setDaysBefore15(boolean daysBefore15) {
		this.daysBefore15 = daysBefore15;
	}

	public boolean isDaysBefore30() {
		return daysBefore30;
	}

	public void setDaysBefore30(boolean daysBefore30) {
		this.daysBefore30 = daysBefore30;
	}

	public boolean isDaysAfter1() {
		return daysAfter1;
	}

	public void setDaysAfter1(boolean daysAfter1) {
		this.daysAfter1 = daysAfter1;
	}

	public boolean isDaysAfter7() {
		return daysAfter7;
	}

	public void setDaysAfter7(boolean daysAfter7) {
		this.daysAfter7 = daysAfter7;
	}

	public boolean isDaysAfter15() {
		return daysAfter15;
	}

	public void setDaysAfter15(boolean daysAfter15) {
		this.daysAfter15 = daysAfter15;
	}

	public boolean isDaysAfter30() {
		return daysAfter30;
	}

	public void setDaysAfter30(boolean daysAfter30) {
		this.daysAfter30 = daysAfter30;
	}

	public String getReminderEmailCC() {
		return reminderEmailCC;
	}

	public void setReminderEmailCC(String reminderEmailCC) {
		this.reminderEmailCC = reminderEmailCC;
	}

	public String getReminderTemplate() {
		return reminderTemplate;
	}

	public void setReminderTemplate(String reminderTemplate) {
		this.reminderTemplate = reminderTemplate;
	}

	public String getSignatureAgent() {
		return signatureAgent;
	}

	public void setSignatureAgent(String signatureAgent) {
		this.signatureAgent = signatureAgent;
	}

	public String getMailFooter() {
		return mailFooter;
	}

	public void setMailFooter(String mailFooter) {
		this.mailFooter = mailFooter;
	}

	public String getInstamojoApiKey() {
		return instamojoApiKey;
	}

	public void setInstamojoApiKey(String instamojoApiKey) {
		this.instamojoApiKey = instamojoApiKey;
	}

	public String getInstamojoAuthToken() {
		return instamojoAuthToken;
	}

	public void setInstamojoAuthToken(String instamojoAuthToken) {
		this.instamojoAuthToken = instamojoAuthToken;
	}

	public String getInstamojoPaymentURL() {
		return instamojoPaymentURL;
	}

	public void setInstamojoPaymentURL(String instamojoPaymentURL) {
		this.instamojoPaymentURL = instamojoPaymentURL;
	}

	@Transient
	public String getBankDetailsAsHTML() {
		String _bankDetails = "";
		try {
			if (this.bankDetails != null) {
				_bankDetails = this.bankDetails.replaceAll("\n", "<br>");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return _bankDetails;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "InfoDetails [id=" + id + ", cmpName=" + cmpName + ", cmpAddress=" + cmpAddress + ", cmpPhone="
				+ cmpPhone + ", cmpWebsiteUrl=" + cmpWebsiteUrl + ", mailFooter=" + mailFooter + ", cmpEmail="
				+ cmpEmail + ", cmpLandLine=" + cmpLandLine + ", gstNo=" + gstNo
				+ ", instamojoApiKey=" + instamojoApiKey + ", instamojoAuthToken=" + instamojoAuthToken
				+ ", instamojoPaymentURL=" + instamojoPaymentURL + ", terms=" + terms + ", bankDetails=" + bankDetails
				+ ", mailContent=" + mailContent + ", zipcode=" + zipcode + ", sendEmail=" + sendEmail
				+ ", daysBefore0=" + daysBefore0 + ", daysBefore1=" + daysBefore1 + ", daysBefore7=" + daysBefore7
				+ ", daysBefore15=" + daysBefore15 + ", daysBefore30=" + daysBefore30 + ", daysAfter1=" + daysAfter1
				+ ", daysAfter7=" + daysAfter7 + ", daysAfter15=" + daysAfter15 + ", daysAfter30=" + daysAfter30
				+ ", reminderEmailCC=" + reminderEmailCC + ", reminderTemplate=" + reminderTemplate
				+ ", signatureAgent=" + signatureAgent + ", getCompanyAddressHTML1()="
				+ getCompanyAddressHTML1() + ", getCompanyAddressHTML2()=" + getCompanyAddressHTML2()
				+ ", getCompanyAddress()=" + getCompanyAddress() + ", getTerms()=" + getTerms() + ", getBankDetails()="
				+ getBankDetails() + ", getMailContent()=" + getMailContent() + ", getGstNo()=" + getGstNo()
				+ ", getId()=" + getId() + ", getCmpLogo()=" + Arrays.toString(getCmpLogo()) + ", getCmpName()="
				+ getCmpName() + ", getCmpAddress()=" + getCmpAddress() + ", getCmpPhone()=" + getCmpPhone()
				+ ", getCmpWebsiteUrl()=" + getCmpWebsiteUrl() + ", getCmpEmail()=" + getCmpEmail()
				+ ", getCmpLandLine()=" + getCmpLandLine() + ", getRoundSeal()=" + Arrays.toString(getRoundSeal())
				+ ", getFullSeal()=" + Arrays.toString(getFullSeal()) + ", isSendEmail()=" + isSendEmail()
				+ ", isDaysBefore0()=" + isDaysBefore0() + ", isDaysBefore1()=" + isDaysBefore1() + ", isDaysBefore7()="
				+ isDaysBefore7() + ", isDaysBefore15()=" + isDaysBefore15() + ", isDaysBefore30()=" + isDaysBefore30()
				+ ", isDaysAfter1()=" + isDaysAfter1() + ", isDaysAfter7()=" + isDaysAfter7() + ", isDaysAfter15()="
				+ isDaysAfter15() + ", isDaysAfter30()=" + isDaysAfter30() + ", getReminderEmailCC()="
				+ getReminderEmailCC() + ", getReminderTemplate()=" + getReminderTemplate() + ", getSignatureAgent()="
				+ getSignatureAgent() + ", getMailFooter()=" + getMailFooter() + ", getInstamojoApiKey()="
				+ getInstamojoApiKey() + ", getInstamojoAuthToken()=" + getInstamojoAuthToken()
				+ ", getInstamojoPaymentURL()=" + getInstamojoPaymentURL() + ", getBankDetailsAsHTML()="
				+ getBankDetailsAsHTML() + ", getZipcode()=" + getZipcode() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	

}
