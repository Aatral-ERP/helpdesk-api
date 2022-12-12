package com.autolib.helpdesk.Institutes.model;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;

public class InstituteProductsRequest {

	private List<Institute> institutes;
	private List<Product> products;
	private Date fromDate;
	private Date toDate;
	private List<ServiceUnder> serviceUnders;
	private List<InstituteProducts> instituteProducts;
	private boolean prepareAMCDashboardData = false;

	public Boolean getPrepareAMCDashboardData() {
		return prepareAMCDashboardData;
	}

	public void setPrepareAMCDashboardData(Boolean prepareAMCDashboardData) {
		this.prepareAMCDashboardData = prepareAMCDashboardData;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<ServiceUnder> getServiceUnders() {
		return serviceUnders;
	}

	public void setServiceUnders(List<ServiceUnder> serviceUnders) {
		this.serviceUnders = serviceUnders;
	}

	public List<InstituteProducts> getInstituteProducts() {
		return instituteProducts;
	}

	public void setInstituteProducts(List<InstituteProducts> instituteProducts) {
		this.instituteProducts = instituteProducts;
	}

	@Override
	public String toString() {
		return "InstituteProductsRequest [institutes=" + institutes + ", products=" + products + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", serviceUnders=" + serviceUnders + ", instituteProducts="
				+ instituteProducts + "]";
	}

}
