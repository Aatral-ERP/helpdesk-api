package com.autolib.helpdesk.Accounting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "bank_account_statements",uniqueConstraints = {
		@UniqueConstraint(name = "uc_desc_date", columnNames = { "totalAmount", "transaction_date", "creditAmount", "debitAmount"}) })
public class AccountStatement {

	public AccountStatement() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Lob
	@Column
	private String description;
	
	@Column
	private String refNo;

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@Column(columnDefinition = "Decimal(10,2) default '0.00'")
	private Double creditAmount = 0.00;

	@Column(columnDefinition = "Decimal(10,2) default '0.00'")
	private Double debitAmount = 0.00;

	@Column(columnDefinition = "Decimal(10,2) default '0.00'")
	private Double totalAmount = 0.00;

	@Temporal(TemporalType.DATE)
	@Column(name = "transaction_date", nullable = false)
	private Date transactionDate;
		
	@Temporal(TemporalType.DATE)
	@Column(name = "post_date", nullable = false)
	private Date postDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "AccountStatement [id=" + id + ", description=" + description + ", refNo=" + refNo + ", creditAmount="
				+ creditAmount + ", debitAmount=" + debitAmount + ", totalAmount=" + totalAmount + ", transactionDate="
				+ transactionDate + ", postDate=" + postDate + "]";
	}

	

}
