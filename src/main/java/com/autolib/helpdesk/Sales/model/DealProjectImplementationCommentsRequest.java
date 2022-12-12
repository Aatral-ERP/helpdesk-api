/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.List;

/**
 * @author Kannadasan
 *
 */
public class DealProjectImplementationCommentsRequest {

	/**
	 * 
	 */
	public DealProjectImplementationCommentsRequest() {
		// TODO Auto-generated constructor stub
	}

	private DealProjectImplementationComments comment;

	private List<DealProjectImplementationCommentsAttachments> attachments;

	public DealProjectImplementationComments getComment() {
		return comment;
	}

	public void setComment(DealProjectImplementationComments comment) {
		this.comment = comment;
	}

	public List<DealProjectImplementationCommentsAttachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DealProjectImplementationCommentsAttachments> attachments) {
		this.attachments = attachments;
	}

}
