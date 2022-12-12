package com.autolib.helpdesk.Sales.model.Inputs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "bill_products", indexes = { @Index(name = "bill_id_idx", columnList = "bill_id"),
		@Index(name = "bill_no_idx", columnList = "bill_no") })
public class BillProducts {

	public BillProducts() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "bill_no")
	private String billNo;

	@Column(name = "bill_id")
	private int billId;

	@Column(name = "product_id")
	private int productId;

	@Lob
	@Column
	private String name;

	@Lob
	@Column
	private String description;

	@Column
	private int quantity;

	@Column
	private Double price;

	@Column
	private Double discount;

	@Column(name = "gst_percentage")
	private int gstPercentage = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public int getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(int gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	@Override
	public String toString() {
		return "BillProducts [id=" + id + ", billNo=" + billNo + ", billId=" + billId + ", productId=" + productId
				+ ", name=" + name + ", description=" + description + ", quantity=" + quantity + ", price=" + price
				+ ", discount=" + discount + ", gstPercentage=" + gstPercentage + "]";
	}

}
