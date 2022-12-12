/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_delivery_challans", indexes = { @Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealDeliveryChallan {

	/**
	 * 
	 */
	public DealDeliveryChallan() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id")
	private int dealId;

	@Column(name = "invoice_id")
	private int invoiceId;

	@Column
	private String invoiceNo;

	@Column(name = "stockDeductId")
	private int stockDeductId;

	@Column
	private String filename;

	@OneToMany(mappedBy = "dealDeliveryChallan", cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
	private List<DealDCProducts> products;

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

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getStockDeductId() {
		return stockDeductId;
	}

	public void setStockDeductId(int stockDeductId) {
		this.stockDeductId = stockDeductId;
	}

	public List<DealDCProducts> getProducts() {
		return products;
	}

	public void setProducts(List<DealDCProducts> products) {
		this.products = products;

		for (DealDCProducts dcp : products) {
			dcp.setDealDeliveryChallan(this);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	@Override
	public String toString() {
		return "DealDeliveryChallan [id=" + id + ", dealId=" + dealId + ", invoiceId=" + invoiceId + ", invoiceNo="
				+ invoiceNo + ", stockDeductId=" + stockDeductId + ", filename=" + filename + ", products=" + products
				+ ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
