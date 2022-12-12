/**
 * 
 */
package com.autolib.helpdesk.Institutes.model;

import java.util.List;

/**
 * @author Kannadasan
 *
 */
public class InvoiceRequest {

	public InvoiceRequest() {
		// TODO Auto-generated constructor stub
	}

	private Invoice invoice;
	private List<InvoiceProduct> invoiceProducts;

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public List<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public void setInvoiceProducts(List<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

}
