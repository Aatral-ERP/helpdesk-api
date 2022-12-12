/**
 * 
 */
package com.autolib.helpdesk.Institutes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.autolib.helpdesk.Agents.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "invoice_product")
public class InvoiceProduct {

	public InvoiceProduct() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Product product;

	@Lob
	@Column
	private String description;
	@Column(name = "discount_description")
	private String discountDescription;
	@Column
	private int quantity;
	@Column(name = "initial_amount")
	private Double initialAmount;
	@Column(name = "discount_amount")
	private Double discountAmount;
	@Column(name = "total_amount")
	private Double totalAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscountDescription() {
		return discountDescription;
	}

	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Double initialAmount) {
		this.initialAmount = initialAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "InvoiceProduct [id=" + id + ", product=" + product + ", description=" + description
				+ ", discountDescription=" + discountDescription + ", quantity=" + quantity + ", initialAmount="
				+ initialAmount + ", discountAmount=" + discountAmount + ", totalAmount=" + totalAmount + "]";
	}

}
