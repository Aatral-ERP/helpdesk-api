/**
 * 
 */
package com.autolib.helpdesk.Agents.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "stock_entries")
public class StockEntry {

	/**
	 * 
	 */
	public StockEntry() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "quantity", nullable = false, columnDefinition = "int(11) default 0")
	private int quantity;

	@Temporal(TemporalType.DATE)
	@Column(name = "entry_date", nullable = false)
	private Date entryDate;

	@Column
	private String entryBy;

	@Column
	private String type;

	@Lob
	@Column
	private String remarks;

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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Override
	public String toString() {
		return "StockEntry [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", entryDate="
				+ entryDate + ", entryBy=" + entryBy + ", type=" + type + ", remarks=" + remarks + ", createddatetime="
				+ createddatetime + "]";
	}

}
