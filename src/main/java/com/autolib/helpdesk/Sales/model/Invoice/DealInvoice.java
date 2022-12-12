/**
 * 
 */
package com.autolib.helpdesk.Sales.model.Invoice;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_invoices", indexes = { @Index(name = "invoice_no_idx", columnList = "invoice_no"),
		@Index(name = "invoice_date_idx", columnList = "invoice_date"),
		@Index(name = "due_date_idx", columnList = "due_date"),
		@Index(name = "gst_month_idx", columnList = "gst_month"),
		@Index(name = "gst_year_idx", columnList = "gst_year"),
		@Index(name = "invoice_status_idx", columnList = "invoice_status"),
		@Index(name = "createddatetime_idx", columnList = "createddatetime"),
		@Index(name = "lastupdatedatetime_idx", columnList = "lastupdatedatetime"),
		@Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealInvoice {

	/**
	 * 
	 */
	public DealInvoice() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "invoice_no", unique = true, nullable = false)
	private String invoiceNo = "";

	@Column(name = "purchase_order_no")
	private String purchaseOrderNo = "";

	@Column(name = "sales_order_no")
	private String salesOrderNo = "";

	@Column(name = "deal_id", nullable = false, updatable = false)
	private int dealId;

	@Lob
	@Column
	private String subject;

	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "gst_month", columnDefinition = "varchar(45) default''")
	private String gstMonth = "";

	@Column(name = "gst_year", columnDefinition = "varchar(45) default''")
	private String gstYear = "";

	@Column(name = "invoice_status")
	private String invoiceStatus = "";

	@Column(name = "excise_duty")
	private Double exciseDuty;

	@Column(name = "sale_commission")
	private Double salesCommission;

	@Column(name = "paid_amount")
	private Double paidAmount;

	@Column(name = "shipping_cost")
	private Double shippingCost;

	@Lob
	@Column(name = "terms")
	private String terms = "";

	@Column(name = "created_by")
	private String createdBy = "";

	@Column(name = "modified_by")
	private String modifiedBy = "";

	@Column(name = "filename")
	private String filename = "";

	@Column(name = "satisfactory_certificate")
	private String satisfactoryCertificate = "";

	@Column(name = "work_completion_certificate")
	private String workCompletionCertificate = "";

	@Column(name = "dcfilename")
	private String dcfilename = "";

	@Column(name = "deal_type", nullable = false, columnDefinition = "varchar(45) default 'Sales'")
	private String dealType = "";

	@Column(name = "gst_type", nullable = false)
	private String gstType;

	@Column(name = "amc_from_date")
	@Temporal(TemporalType.DATE)
	private Date amcFromDate;

	@Column(name = "amc_to_date")
	@Temporal(TemporalType.DATE)
	private Date amcToDate;

	@Column(name = "no_of_products", columnDefinition = "SMALLINT")
	private int noOfProducts;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_id", referencedColumnName = "institute_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Institute institute;

	@Lob
	@Column
	private String shippingTo;

	@Lob
	@Column
	private String billingTo;

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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getGstMonth() {
		return gstMonth;
	}

	public void setGstMonth(String gstMonth) {
		this.gstMonth = gstMonth;
	}

	public String getGstYear() {
		return gstYear;
	}

	public void setGstYear(String gstYear) {
		this.gstYear = gstYear;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Double getExciseDuty() {
		return exciseDuty;
	}

	public void setExciseDuty(Double exciseDuty) {
		this.exciseDuty = exciseDuty;
	}

	public Double getSalesCommission() {
		return salesCommission;
	}

	public void setSalesCommission(Double salesCommission) {
		this.salesCommission = salesCommission;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSatisfactoryCertificate() {
		return satisfactoryCertificate;
	}

	public void setSatisfactoryCertificate(String satisfactoryCertificate) {
		this.satisfactoryCertificate = satisfactoryCertificate;
	}

	public String getWorkCompletionCertificate() {
		return workCompletionCertificate;
	}

	public void setWorkCompletionCertificate(String workCompletionCertificate) {
		this.workCompletionCertificate = workCompletionCertificate;
	}

	public String getDcfilename() {
		return dcfilename;
	}

	public void setDcfilename(String dcfilename) {
		this.dcfilename = dcfilename;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
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

	public int getNoOfProducts() {
		return noOfProducts;
	}

	public void setNoOfProducts(int noOfProducts) {
		this.noOfProducts = noOfProducts;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
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

	@Transient
	public Object getBillingAddress() {
		String billing_to = "";
		try {
			billing_to = this.billingTo.replaceAll("\n", "<br>");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return billing_to;
	}

	@Transient
	public Object getShippingAddress() {
		String shipping_to = "";
		try {
			shipping_to = this.shippingTo.replaceAll("\n", "<br>");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return shipping_to;
	}

	@Override
	public String toString() {
		return "DealInvoice [id=" + id + ", invoiceNo=" + invoiceNo + ", purchaseOrderNo=" + purchaseOrderNo
				+ ", salesOrderNo=" + salesOrderNo + ", dealId=" + dealId + ", subject=" + subject + ", invoiceDate="
				+ invoiceDate + ", dueDate=" + dueDate + ", gstMonth=" + gstMonth + ", gstYear=" + gstYear
				+ ", invoiceStatus=" + invoiceStatus + ", exciseDuty=" + exciseDuty + ", salesCommission="
				+ salesCommission + ", paidAmount=" + paidAmount + ", shippingCost=" + shippingCost + ", terms=" + terms
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", filename=" + filename
				+ ", satisfactoryCertificate=" + satisfactoryCertificate + ", workCompletionCertificate="
				+ workCompletionCertificate + ", dcfilename=" + dcfilename + ", dealType=" + dealType + ", gstType="
				+ gstType + ", amcFromDate=" + amcFromDate + ", amcToDate=" + amcToDate + ", noOfProducts="
				+ noOfProducts + ", institute=" + institute + ", shippingTo=" + shippingTo + ", billingTo=" + billingTo
				+ ", subTotal=" + subTotal + ", discount=" + discount + ", tax=" + tax + ", adjustment=" + adjustment
				+ ", grandTotal=" + grandTotal + ", createddatetime=" + createddatetime + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
