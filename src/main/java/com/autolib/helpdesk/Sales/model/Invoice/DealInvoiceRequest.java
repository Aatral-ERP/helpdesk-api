package com.autolib.helpdesk.Sales.model.Invoice;

import java.util.List;

import javax.persistence.Transient;

import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealDeliveryChallan;

public class DealInvoiceRequest {

	public DealInvoiceRequest() {
		// TODO Auto-generated constructor stub
	}

	private DealInvoice dealInvoice;

	private Deal deal;

	private DealDeliveryChallan deliveryChallan;

	private List<DealInvoiceProducts> dealInvoiceProducts;

	private List<InstituteContact> instituteContacts;

	private String exportType = "";
	private String templateName = "";
	private boolean addRoundSeal = false;
	private boolean addFullSeal = false;
	private boolean addSign = false;
	private String signatureBy = "";
	private String designation = "";
	private String fileAsBase64 = "";
	private String filename = "";
	private String receiptContent = "";

	private boolean detailedPricing = false;

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public DealDeliveryChallan getDeliveryChallan() {
		return deliveryChallan;
	}

	public void setDeliveryChallan(DealDeliveryChallan deliveryChallan) {
		this.deliveryChallan = deliveryChallan;
	}

	public String getExportType() {
		if (exportType == null || exportType.isEmpty())
			return "PDF";
		else
			return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public boolean isDetailedPricing() {
		return detailedPricing;
	}

	public void setDetailedPricing(boolean detailedPricing) {
		this.detailedPricing = detailedPricing;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean getAddRoundSeal() {
		return addRoundSeal;
	}

	public void setAddRoundSeal(boolean addRoundSeal) {
		this.addRoundSeal = addRoundSeal;
	}

	public boolean getAddFullSeal() {
		return addFullSeal;
	}

	public void setAddFullSeal(boolean addFullSeal) {
		this.addFullSeal = addFullSeal;
	}

	public boolean getAddSign() {
		return addSign;
	}

	public void setAddSign(boolean addSign) {
		this.addSign = addSign;
	}

	public String getSignatureBy() {
		return signatureBy;
	}

	public void setSignatureBy(String signatureBy) {
		this.signatureBy = signatureBy;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFileAsBase64() {
		return fileAsBase64;
	}

	public void setFileAsBase64(String fileAsBase64) {
		this.fileAsBase64 = fileAsBase64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getReceiptContent() {
		return receiptContent;
	}

	public void setReceiptContent(String receiptContent) {
		this.receiptContent = receiptContent;
	}

	public DealInvoice getDealInvoice() {
		return dealInvoice;
	}

	public void setDealInvoice(DealInvoice dealInvoice) {
		this.dealInvoice = dealInvoice;
	}

	public List<InstituteContact> getInstituteContacts() {
		return instituteContacts;
	}

	public void setInstituteContacts(List<InstituteContact> instituteContacts) {
		this.instituteContacts = instituteContacts;
	}

	public List<DealInvoiceProducts> getDealInvoiceProducts() {
		return dealInvoiceProducts;
	}

	public void setDealInvoiceProducts(List<DealInvoiceProducts> dealInvoiceProducts) {
		this.dealInvoiceProducts = dealInvoiceProducts;
	}

	@Transient
	public String getBillingToAddress() {
		String billing_to = "";
		if (this.getDeal().getBillingTo() != null && !this.getDeal().getBillingTo().equals(""))
			billing_to = billing_to + this.getDeal().getBillingTo() + ",<br>";
		if (this.getDeal().getInstitute().getInstituteName() != null
				&& !this.getDeal().getInstitute().getInstituteName().equals(""))
			billing_to = billing_to + this.getDeal().getInstitute().getInstituteName() + ",<br>";
		if (this.getDeal().getBillingStreet1() != null && !this.getDeal().getBillingStreet1().equals(""))
			billing_to = billing_to + this.getDeal().getBillingStreet1() + ",<br>";
		if (this.getDeal().getBillingStreet2() != null && !this.getDeal().getBillingStreet2().equals(""))
			billing_to = billing_to + this.getDeal().getBillingStreet2() + ",<br>";

		if (this.getDeal().getBillingCity() != null && !this.getDeal().getBillingCity().equals(""))
			billing_to = billing_to + this.getDeal().getBillingCity() + ", ";

		if (this.getDeal().getBillingState() != null && !this.getDeal().getBillingState().equals(""))
			billing_to = billing_to + this.getDeal().getBillingState() + ", ";

		if (this.getDeal().getBillingCountry() != null && !this.getDeal().getBillingCountry().equals(""))
			billing_to = billing_to + this.getDeal().getBillingCountry() + " - ";

		if (this.getDeal().getBillingZIPCode() != null && !this.getDeal().getBillingZIPCode().equals(""))
			billing_to = billing_to + this.getDeal().getBillingZIPCode() + ".<br>";

		if (this.getDeal().getInstitute().getGstno() != null && !this.getDeal().getInstitute().getGstno().equals(""))
			billing_to = billing_to + "GSTIN : " + this.getDeal().getInstitute().getGstno();

		return billing_to;
	}

	@Transient
	public String getShippingToAddress() {
		String shipping_to = "";
		if (this.getDeal().getShippingTo() != null && !this.getDeal().getShippingTo().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingTo() + ",<br>";
		if (this.getDeal().getInstitute().getInstituteName() != null
				&& !this.getDeal().getInstitute().getInstituteName().equals(""))
			shipping_to = shipping_to + this.getDeal().getInstitute().getInstituteName() + ",<br>";
		if (this.getDeal().getShippingStreet1() != null && !this.getDeal().getShippingStreet1().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingStreet1() + ",<br>";
		if (this.getDeal().getShippingStreet2() != null && !this.getDeal().getShippingStreet2().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingStreet2() + ",<br>";

		if (this.getDeal().getShippingCity() != null && !this.getDeal().getShippingCity().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingCity() + ", ";

		if (this.getDeal().getShippingState() != null && !this.getDeal().getShippingState().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingState() + ", ";

		if (this.getDeal().getShippingCountry() != null && !this.getDeal().getShippingCountry().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingCountry() + " - ";

		if (this.getDeal().getShippingZIPCode() != null && !this.getDeal().getShippingZIPCode().equals(""))
			shipping_to = shipping_to + this.getDeal().getShippingZIPCode() + ".<br>";

		if (this.getDeal().getInstitute().getGstno() != null && !this.getDeal().getInstitute().getGstno().equals(""))
			shipping_to = shipping_to + "GSTIN : " + this.getDeal().getInstitute().getGstno();

		return shipping_to;
	}

	public String isDiscountApplied() {
		return this.dealInvoiceProducts.stream().filter(dip -> dip.getRateAmount() != dip.getPrice()).findFirst()
				.isPresent() ? "YES" : "NO";
	}

	@Override
	public String toString() {
		return "DealInvoiceRequest [dealInvoice=" + dealInvoice + ", deal=" + deal + ", deliveryChallan="
				+ deliveryChallan + ", dealInvoiceProducts=" + dealInvoiceProducts + ", instituteContacts="
				+ instituteContacts + ", exportType=" + exportType + ", templateName=" + templateName
				+ ", addRoundSeal=" + addRoundSeal + ", addFullSeal=" + addFullSeal + ", addSign=" + addSign
				+ ", signatureBy=" + signatureBy + ", designation=" + designation + ", fileAsBase64=" + fileAsBase64
				+ ", filename=" + filename + ", receiptContent=" + receiptContent + ", detailedPricing="
				+ detailedPricing + "]";
	}

}
