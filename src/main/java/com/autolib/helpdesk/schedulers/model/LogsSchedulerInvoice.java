/**
 * 
 */
package com.autolib.helpdesk.schedulers.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "logs_scheduler_invoice")
public class LogsSchedulerInvoice {

	/**
	 * 
	 */
	public LogsSchedulerInvoice() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private int dealId;
	@Column
	private String mailTo;
	@Column
	private String mailList;
	@Column
	private String mailSubject;
	@Column
	private String invoiceURL;

	@Column(name = "create_deal", nullable = false)
	private boolean createDeal = false;
	@Column(name = "create_proforma_invoice", nullable = false)
	private boolean createProformaInvoice = false;
	@Column(name = "generate_proforma_invoice", nullable = false)
	private boolean generateProformaInvoice = false;
	@Column(name = "create_deal_email", nullable = false)
	private boolean createDealEmail = false;
	@Column(name = "send_email", nullable = false)
	private boolean sendEmail = false;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@Lob
	@Column
	private String exception;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailList() {
		return mailList;
	}

	public void setMailList(String mailList) {
		this.mailList = mailList;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getInvoiceURL() {
		return invoiceURL;
	}

	public void setInvoiceURL(String invoiceURL) {
		this.invoiceURL = invoiceURL;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public boolean getCreateDeal() {
		return createDeal;
	}

	public void setCreateDeal(boolean createDeal) {
		this.createDeal = createDeal;
	}

	public boolean getCreateProformaInvoice() {
		return createProformaInvoice;
	}

	public void setCreateProformaInvoice(boolean createProformaInvoice) {
		this.createProformaInvoice = createProformaInvoice;
	}

	public boolean getGenerateProformaInvoice() {
		return generateProformaInvoice;
	}

	public void setGenerateProformaInvoice(boolean generateProformaInvoice) {
		this.generateProformaInvoice = generateProformaInvoice;
	}

	public boolean getCreateDealEmail() {
		return createDealEmail;
	}

	public void setCreateDealEmail(boolean createDealEmail) {
		this.createDealEmail = createDealEmail;
	}

	public boolean getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "LogsSchedulerInvoice [id=" + id + ", dealId=" + dealId + ", mailTo=" + mailTo + ", mailList=" + mailList
				+ ", mailSubject=" + mailSubject + ", invoiceURL=" + invoiceURL + ", createDeal=" + createDeal
				+ ", createProformaInvoice=" + createProformaInvoice + ", generateProformaInvoice="
				+ generateProformaInvoice + ", createDealEmail=" + createDealEmail + ", sendEmail=" + sendEmail
				+ ", institute=" + institute + ", exception=" + exception + ", createddatetime=" + createddatetime
				+ "]";
	}

}
