package com.autolib.helpdesk.Sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "deal_dc_products", uniqueConstraints = {
		@UniqueConstraint(name = "uc_dc_product", columnNames = { "dc_id", "product_id" }) })
public class DealDCProducts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int dealId;
	private String invoiceNo;
	@Column(name = "product_id")
	private int productId;
	private String name;
	private String description;
	private String uom;
	private int partId;
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "dc_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private DealDeliveryChallan dealDeliveryChallan;

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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public DealDeliveryChallan getDealDeliveryChallan() {
		return dealDeliveryChallan;
	}

	public void setDealDeliveryChallan(DealDeliveryChallan dealDeliveryChallan) {
		this.dealDeliveryChallan = dealDeliveryChallan;
	}

	@Transient
	public String getNameDescHTMLText() {
		String name_description =  getName() ;

		if (!getDescription().isEmpty()) {
			name_description = name_description + "<br>" + getDescription();
		}
		return name_description;
	}

	@Transient
	public String getQuantityAsHTMLText() {
		String str = getQuantity() + "";
		if (this.uom != null && !this.uom.isEmpty()) {
			str = str + "<br>(" + this.uom + ")";
		}
		return str;
	}

}
