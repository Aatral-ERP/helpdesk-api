/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.List;

import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;

/**
 * @author Kannadasan
 *
 */
public class DealResponse {

	/**
	 * 
	 */
	public DealResponse() {
		// TODO Auto-generated constructor stub
	}

	public DealResponse(Deal deal, DealQuotation quote) {
		this.deal = deal;
		this.dealQuotation = quote;
	}

	public DealResponse(Deal deal, DealPurchaseOrder po) {
		this.deal = deal;
		this.purchaseOrder = po;
	}

	public DealResponse(Deal deal, DealSalesOrder so) {
		this.deal = deal;
		this.salesOrder = so;
	}

	public DealResponse(Deal deal, DealProformaInvoice pi) {
		this.deal = deal;
		this.dealProformaInvoice = pi;
	}

	public DealResponse(Deal deal, DealInvoice inv) {
		this.deal = deal;
		this.dealInvoice = inv;
	}

	public DealResponse(Deal deal, DealDeliveryChallan deliveryChallan) {
		this.deal = deal;
		this.deliveryChallan = deliveryChallan;
	}

	public DealResponse(Deal deal, DealPayments payment) {
		this.deal = deal;
		this.payment = payment;
	}

	public DealResponse(Deal deal, DealProjectImplementation projectImplementation) {
		this.deal = deal;
		this.projectImplementation = projectImplementation;
	}

	public DealResponse(Deal deal, DealProjectImplementation projectImplementation, List<DealProducts> dealProducts) {
		this.deal = deal;
		this.dealProducts = dealProducts;
		this.projectImplementation = projectImplementation;
	}

	private Deal deal;

	private List<DealProducts> dealProducts;

	private List<InstituteContact> instituteContacts;

	private DealQuotation dealQuotation;

	private DealSalesOrder salesOrder;

	private DealPurchaseOrder purchaseOrder;

	private DealProjectImplementation projectImplementation;

	private DealInvoice dealInvoice;

	private DealDeliveryChallan deliveryChallan;

	private DealProformaInvoice dealProformaInvoice;

	private DealPayments payment;

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public List<DealProducts> getDealProducts() {
		return dealProducts;
	}

	public void setDealProducts(List<DealProducts> dealProducts) {
		this.dealProducts = dealProducts;
	}

	public List<InstituteContact> getInstituteContacts() {
		return instituteContacts;
	}

	public void setInstituteContacts(List<InstituteContact> instituteContacts) {
		this.instituteContacts = instituteContacts;
	}

	public DealQuotation getDealQuotation() {
		return dealQuotation;
	}

	public void setDealQuotation(DealQuotation dealQuotation) {
		this.dealQuotation = dealQuotation;
	}

