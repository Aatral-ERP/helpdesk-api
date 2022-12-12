package com.autolib.helpdesk.Institutes.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Sales.model.DealProducts;

public class AMCSearchRequest {
	
	private List<Product> amcProducts;
	private List<Institute> institutes;
	
	public String serviceType;
	public String payMode;	
	public String amcId;
	public String transDetails;
	public Date amcValidDateFrom;
	public Date amcValidDateTo;
	public Date amcInvDateFrom;
	public Date amcInvDateTo;
	private Date amcPaidDateFrom;
	private Date amcPaidDateTo;
	
	public List<Product> getAmcProducts() {
		return amcProducts;
	}
	public void setAmcProducts(List<Product> amcProducts) {
		this.amcProducts = amcProducts;
	}
	public List<Institute> getInstitutes() {
		return institutes;
	}
	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public Date getAmcPaidDateFrom() {
		return amcPaidDateFrom;
	}
	public void setAmcPaidDateFrom(Date amcPaidDateFrom) {
		this.amcPaidDateFrom = amcPaidDateFrom;
	}
	public Date getAmcPaidDateTo() {
		return amcPaidDateTo;
	}
	public void setAmcPaidDateTo(Date amcPaidDateTo) {
		this.amcPaidDateTo = amcPaidDateTo;
	}	
	public String getAmcId() {
		return amcId;
	}
	public void setAmcId(String amcId) {
		this.amcId = amcId;
	}
	public String getTransDetails() {
		return transDetails;
	}
	public void setTransDetails(String transDetails) {
		this.transDetails = transDetails;
	}
	public Date getAmcValidDateFrom() {
		return amcValidDateFrom;
	}
	public void setAmcValidDateFrom(Date amcValidDateFrom) {
		this.amcValidDateFrom = amcValidDateFrom;
	}
	public Date getAmcValidDateTo() {
		return amcValidDateTo;
	}
	public void setAmcValidDateTo(Date amcValidDateTo) {
		this.amcValidDateTo = amcValidDateTo;
	}
	public Date getAmcInvDateFrom() {
		return amcInvDateFrom;
	}
	public void setAmcInvDateFrom(Date amcInvDateFrom) {
		this.amcInvDateFrom = amcInvDateFrom;
	}
	public Date getAmcInvDateTo() {
		return amcInvDateTo;
	}
	public void setAmcInvDateTo(Date amcInvDateTo) {
		this.amcInvDateTo = amcInvDateTo;
	}
	@Override
	public String toString() {
		return "AMCSearchRequest [amcProducts=" + amcProducts + ", institutes=" + institutes + ", serviceType="
				+ serviceType + ", payMode=" + payMode + ", amcId=" + amcId + ", transDetails=" + transDetails
				+ ", amcValidDateFrom=" + amcValidDateFrom + ", amcValidDateTo=" + amcValidDateTo + ", amcInvDateFrom="
				+ amcInvDateFrom + ", amcInvDateTo=" + amcInvDateTo + ", amcPaidDateFrom=" + amcPaidDateFrom
				+ ", amcPaidDateTo=" + amcPaidDateTo + "]";
	}
	

}
