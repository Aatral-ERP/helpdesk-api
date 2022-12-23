/**
 * 
 */
package com.autolib.helpdesk.Accounting.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealPayments;

public class AmcReportRequest {
	
	
	
	public AmcReportRequest() {
		
	}
	
	private Deal deal;
	
	private List<Institute> institutes;
	
	private Date fromDate;
	
	private Date toDate;
	
	private Date paymentFromDate;
	
	private Date paymentToDate;
	
	private DealPayments dealPayment;
	
	private List<Product> products;

	/**
	 * @return the deal
	 */
	public Deal getDeal() {
		return deal;
	}

	/**
	 * @param deal the deal to set
	 */
	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	/**
	 * @return the paymentFromDate
	 */
	public Date getPaymentFromDate() {
		return paymentFromDate;
	}

	/**
	 * @param paymentFromDate the paymentFromDate to set
	 */
	public void setPaymentFromDate(Date paymentFromDate) {
		this.paymentFromDate = paymentFromDate;
	}

	/**
	 * @return the paymentToDate
	 */
	public Date getPaymentToDate() {
		return paymentToDate;
	}

	/**
	 * @param paymentToDate the paymentToDate to set
	 */
	public void setPaymentToDate(Date paymentToDate) {
		this.paymentToDate = paymentToDate;
	}

	/**
	 * @return the dealPayment
	 */
	public DealPayments getDealPayment() {
		return dealPayment;
	}

	/**
	 * @param dealPayment the dealPayment to set
	 */
	public void setDealPayment(DealPayments dealPayment) {
		this.dealPayment = dealPayment;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "AmcReportRequest [deal=" + deal + ", institutes=" + institutes + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", paymentFromDate=" + paymentFromDate + ", paymentToDate=" + paymentToDate
				+ ", dealPayment=" + dealPayment + ", products=" + products + "]";
	}

	
}