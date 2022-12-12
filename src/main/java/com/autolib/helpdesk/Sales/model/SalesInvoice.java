package com.autolib.helpdesk.Sales.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "invoices")
public class SalesInvoice {
	public SalesInvoice() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "invoice_no", length = 128)
	private String invoiceNo;
	@Column(name = "reference_no")
	private String referenceNo;
	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	@Column(name = "gst_amount")
	private int gstAmount;
	@Column(name = "gst_percentage")
	private int gstPercentage;
	@Column(name = "total_amount")
	private Double totalAmount;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;
	
	
	
	@Column(name = "billing_to")
	private String billingTo = "";
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

	@Column(name = "subtotal")
	private Double subtotal = 0.00;
	
	@Column(name="product_name")
	private String  productName;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIgst() {
		return igst;
	}

	public void setIgst(int igst) {
		this.igst = igst;
	}

	public int getCgst() {
		return cgst;
	}

	public void setCgst(int cgst) {
		this.cgst = cgst;
	}

	public int getSgst() {
		return sgst;
	}

	public void setSgst(int sgst) {
		this.sgst = sgst;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name="description")
	private String  description;
	
	@Column
	private int igst;
	
	@Column
	private int cgst;
	
	@Column
	private int sgst;
	
	@Column
	private Double price = 0.00;
	
	
	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
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

	public Double getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(Double grandtotal) {
		this.grandtotal = grandtotal;
	}

	@Column(name = "discount")
	private Double discount = 0.00;
	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	@Column(name = "tax")
	private Double tax = 0.00;
	@Column(name = "adjustment")
	private Double adjustment = 0.00;
	@Column(name = "grandtotal")
	private Double grandtotal = 0.00;

	@Column(name = "gst_type")
	private String gstType = "";
	
	
	@Column(name = "valid_until")
	private Date validUntil ;
	
	
	
	@Column(name = "terms")
	private String terms ;
	
	
	public int getNoOfProducts() {
		return noOfProducts;
	}

	public void setNoOfProducts(int noOfProducts) {
		this.noOfProducts = noOfProducts;
	}

	@Column(name = "no_of_products", columnDefinition = "SMALLINT")
	private int noOfProducts;
	
	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(int gstAmount) {
		this.gstAmount = gstAmount;
	}

	public int getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(int gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
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

	

	

}
