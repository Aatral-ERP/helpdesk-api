package com.autolib.helpdesk.Sales.model.Inputs;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Agents.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bills", indexes = { @Index(name = "vendor_id_idx", columnList = "vendor_id"),
		@Index(name = "bill_no_idx", columnList = "bill_no"), @Index(name = "order_no_idx", columnList = "order_no"),
		@Index(name = "bill_date_idx", columnList = "bill_date"),
		@Index(name = "due_date_idx", columnList = "due_date"),
		@Index(name = "createddatetime_idx", columnList = "createddatetime"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "created_by_idx", columnList = "created_by") })
public class Bill {

	public Bill() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "vendor_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Vendor vendor;

	@Column(name = "bill_no")
	private String billNo;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "bill_date")
	private Date billDate;

	@Column(name = "due_date")
	private Date dueDate;

	@Lob
	@Column
	private String shippingTo;

	@Lob
	@Column
	private String billingTo;

	@Column
	private String gstType;

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

	@Lob
	@Column
	private String terms;

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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getShippingTo() {
		return shippingTo;
	}

	public void setShippingTo(String shippingTo) {
		this.shippingTo = shippingTo;
	}

	public String getBillingTo() {
		return billingTo;
	}

	public void setBillingTo(String billingTo) {
		this.billingTo = billingTo;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", vendor=" + vendor + ", billNo=" + billNo + ", orderNo=" + orderNo + ", billDate="
				+ billDate + ", dueDate=" + dueDate + ", shippingTo=" + shippingTo + ", billingTo=" + billingTo
				+ ", gstType=" + gstType + ", subTotal=" + subTotal + ", discount=" + discount + ", tax=" + tax
				+ ", adjustment=" + adjustment + ", grandTotal=" + grandTotal + ", noOfProducts=" + noOfProducts
				+ ", terms=" + terms + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createddatetime="
				+ createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
