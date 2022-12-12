package com.autolib.helpdesk.Agents.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "products_raw_materials")
public class ProductsRawMaterials {

	public ProductsRawMaterials() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column
	private int productId;

	@Column
	private int mappedProductId;

	@Column(length = 512)
	private String mappedProductName;

	@Column
	private String description;

	@Column
	private int quantity;

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

	public int getMappedProductId() {
		return mappedProductId;
	}

	public void setMappedProductId(int mappedProductId) {
		this.mappedProductId = mappedProductId;
	}

	public String getMappedProductName() {
		return mappedProductName;
	}

	public void setMappedProductName(String mappedProductName) {
		this.mappedProductName = mappedProductName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	@Override
	public String toString() {
		return "ProductsRawMaterials [id=" + id + ", productId=" + productId + ", mappedProductId=" + mappedProductId
				+ ", mappedProductName=" + mappedProductName + ", description=" + description + ", quantity=" + quantity
				+ ", createddatetime=" + createddatetime + "]";
	}

}
