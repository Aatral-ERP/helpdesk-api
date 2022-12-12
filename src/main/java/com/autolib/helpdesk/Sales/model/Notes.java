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
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_notes", indexes = { @Index(name = "deal_id_idx", columnList = "deal_id"),
		@Index(name = "note_for_idx", columnList = "note_for") })
public class Notes {

	/**
	 * 
	 */
	public Notes() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id")
	private int dealId;

	@Column(name = "note_for")
	private String noteFor;

	@Column(name = "note_title")
	private String noteTitle;

	@Lob
	@Column
	private String note;

	@Column
	private String noteby;

	@Column
	private String editedby;

	@Transient
	private String file;

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

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteby() {
		return noteby;
	}

	public void setNoteby(String noteby) {
		this.noteby = noteby;
	}

	public String getEditedby() {
		return editedby;
	}

	public void setEditedby(String editedby) {
		this.editedby = editedby;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

}