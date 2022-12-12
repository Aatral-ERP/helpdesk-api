/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deals", indexes = { @Index(name = "institute_id_idx", columnList = "institute_id"),
		@Index(name = "quote_no_idx", columnList = "quote_no"),
		@Index(name = "invoice_no_idx", columnList = "invoice_no"),
		@Index(name = "sales_order_no_idx", columnList = "sales_order_no"),
		@Index(name = "purchase_order_no_idx", columnList = "purchase_order_no"),
		@Index(name = "proforma_invoice_no_idx", columnList = "proforma_invoice_no"),
		@Index(name = "createddatetime_idx", columnList = "createddatetime"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "created_by_idx", columnList = "created_by") })
public class Deal {

	/**
	 * 
	 */
	public Deal() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_type", nullable = false, columnDefinition = "varchar(45) default 'Sales'")
	private String dealType = "";

	@Column(name = "quote_no")
	private String quoteNo = "";

	@Column(name = "proforma_invoice_no")
	private String proformaInvoiceNo = "";

	@Column(name = "invoice_no")
	private String invoiceNo = "";

	@Column(name = "sales_order_no")
	private String salesOrderNo = "";

	@Column(name = "purchase_order_no")
	private String purchaseOrderNo = "";

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@Column(name = "gst_type", nullable = false)
	private String gstType;

	@Column(name = "amc_from_date")
	@Temporal(TemporalType.DATE)
	private Date amcFromDate;

	@Column(name = "amc_to_date")
	@Temporal(TemporalType.DATE)
	private Date amcToDate;

	@Column(name = "billing_to")
	private String billingTo = "";
	@Column(name = "billing_street1")
	private String billingStreet1 = "";
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

	@Column(name = "shipping_to")
	private String shippingTo = "";
	@Column(name = "shipping_street1")
	private String shippingStreet1 = "";
	@Column(name = "shipping_street2")
	private String shippingStreet2 = "";
	@Column(name = "shipping_city")
	private String shippingCity = "";
	@Column(name = "shipping_state")
	private String shippingState = "";
	@Column(name = "shipping_country")
	private String shippingCountry = "";
	@Column(name = "shipping_zipcode")
	private String shippingZIPCode = "";

	@Column(name = "sub_total")
	private Double subTotal = 0.00;
	@Column(name = "discount")
	private Double discount = 0.00;
	@Column(name = "tax")
	private Double tax = 0.00;
	@Column(name = "adjustment")
	private Double adjustment = 0.00;
	@Column(name = "grand_total")
	private Double grandTotal = 0.00;

	@Column(name = "no_of_products", columnDefinition = "SMALLINT")
	private int noOfProducts;

	@Column(name = "created_by")
	private String createdBy = "";

	@Column(name = "modified_by")
	private String modifiedBy = "";

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public String getProformaInvoiceNo() {
		return proformaInvoiceNo;
	}

	public void setProformaInvoiceNo(String proformaInvoiceNo) {
		this.proformaInvoiceNo = proformaInvoiceNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

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

	public String getShippingTo() {
		return shippingTo;
	}

	public void setShippingTo(String shippingTo) {
		this.shippingTo = shippingTo;
	}

	public String getShippingStreet1() {
		return shippingStreet1;
	}

	public void setShippingStreet1(String shippingStreet1) {
		this.shippingStreet1 = shippingStreet1;
	}

	public String getShippingStreet2() {
		return shippingStreet2;
	}

	public void setShippingStreet2(String shippingStreet2) {
		this.shippingStreet2 = shippingStreet2;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingZIPCode() {
		return shippingZIPCode;
	}

	public void setShippingZIPCode(String shippingZIPCode) {
		this.shippingZIPCode = shippingZIPCode;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public int getNoOfProducts() {
		return noOfProducts;
	}

	public void setNoOfProducts(int noOfProducts) {
		this.noOfProducts = noOfProducts;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public Date getAmcFromDate() {
		return amcFromDate;
	}

	public void setAmcFromDate(Date amcFromDate) {
		this.amcFromDate = amcFromDate;
	}

	public Date getAmcToDate() {
		return amcToDate;
	}

	public void setAmcToDate(Date amcToDate) {
		this.amcToDate = amcToDate;
	}

	@Override
	public String toString() {
		return "Deal [id=" + id + ", dealType=" + dealType + ", quoteNo=" + quoteNo + ", proformaInvoiceNo="
				+ proformaInvoiceNo + ", invoiceNo=" + invoiceNo + ", salesOrderNo=" + salesOrderNo
				+ ", purchaseOrderNo=" + purchaseOrderNo + ", institute=" + institute + ", gstType=" + gstType
				+ ", amcFromDate=" + amcFromDate + ", amcToDate=" + amcToDate + ", billingTo=" + billingTo
				+ ", billingStreet1=" + billingStreet1 + ", billingStreet2=" + billingStreet2 + ", billingCity="
				+ billingCity + ", billingState=" + billingState + ", billingCountry=" + billingCountry
				+ ", billingZIPCode=" + billingZIPCode + ", shippingTo=" + shippingTo + ", shippingStreet1="
				+ shippingStreet1 + ", shippingStreet2=" + shippingStreet2 + ", shippingCity=" + shippingCity
				+ ", shippingState=" + shippingState + ", shippingCountry=" + shippingCountry + ", shippingZIPCode="
				+ shippingZIPCode + ", subTotal=" + subTotal + ", discount=" + discount + ", tax=" + tax
				+ ", adjustment=" + adjustment + ", grandTotal=" + grandTotal + ", noOfProducts=" + noOfProducts
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
