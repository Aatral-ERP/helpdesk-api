/**
 * 
 */
package com.autolib.helpdesk.Sales.model.Invoice;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_invoice_contacts", indexes = { @Index(name = "invoice_id_idx", columnList = "invoice_id") })
public class DealInvoiceContacts {

	/**
	 * 
	 */
	public DealInvoiceContacts() {
		// TODO Auto-generated constructor stub
	}

	public DealInvoiceContacts(int invoiceId, InstituteContact instituteContact) {
		this.invoiceId = invoiceId;
		this.instituteContact = instituteContact;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "invoice_id", nullable = false)
	private int invoiceId;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "institute_contact_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private InstituteContact instituteContact;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public InstituteContact getInstituteContact() {
		return instituteContact;
	}

	public void setInstituteContact(InstituteContact instituteContact) {
		this.instituteContact = instituteContact;
	}

}
