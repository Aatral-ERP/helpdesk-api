package com.autolib.helpdesk.Agents.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "raw_materials_requests_products")
public class RawMaterialRequestProducts {

	public RawMaterialRequestProducts() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column
	private int requestId;

	@Column
	private int productId;

	@Column
	private int mappedProductId;

	@Column
	private String mappedProductName;

	@Column
	private String description;

	@Column
	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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

}
