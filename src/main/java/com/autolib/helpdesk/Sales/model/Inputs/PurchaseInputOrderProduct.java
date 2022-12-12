package com.autolib.helpdesk.Sales.model.Inputs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "purchase_input_order_products", indexes = { @Index(name = "order_id_idx", columnList = "order_id"),
		@Index(name = "order_no_idx", columnList = "order_no"),
		@Index(name = "product_id_idx", columnList = "product_id") })
public class PurchaseInputOrderProduct {

	public PurchaseInputOrderProduct() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "order_id")
	private int orderId;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	@Transient
	public double getGSTAmount() {
		double gst = 0.00;
		if (getGstPercentage() > 0) {
			gst = ((getPrice() - getDiscount()) * getQuantity()) * ((double) getGstPercentage() / 100);
		}
		return gst;
	}

	@Transient
	public double getRateAmount() {
		double gst = 0.00;
		if (getGstPercentage() > 0) {
			gst = (getPrice() - getDiscount());
		}
		return gst;
	}

	@Transient
	public double getTotalAmount() {
		double total = 0.00;

		double amount = getPrice() - getDiscount();
		total = ((amount) * getQuantity()) + getGSTAmount();

		System.out.println("getPrice:::" + getPrice());
		System.out.println("getDiscount:::" + getDiscount());
		System.out.println("amount:::" + amount);
		System.out.println("getGSTAmount:::" + getGSTAmount());
		System.out.println("getQuantity:::" + getQuantity());
		System.out.println("total:::" + total);

		return total;
	}

//	@Transient
//	public double getTotalAmount() {
//		double total = 0.00;
//		total = ((getPrice() - getDiscount()) + getGSTAmount()) * getQuantity();
//		return total;
//	}

	@Transient
	public String getGSTHTMLText(String gstType) {
		String gst_text = "";
		int gst_percent = getGstPercentage();
		double gst_amount = getGSTAmount();

		if (gstType.equalsIgnoreCase("IGST")) {
			gst_text = "IGST(" + gst_percent + "%) " + gst_amount;
		} else {
			String __gst = String.format("%.2f", (gst_amount / 2));
			gst_text = gst_text + "CGST(" + gst_percent / 2 + "%) " + __gst;
			gst_text = gst_text + "<br>SGST(" + gst_percent / 2 + "%) " + __gst;
		}
		return gst_text;
	}

	@Transient
	public String getNameDescHTMLText() {
		String name_description = getName();
		if (getDiscount() > 0) {
			name_description = name_description + "<br><u>Discount : " + getDiscount() + "</u>";
		}
		if (!getDescription().isEmpty()) {
			name_description = name_description + "<br>" + getDescription();
		}
		return name_description.replaceAll("\n", "<br>");
	}

	@Override
	public String toString() {
		return "PurchaseInputOrderProduct [id=" + id + ", orderNo=" + orderNo + ", orderId=" + orderId + ", productId="
				+ productId + ", name=" + name + ", description=" + description + ", quantity=" + quantity + ", price="
				+ price + ", discount=" + discount + ", gstPercentage=" + gstPercentage + "]";
	}

}
