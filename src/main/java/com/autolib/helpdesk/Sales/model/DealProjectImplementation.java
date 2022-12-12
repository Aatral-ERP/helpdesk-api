/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_project_implementations")
public class DealProjectImplementation {

	/**
	 * 
	 */
	public DealProjectImplementation() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id", unique = true, updatable = false, nullable = false)
	private int dealId;
	@Column
	private String poNo;
	@Column
	private String status;

	@Transient
	private String mailAction;

	@Column
	private String manufacturingAgent;
//	@Column
//	private String manufacturingStatus;
	@Column
	private String manufacturingAssignedBy;
	@Column
	private String manufacturingFinishedBy;
	@Column
	private String manufacturingApprovedBy;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date manufacturingAssignedDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date manufacturingFinishedDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date manufacturingApprovedDateTime;

	@Column
	private String deliveryAgent;
//	@Column
//	private String deliveryStatus;
	@Column
	private String deliveryAssignedBy;
	@Column
	private String deliveryFinishedBy;
	@Column
	private String deliveryApprovedBy;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryAssignedDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryFinishedDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryApprovedDateTime;

	@Column
	private String installedAgent;
//	@Column
//	private String installedStatus;
	@Column
	private String installedAssignedBy;
	@Column
	private String installedFinishedBy;
	@Column
	private String installedApprovedBy;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date installedAssignedDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date installedFinishedDateTime;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date installedApprovedDateTime;

	@Column
	private String workCompletionBy;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date workCompletionDateTime;

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

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getManufacturingAgent() {
		return manufacturingAgent;
	}

	public void setManufacturingAgent(String manufacturingAgent) {
		this.manufacturingAgent = manufacturingAgent;
	}

//	public String getManufacturingStatus() {
//		return manufacturingStatus;
//	}
//
//	public void setManufacturingStatus(String manufacturingStatus) {
//		this.manufacturingStatus = manufacturingStatus;
//	}

	public String getManufacturingAssignedBy() {
		return manufacturingAssignedBy;
	}

	public void setManufacturingAssignedBy(String manufacturingAssignedBy) {
		this.manufacturingAssignedBy = manufacturingAssignedBy;
	}

	public String getManufacturingFinishedBy() {
		return manufacturingFinishedBy;
	}

	public void setManufacturingFinishedBy(String manufacturingFinishedBy) {
		this.manufacturingFinishedBy = manufacturingFinishedBy;
	}

	public String getManufacturingApprovedBy() {
		return manufacturingApprovedBy;
	}

	public void setManufacturingApprovedBy(String manufacturingApprovedBy) {
		this.manufacturingApprovedBy = manufacturingApprovedBy;
	}

	public Date getManufacturingAssignedDateTime() {
		return manufacturingAssignedDateTime;
	}

	public void setManufacturingAssignedDateTime(Date manufacturingAssignedDateTime) {
		this.manufacturingAssignedDateTime = manufacturingAssignedDateTime;
	}

	public Date getManufacturingFinishedDateTime() {
		return manufacturingFinishedDateTime;
	}

	public void setManufacturingFinishedDateTime(Date manufacturingFinishedDateTime) {
		this.manufacturingFinishedDateTime = manufacturingFinishedDateTime;
	}

	public Date getManufacturingApprovedDateTime() {
		return manufacturingApprovedDateTime;
	}

	public void setManufacturingApprovedDateTime(Date manufacturingApprovedDateTime) {
		this.manufacturingApprovedDateTime = manufacturingApprovedDateTime;
	}

	public String getDeliveryAgent() {
		return deliveryAgent;
	}

	public void setDeliveryAgent(String deliveryAgent) {
		this.deliveryAgent = deliveryAgent;
	}

//	public String getDeliveryStatus() {
//		return deliveryStatus;
//	}
//
//	public void setDeliveryStatus(String deliveryStatus) {
//		this.deliveryStatus = deliveryStatus;
//	}

	public String getDeliveryAssignedBy() {
		return deliveryAssignedBy;
	}

	public void setDeliveryAssignedBy(String deliveryAssignedBy) {
		this.deliveryAssignedBy = deliveryAssignedBy;
	}

	public String getDeliveryFinishedBy() {
		return deliveryFinishedBy;
	}

	public void setDeliveryFinishedBy(String deliveryFinishedBy) {
		this.deliveryFinishedBy = deliveryFinishedBy;
	}

	public String getDeliveryApprovedBy() {
		return deliveryApprovedBy;
	}

	public void setDeliveryApprovedBy(String deliveryApprovedBy) {
		this.deliveryApprovedBy = deliveryApprovedBy;
	}

	public Date getDeliveryAssignedDateTime() {
		return deliveryAssignedDateTime;
	}

