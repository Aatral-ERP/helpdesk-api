package com.autolib.helpdesk.Sales.model.Inputs;

import java.util.Date;
import java.util.List;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.Vendor;

public class BillPaymentSearchRequest {

	private List<Agent> agents;
	private List<Vendor> vendors;

	private String billId;
	private String referenceno;
	private String mode;
	private Date paymentModifiedDateFrom;
	private Date paymentModifiedDateTo;
	private Date paymentDateFrom;
	private Date paymentDateTo;
	

	public BillPaymentSearchRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Vendor> getVendors() {
		return vendors;
	}



	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}



	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Date getPaymentModifiedDateFrom() {
		return paymentModifiedDateFrom;
	}

	public void setPaymentModifiedDateFrom(Date paymentModifiedDateFrom) {
		this.paymentModifiedDateFrom = paymentModifiedDateFrom;
	}

	public Date getPaymentModifiedDateTo() {
		return paymentModifiedDateTo;
	}

	public void setPaymentModifiedDateTo(Date paymentModifiedDateTo) {
		this.paymentModifiedDateTo = paymentModifiedDateTo;
	}

	public Date getPaymentDateFrom() {
		return paymentDateFrom;
	}

	public void setPaymentDateFrom(Date paymentDateFrom) {
		this.paymentDateFrom = paymentDateFrom;
	}

	public Date getPaymentDateTo() {
		return paymentDateTo;
	}

	public void setPaymentDateTo(Date paymentDateTo) {
		this.paymentDateTo = paymentDateTo;
	}

	@Override
	public String toString() {
		return "BillPaymentSearchRequest [agents=" + agents + ", billId=" + billId + ", referenceno=" + referenceno
				+ ", mode=" + mode + ", paymentModifiedDateFrom=" + paymentModifiedDateFrom + ", paymentModifiedDateTo="
				+ paymentModifiedDateTo + ", paymentDateFrom=" + paymentDateFrom + ", paymentDateTo=" + paymentDateTo
				+ "]";
	}

	
}