	public DealSalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(DealSalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public DealInvoice getDealInvoice() {
		return dealInvoice;
	}

	public void setDealInvoice(DealInvoice dealInvoice) {
		this.dealInvoice = dealInvoice;
	}

	public DealDeliveryChallan getDeliveryChallan() {
		return deliveryChallan;
	}

	public void setDeliveryChallan(DealDeliveryChallan deliveryChallan) {
		this.deliveryChallan = deliveryChallan;
	}

	public DealProformaInvoice getDealProformaInvoice() {
		return dealProformaInvoice;
	}

	public void setDealProformaInvoice(DealProformaInvoice dealProformaInvoice) {
		this.dealProformaInvoice = dealProformaInvoice;
	}
//
//	@Transient
//	public String getBillingToAddress() {
//		String billing_to = "";
//		if (this.getDeal().getBillingTo() != null && !this.getDeal().getBillingTo().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingTo() + ",<br>";
//		if (this.getDeal().getInstitute().getInstituteName() != null
//				&& !this.getDeal().getInstitute().getInstituteName().equals(""))
//			billing_to = billing_to + this.getDeal().getInstitute().getInstituteName() + ",<br>";
//		if (this.getDeal().getBillingStreet1() != null && !this.getDeal().getBillingStreet1().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingStreet1() + ",<br>";
//		if (this.getDeal().getBillingStreet2() != null && !this.getDeal().getBillingStreet2().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingStreet2() + ",<br>";
//
//		if (this.getDeal().getBillingCity() != null && !this.getDeal().getBillingCity().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingCity() + ", ";
//
//		if (this.getDeal().getBillingState() != null && !this.getDeal().getBillingState().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingState() + ", ";
//
//		if (this.getDeal().getBillingCountry() != null && !this.getDeal().getBillingCountry().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingCountry() + " - ";
//
//		if (this.getDeal().getBillingZIPCode() != null && !this.getDeal().getBillingZIPCode().equals(""))
//			billing_to = billing_to + this.getDeal().getBillingZIPCode() + ".<br>";
//
//		if (this.getDeal().getInstitute().getGstno() != null && !this.getDeal().getInstitute().getGstno().equals(""))
//			billing_to = billing_to + "GSTIN : " + this.getDeal().getInstitute().getGstno();
//		else
//			billing_to = billing_to + "GSTIN : Unregistered";
//
//		return billing_to;
//	}
//
//	@Transient
//	public String getShippingToAddress() {
//		String shipping_to = "";
//		if (this.getDeal().getShippingTo() != null && !this.getDeal().getShippingTo().equals(""))
//			shipping_to = shipping_to + this.getDeal().getBillingTo() + ",<br>";
//		if (this.getDeal().getInstitute().getInstituteName() != null
//				&& !this.getDeal().getInstitute().getInstituteName().equals(""))
//			shipping_to = shipping_to + this.getDeal().getInstitute().getInstituteName() + ",<br>";
//		if (this.getDeal().getShippingStreet1() != null && !this.getDeal().getShippingStreet1().equals(""))
//			shipping_to = shipping_to + this.getDeal().getShippingStreet1() + ",<br>";
//		if (this.getDeal().getShippingStreet2() != null && !this.getDeal().getShippingStreet2().equals(""))
//			shipping_to = shipping_to + this.getDeal().getShippingStreet2() + ",<br>";
//
//		if (this.getDeal().getShippingCity() != null && !this.getDeal().getShippingCity().equals(""))
//			shipping_to = shipping_to + this.getDeal().getShippingCity() + ", ";
//
//		if (this.getDeal().getShippingState() != null && !this.getDeal().getShippingState().equals(""))
//			shipping_to = shipping_to + this.getDeal().getShippingState() + ", ";
//
//		if (this.getDeal().getShippingCountry() != null && !this.getDeal().getShippingCountry().equals(""))
//			shipping_to = shipping_to + this.getDeal().getShippingCountry() + " - ";
//
//		if (this.getDeal().getShippingZIPCode() != null && !this.getDeal().getShippingZIPCode().equals(""))
//			shipping_to = shipping_to + this.getDeal().getShippingZIPCode() + ".<br>";
//
//		if (this.getDeal().getInstitute().getGstno() != null && !this.getDeal().getInstitute().getGstno().equals(""))
//			shipping_to = shipping_to + "GSTIN : " + this.getDeal().getInstitute().getGstno();
//		else
//			shipping_to = shipping_to + "GSTIN : Unregistered";
//
//		return shipping_to;
//	}

	public DealPayments getPayment() {
		return payment;
	}

	public void setPayment(DealPayments payment) {
		this.payment = payment;
	}

	public DealPurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(DealPurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public DealProjectImplementation getProjectImplementation() {
		return projectImplementation;
	}

	public void setProjectImplementation(DealProjectImplementation projectImplementation) {
		this.projectImplementation = projectImplementation;
	}

	@Override
	public String toString() {
		return "DealResponse [deal=" + deal + ", dealProducts=" + dealProducts + ", instituteContacts="
				+ instituteContacts + ", dealQuotation=" + dealQuotation + ", salesOrder=" + salesOrder
				+ ", dealInvoice=" + dealInvoice + ", dealProformaInvoice=" + dealProformaInvoice + ", payment="
				+ payment + "]";
	}

}
