/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

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
@Table(name = "deal_contacts", indexes = { @Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealContacts {

	/**
	 * 
	 */
	public DealContacts() {
		// TODO Auto-generated constructor stub
	}

	public DealContacts(int dealId, InstituteContact instituteContact) {
		this.dealId = dealId;
		this.instituteContact = instituteContact;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id", nullable = false)
	private int dealId;

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

	public InstituteContact getInstituteContact() {
		return instituteContact;
	}

	public void setInstituteContact(InstituteContact instituteContact) {
		this.instituteContact = instituteContact;
	}

}