	public void setDeliveryAssignedDateTime(Date deliveryAssignedDateTime) {
		this.deliveryAssignedDateTime = deliveryAssignedDateTime;
	}

	public Date getDeliveryFinishedDateTime() {
		return deliveryFinishedDateTime;
	}

	public void setDeliveryFinishedDateTime(Date deliveryFinishedDateTime) {
		this.deliveryFinishedDateTime = deliveryFinishedDateTime;
	}

	public Date getDeliveryApprovedDateTime() {
		return deliveryApprovedDateTime;
	}

	public void setDeliveryApprovedDateTime(Date deliveryApprovedDateTime) {
		this.deliveryApprovedDateTime = deliveryApprovedDateTime;
	}

	public String getInstalledAgent() {
		return installedAgent;
	}

	public void setInstalledAgent(String installedAgent) {
		this.installedAgent = installedAgent;
	}

//	public String getInstalledStatus() {
//		return installedStatus;
//	}
//
//	public void setInstalledStatus(String installedStatus) {
//		this.installedStatus = installedStatus;
//	}

	public String getInstalledAssignedBy() {
		return installedAssignedBy;
	}

	public void setInstalledAssignedBy(String installedAssignedBy) {
		this.installedAssignedBy = installedAssignedBy;
	}

	public String getInstalledFinishedBy() {
		return installedFinishedBy;
	}

	public void setInstalledFinishedBy(String installedFinishedBy) {
		this.installedFinishedBy = installedFinishedBy;
	}

	public String getInstalledApprovedBy() {
		return installedApprovedBy;
	}

	public void setInstalledApprovedBy(String installedApprovedBy) {
		this.installedApprovedBy = installedApprovedBy;
	}

	public Date getInstalledAssignedDateTime() {
		return installedAssignedDateTime;
	}

	public void setInstalledAssignedDateTime(Date installedAssignedDateTime) {
		this.installedAssignedDateTime = installedAssignedDateTime;
	}

	public Date getInstalledFinishedDateTime() {
		return installedFinishedDateTime;
	}

	public void setInstalledFinishedDateTime(Date installedFinishedDateTime) {
		this.installedFinishedDateTime = installedFinishedDateTime;
	}

	public Date getInstalledApprovedDateTime() {
		return installedApprovedDateTime;
	}

	public void setInstalledApprovedDateTime(Date installedApprovedDateTime) {
		this.installedApprovedDateTime = installedApprovedDateTime;
	}

	public String getWorkCompletionBy() {
		return workCompletionBy;
	}

	public void setWorkCompletionBy(String workCompletionBy) {
		this.workCompletionBy = workCompletionBy;
	}

	public Date getWorkCompletionDateTime() {
		return workCompletionDateTime;
	}

	public void setWorkCompletionDateTime(Date workCompletionDateTime) {
		this.workCompletionDateTime = workCompletionDateTime;
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

	public String getMailAction() {
		return this.mailAction;
	}

	public void setMailAction(String mailAction) {
		this.mailAction = mailAction;
	}

	@Override
	public String toString() {
		return "DealProjectImplementation [id=" + id + ", dealId=" + dealId + ", poNo=" + poNo + ", mailAction="
				+ mailAction + ", status=" + status + ", manufacturingAgent=" + manufacturingAgent
				+ ", manufacturingAssignedBy=" + manufacturingAssignedBy + ", manufacturingFinishedBy="
				+ manufacturingFinishedBy + ", manufacturingApprovedBy=" + manufacturingApprovedBy
				+ ", manufacturingAssignedDateTime=" + manufacturingAssignedDateTime
				+ ", manufacturingFinishedDateTime=" + manufacturingFinishedDateTime
				+ ", manufacturingApprovedDateTime=" + manufacturingApprovedDateTime + ", deliveryAgent="
				+ deliveryAgent + ", deliveryAssignedBy=" + deliveryAssignedBy + ", deliveryFinishedBy="
				+ deliveryFinishedBy + ", deliveryApprovedBy=" + deliveryApprovedBy + ", deliveryAssignedDateTime="
				+ deliveryAssignedDateTime + ", deliveryFinishedDateTime=" + deliveryFinishedDateTime
				+ ", deliveryApprovedDateTime=" + deliveryApprovedDateTime + ", installedAgent=" + installedAgent
				+ ", installedAssignedBy=" + installedAssignedBy + ", installedFinishedBy=" + installedFinishedBy
				+ ", installedApprovedBy=" + installedApprovedBy + ", installedAssignedDateTime="
				+ installedAssignedDateTime + ", installedFinishedDateTime=" + installedFinishedDateTime
				+ ", installedApprovedDateTime=" + installedApprovedDateTime + ", workCompletionBy=" + workCompletionBy
				+ ", workCompletionDateTime=" + workCompletionDateTime + ", createddatetime=" + createddatetime
				+ ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
