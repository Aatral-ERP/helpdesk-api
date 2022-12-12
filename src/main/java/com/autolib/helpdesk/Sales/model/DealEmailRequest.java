package com.autolib.helpdesk.Sales.model;

import java.util.List;

public class DealEmailRequest {

	public DealEmailRequest() {
		// TODO Auto-generated constructor stub
	}

	private DealEmail dealEmail;

	private List<DealEmailAttachments> dealEmailAttachments;

	public DealEmail getDealEmail() {
		return dealEmail;
	}

	public void setDealEmail(DealEmail dealEmail) {
		this.dealEmail = dealEmail;
	}

	public List<DealEmailAttachments> getDealEmailAttachments() {
		return dealEmailAttachments;
	}

	public void setDealEmailAttachments(List<DealEmailAttachments> dealEmailAttachments) {
		this.dealEmailAttachments = dealEmailAttachments;
	}

}
