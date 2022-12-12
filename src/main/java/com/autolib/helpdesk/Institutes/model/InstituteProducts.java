package com.autolib.helpdesk.Institutes.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;

@Entity
@Table(name = "institute_products", uniqueConstraints = {
		@UniqueConstraint(name = "uc_instid_product", columnNames = { "institute_id", "product_id" }) }, indexes = {
				@Index(name = "institute_id_idx", columnList = "institute_id"),
				@Index(name = "product_id_idx", columnList = "product_id"),
				@Index(name = "amc_expiry_date_idx", columnList = "amc_expiry_date"),
				@Index(name = "last_amc_paid_date_idx", columnList = "last_amc_paid_date"),
				@Index(name = "current_service_under_idx", columnList = "current_service_under") })
public class InstituteProducts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	private Institute institute;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	private Product product;

	@Column(name = "amc_amount")
	private Double amcAmount;

	@Column(name = "last_amc_paid_date")
	@Temporal(TemporalType.DATE)
	private Date lastAMCPaidDate;

	@Column(name = "amc_expiry_date")
	@Temporal(TemporalType.DATE)
	private Date amcExpiryDate;

	@Column(name = "quantity", nullable = false, columnDefinition = "int(11) default 1")
	private int quantity;

	@Column(name = "current_service_under")
	@Enumerated(EnumType.STRING)
	private ServiceUnder currentServiceUnder;

	@Column(name = "last_amc_reminder_sent_date_time")
	private Date lastAMCReminderSentDateTime;

	@Column(name = "last_amc_reminder_deal_id")
	private String lastAMCReminderDealId;

	@Transient
	private long daysDiff = 0;

	@Transient
	private String expiresIn = "";

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public InstituteProducts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	public Double getAmcAmount() {
		return amcAmount;
	}

	public void setAmcAmount(Double amcAmount) {
		this.amcAmount = amcAmount;
	}

	public Date getLastAMCPaidDate() {
		return lastAMCPaidDate;
	}

	public void setLastAMCPaidDate(Date lastAMCPaidDate) {
		this.lastAMCPaidDate = lastAMCPaidDate;
	}

	public Date getAmcExpiryDate() {
		return amcExpiryDate;
	}

	public void setAmcExpiryDate(Date amcExpiryDate) {
		this.amcExpiryDate = amcExpiryDate;
	}

	public ServiceUnder getCurrentServiceUnder() {
		return currentServiceUnder;
	}

	public void setCurrentServiceUnder(ServiceUnder currentServiceUnder) {
		this.currentServiceUnder = currentServiceUnder;
	}

	public Date getLastAMCReminderSentDateTime() {
		return lastAMCReminderSentDateTime;
	}

	public void setLastAMCReminderSentDateTime(Date lastAMCReminderSentDateTime) {
		this.lastAMCReminderSentDateTime = lastAMCReminderSentDateTime;
	}

	public String getLastAMCReminderDealId() {
		return lastAMCReminderDealId;
	}

	public void setLastAMCReminderDealId(String lastAMCReminderDealId) {
		this.lastAMCReminderDealId = lastAMCReminderDealId;
	}

	public long getDaysDiff() {
		return daysDiff;
	}

	public void setDaysDiff(long daysDiff) {
		this.daysDiff = daysDiff;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "InstituteProducts [id=" + id + ", institute=" + institute + ", product=" + product + ", amcAmount="
				+ amcAmount + ", lastAMCPaidDate=" + lastAMCPaidDate + ", amcExpiryDate=" + amcExpiryDate
				+ ", quantity=" + quantity + ", currentServiceUnder=" + currentServiceUnder
				+ ", lastAMCReminderSentDateTime=" + lastAMCReminderSentDateTime + ", lastAMCReminderDealId="
				+ lastAMCReminderDealId + ", daysDiff=" + daysDiff + ", expiresIn=" + expiresIn + ", createddatetime="
				+ createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
