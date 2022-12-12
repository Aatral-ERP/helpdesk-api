package com.autolib.helpdesk.Agents.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "role_master")
public class RoleMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column(length = 512, nullable = false)
	private String name;

	@Column
	private String institute;

	@Column
	private String product;

	@Column
	private String supplier;

	@Column
	private String leadManagement;

	@Column(name = "lead_management_admin", nullable = false)
	private boolean leadManagementAdmin = false;

	@Column
	private String sales;

	@Column(name = "sales_admin", nullable = false)
	private boolean salesAdmin = false;

	@Column
	private String purchaseInput;

	@Column
	private String accounting;

	@Column
	private String hr;

	@Column
	private String admin;

	@Column
	private String tickets;

	@Column(name = "tickets_admin", nullable = false)
	private boolean ticketsAdmin = false;

	@Column
	private String reports;

	@Column
	private String defaultDashboard;

	@Column(name = "created_by")
	private String createdBy = "";

	@Column(name = "modified_by")
	private String modifiedBy = "";

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

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

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getLeadManagement() {
		return leadManagement;
	}

	public void setLeadManagement(String leadManagement) {
		this.leadManagement = leadManagement;
	}

	public boolean isLeadManagementAdmin() {
		return leadManagementAdmin;
	}

	public void setLeadManagementAdmin(boolean leadManagementAdmin) {
		this.leadManagementAdmin = leadManagementAdmin;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public boolean isSalesAdmin() {
		return salesAdmin;
	}

	public void setSalesAdmin(boolean salesAdmin) {
		this.salesAdmin = salesAdmin;
	}

	public String getPurchaseInput() {
		return purchaseInput;
	}

	public void setPurchaseInput(String purchaseInput) {
		this.purchaseInput = purchaseInput;
	}

	public String getAccounting() {
		return accounting;
	}

	public void setAccounting(String accounting) {
		this.accounting = accounting;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getTickets() {
		return tickets;
	}

	public void setTickets(String tickets) {
		this.tickets = tickets;
	}

	public boolean isTicketsAdmin() {
		return ticketsAdmin;
	}

	public void setTicketsAdmin(boolean ticketsAdmin) {
		this.ticketsAdmin = ticketsAdmin;
	}

	public String getReports() {
		return reports;
	}

	public void setReports(String reports) {
		this.reports = reports;
	}

	public String getDefaultDashboard() {
		return defaultDashboard;
	}

	public void setDefaultDashboard(String defaultDashboard) {
		this.defaultDashboard = defaultDashboard;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	@Override
	public String toString() {
		return "RoleMaster [id=" + id + ", name=" + name + ", institute=" + institute + ", product=" + product
				+ ", supplier=" + supplier + ", sales=" + sales + ", purchaseInput=" + purchaseInput + ", accounting="
				+ accounting + ", hr=" + hr + ", admin=" + admin + ", tickets=" + tickets + ", reports=" + reports
				+ ", defaultDashboard=" + defaultDashboard + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", createddatetime=" + createddatetime + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
