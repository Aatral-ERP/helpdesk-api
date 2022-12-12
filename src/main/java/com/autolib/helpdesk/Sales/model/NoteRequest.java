/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.List;

/**
 * @author Kannadasan
 *
 */
public class NoteRequest {

	/**
	 * 
	 */
	public NoteRequest() {
		// TODO Auto-generated constructor stub
	}

	private Notes note;
	private List<NoteAttachments> noteAttachments;

	public Notes getNote() {
		return note;
	}

	public void setNote(Notes note) {
		this.note = note;
	}

	public List<NoteAttachments> getNoteAttachments() {
		return noteAttachments;
	}

	public void setNoteAttachments(List<NoteAttachments> noteAttachments) {
		this.noteAttachments = noteAttachments;
	}

}
