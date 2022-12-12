package com.autolib.helpdesk.Agents.entity;

import java.util.Date;
import java.util.List;

public class StockEntryReq {

	private List<Product> products;
	private String remarks;
	private String type;
	private String category;
	public Date entryDateFrom;
	public Date entryDateTo;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getEntryDateFrom() {
		return entryDateFrom;
	}

	public void setEntryDateFrom(Date entryDateFrom) {
		this.entryDateFrom = entryDateFrom;
	}

	public Date getEntryDateTo() {
		return entryDateTo;
	}

	public void setEntryDateTo(Date entryDateTo) {
		this.entryDateTo = entryDateTo;
	}

	@Override
	public String toString() {
		return "StockEntryReq [products=" + products + ", remarks=" + remarks + ", type=" + type + ", entryDateFrom="
				+ entryDateFrom + ", entryDateTo=" + entryDateTo + "]";
	}

}
