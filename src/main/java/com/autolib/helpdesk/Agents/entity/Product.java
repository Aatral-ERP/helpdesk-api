package com.autolib.helpdesk.Agents.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products", indexes = { @Index(name = "category_idx", columnList = "category") })
public class Product {

	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	public Product(int id, String name, Double amount, Double amcAmount, String hsn, String amchsn,
			boolean finishedProduct, int stock, String category) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.amcAmount = amcAmount;
		this.hsn = hsn;
		this.amchsn = amchsn;
		this.finishedProduct = finishedProduct;
		this.stock = stock;
		this.category = category;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(length = 512, nullable = false)
	private String name;

	@Column(nullable = false)
	private String category = "";

	@Lob
	@Column
	private String description;

	@Column(columnDefinition = "Decimal(10,2) default '0.00'")
	private Double amount = 0.00;

	@Column(columnDefinition = "smallint default 0")
	private int gst = 0;

	@Column(name = "amc_amount", columnDefinition = "Decimal(10,2) default '0.00'")
	private Double amcAmount = 0.00;

	@Column
	private String warranty;

	@Lob
	@Column
	private String salesDescription;

	@Lob
	@Column
	private String amcDescription;

	@Column
	private String hsn;

	@Column
	private String uom;

	@Column
	private String amchsn;

	@Column(name = "finished_product", columnDefinition = "tinyint(1) default 1", nullable = false)
	private boolean finishedProduct = true;

	@Column(columnDefinition = "tinyint(1) default 1", nullable = false)
	private boolean maintainable = true;

	@Column(columnDefinition = "int(11) default 0", nullable = false, updatable = false)
	private int stock = 0;

	@Column(columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean externalProduct = false;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "vendor_id", referencedColumnName = "id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Vendor vendor;

	@Column(name = "vendor_amount", columnDefinition = "Decimal(10,2) default '0.00'")
	private Double vendorAmount = 0.00;

	@Column(name = "vendor_amc_amount", columnDefinition = "Decimal(10,2) default '0.00'")
	private Double vendorAmcAmount = 0.00;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmcAmount() {
		return amcAmount;
	}

	public void setAmcAmount(Double amcAmount) {
		this.amcAmount = amcAmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getGst() {
		return gst;
	}

	public void setGst(int gst) {
		this.gst = gst;
	}

	public String getSalesDescription() {
		return salesDescription;
	}

	public void setSalesDescription(String salesDescription) {
		this.salesDescription = salesDescription;
	}

	public String getAmcDescription() {
		return amcDescription;
	}

	public void setAmcDescription(String amcDescription) {
		this.amcDescription = amcDescription;
	}

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getAmchsn() {
		return amchsn;
	}

	public void setAmchsn(String amchsn) {
		this.amchsn = amchsn;
	}

	public boolean isMaintainable() {
		return maintainable;
	}

	public void setMaintainable(boolean maintainable) {
		this.maintainable = maintainable;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Double getVendorAmount() {
		return vendorAmount;
	}

	public void setVendorAmount(Double vendorAmount) {
		this.vendorAmount = vendorAmount;
	}

	public Double getVendorAmcAmount() {
		return vendorAmcAmount;
	}

	public void setVendorAmcAmount(Double vendorAmcAmount) {
		this.vendorAmcAmount = vendorAmcAmount;
	}

	public boolean isExternalProduct() {
		return externalProduct;
	}

	public void setExternalProduct(boolean externalProduct) {
		this.externalProduct = externalProduct;
	}

	public boolean isFinishedProduct() {
		return finishedProduct;
	}

	public void setFinishedProduct(boolean finishedProduct) {
		this.finishedProduct = finishedProduct;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", amount=" + amount + ", gst=" + gst + ", amcAmount=" + amcAmount + ", warranty=" + warranty
				+ ", salesDescription=" + salesDescription + ", amcDescription=" + amcDescription + ", hsn=" + hsn
				+ ", uom=" + uom + ", amchsn=" + amchsn + ", finishedProduct=" + finishedProduct + ", maintainable="
				+ maintainable + ", stock=" + stock + ", externalProduct=" + externalProduct + ", vendor=" + vendor
				+ ", vendorAmount=" + vendorAmount + ", vendorAmcAmount=" + vendorAmcAmount + "]";
	}

}
