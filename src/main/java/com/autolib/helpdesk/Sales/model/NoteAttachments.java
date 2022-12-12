/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "deal_note_attachments", indexes = { @Index(name = "deal_id_idx", columnList = "deal_id"),
		@Index(name = "note_id_idx", columnList = "note_id") })
public class NoteAttachments {

	/**
	 * 
	 */
	public NoteAttachments() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "deal_id")
	private int dealId;

	@Column(name = "note_id")
	private int noteId;

	@Column
	private String filename;

	@Column
	private String filetype;

	@Column
	private Double size;

	@Transient
	private String file;

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

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
