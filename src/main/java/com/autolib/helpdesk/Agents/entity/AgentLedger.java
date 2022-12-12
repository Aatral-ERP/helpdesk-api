/**
 * 
 */
package com.autolib.helpdesk.Agents.entity;

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

import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "agent_ledger")
public class AgentLedger {

	/**
	 * 
	 */
	public AgentLedger() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	@Column
	private String agentEmailId;

	@Column(length = 512, nullable = false)
	private String subject;

	@Column
	private String journal;

	@Lob
	@Column
	private String notes;

	@Column
	private Double credit;

	@Column
	private Double debit;

	@Column
	private Double balance;

	@Column
	private String filename;

	@Temporal(TemporalType.DATE)
	@Column(name = "payment_date", nullable = false)
	private Date paymentDate;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getAgentEmailId() {
		return agentEmailId;
	}

	public void setAgentEmailId(String agentEmailId) {
		this.agentEmailId = agentEmailId;
	}

	@Override
	public String toString() {
		return "AgentLedger [id=" + id + ", agentEmailId=" + agentEmailId + ", subject=" + subject + ", journal="
				+ journal + ", notes=" + notes + ", credit=" + credit + ", debit=" + debit + ", balance=" + balance
				+ ", filename=" + filename + ", paymentDate=" + paymentDate + ", createddatetime=" + createddatetime
				+ "]";
	}

}
