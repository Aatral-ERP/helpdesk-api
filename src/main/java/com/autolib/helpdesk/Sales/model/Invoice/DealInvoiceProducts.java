/**
 * 
 */
package com.autolib.helpdesk.Sales.model.Invoice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_invoice_products", indexes = { @Index(name = "deal_id_idx", columnList = "deal_id"),
		@Index(name = "invoice_no_idx", columnList = "invoice_no"),
		@Index(name = "invoice_id_idx", columnList = "invoice_id"),
		@Index(name = "product_id_idx", columnList = "product_id") })
public class DealInvoiceProducts {

	/**
	 * 
	 */
	public DealInvoiceProducts() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id", nullable = false)
	private int dealId;

	@Column(name = "invoice_id", nullable = false)
	private int invoiceId;

	@Column(name = "invoice_no")
	private String invoiceNo = "";

	@Column(name = "product_id", nullable = false)
	private int productId;

	@Lob
	@Column(name = "name")
	private String name = "";

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price = 0.00;

	@Column(name = "discount")
	private Double discount = 0.00;

	@Column(name = "quantity")
	private int quantity = 0;

	@Column
	private String uom;

	@Column(nullable = false, columnDefinition = "int(11) default 1")
	private int partId;

	@Column(name = "gst_percentage")
	private int gstPercentage = 0;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		return getPrice() - getDiscount();
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
		String name_description = this.getName();
		if (getDiscount() > 0) {
			name_description = name_description + "<br><u>Discount : " + getDiscount() + "</u>";
		}

		if (!getDescription().isEmpty()) {
			name_description = name_description + "<br>" + getDescription();
		}
		return name_description;
	}

	@Transient
	public String getQuantityAsHTMLText() {
		String str = getQuantity() + "";
		if (this.uom != null && this.uom != "") {
			str = str + "<br>(" + this.uom + ")";
		}
		return str;
	}

	@Transient
	public String getNameQuantityAsHTMLText() {
		String str = getName() + " (Qty - ";
		str = str + getQuantity() + " ";

		if (this.uom != null && !this.uom.isEmpty()) {
			str = str + this.uom;
		}
		str = str + ")";
//		if (!getDescription().isEmpty()) {
//			str = str + "<br>" + getDescription().replaceAll("\r", "<br>");
//		}
		return str;
	}

	@Override
	public String toString() {
		return "DealInvoiceProducts [id=" + id + ", dealId=" + dealId + ", invoiceId=" + invoiceId + ", invoiceNo="
				+ invoiceNo + ", productId=" + productId + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", discount=" + discount + ", quantity=" + quantity + ", gstPercentage="
				+ gstPercentage + "]";
	}

}
