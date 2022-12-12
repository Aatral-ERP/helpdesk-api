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
@Table(name = "deal_project_implementation_comment_attachments", indexes = {
		@Index(name = "comment_id_idx", columnList = "comment_id"),
		@Index(name = "deal_id_idx", columnList = "deal_id") })
public class DealProjectImplementationCommentsAttachments {

	/**
	 * 
	 */
	public DealProjectImplementationCommentsAttachments() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "comment_id")
	private int commentId;

	@Column(name = "deal_id")
	private int dealId;

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

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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
