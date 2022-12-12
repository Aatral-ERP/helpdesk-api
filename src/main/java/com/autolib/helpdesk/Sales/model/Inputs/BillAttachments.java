package com.autolib.helpdesk.Sales.model.Inputs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bill_attachments", indexes = { @Index(name = "bill_id_idx", columnList = "bill_id") })
public class BillAttachments {

	public BillAttachments() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "bill_id")
	private int billId;

	@Column
	private String filename;

	@Column
	private String filetype;

	@Column
	private long size;

	@Transient
	private String file;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
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

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "BillAttachments [id=" + id + ", billId=" + billId + ", filename=" + filename + ", filetype=" + filetype
				+ ", size=" + size + "]";
	}

}
