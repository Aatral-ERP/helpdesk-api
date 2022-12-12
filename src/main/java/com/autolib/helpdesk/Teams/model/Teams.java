package com.autolib.helpdesk.Teams.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "teams", indexes = { @Index(name = "status_idx", columnList = "status"),
		@Index(name = "name_idx", columnList = "name") })
public class Teams {

	public Teams() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Lob
	@Column
	private String description;

	@Column
	private String category;

	@Column(nullable = false)
	private String status;

	@Lob
	@Column
	private String workflows;

	@Column(name = "open_tasks", nullable = false, columnDefinition = "int(11) default 0")
	private int openTasks;

	@Column(name = "closed_tasks", nullable = false, columnDefinition = "int(11) default 0")
	private int closedTasks;

	@Column
	private String colorcode;

	@Column(name = "lead_name")
	private String leadName = "";

	@Column
	private String leadEmail = "";

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getLeadEmail() {
		return leadEmail;
	}

	public void setLeadEmail(String leadEmail) {
		this.leadEmail = leadEmail;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getWorkflows() {
		return workflows;
	}

	public void setWorkflows(String workflows) {
		this.workflows = workflows;
	}

	public String getColorcode() {
		return colorcode;
	}

	public void setColorcode(String colorcode) {
		this.colorcode = colorcode;
	}

	public int getOpenTasks() {
		return openTasks;
	}

	public void setOpenTasks(int openTasks) {
		this.openTasks = openTasks;
	}

	public int getClosedTasks() {
		return closedTasks;
	}

	public void setClosedTasks(int closedTasks) {
		this.closedTasks = closedTasks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Teams [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", status=" + status + ", workflows=" + workflows + ", openTasks=" + openTasks + ", closedTasks="
				+ closedTasks + ", colorcode=" + colorcode + ", leadName=" + leadName + ", leadEmail=" + leadEmail
				+ ", createddatetime=" + createddatetime + "]";
	}

}
