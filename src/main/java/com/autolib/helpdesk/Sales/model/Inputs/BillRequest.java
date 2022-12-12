package com.autolib.helpdesk.Sales.model.Inputs;

import java.util.List;

public class BillRequest {

	public BillRequest() {
		// TODO Auto-generated constructor stub
	}

	private Bill bill;

	private List<BillProducts> billProducts;

	private List<BillAttachments> billAttachments;

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<BillProducts> getBillProducts() {
		return billProducts;
	}

	public void setBillProducts(List<BillProducts> billProducts) {
		this.billProducts = billProducts;
	}

	public List<BillAttachments> getBillAttachments() {
		return billAttachments;
	}

	public void setBillAttachments(List<BillAttachments> billAttachments) {
		this.billAttachments = billAttachments;
	}

	@Override
	public String toString() {
		return "BillRequest [bill=" + bill + ", billProducts=" + billProducts + ", billAttachments=" + billAttachments
				+ "]";
	}

}
