/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.List;

import javax.persistence.Transient;

import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;

/**
 * @author Kannadasan
 *
 */
public class DealRequest {

	/**
	 * 
	 */
	public DealRequest() {
		// TODO Auto-generated constructor stub
	}

	public DealRequest(Deal deal, DealQuotation quote) {
		this.deal = deal;
		this.dealQuotation = quote;
	}

	public DealRequest(Deal deal, DealPurchaseOrder po) {
		this.deal = deal;
		this.purchaseOrder = po;
	}

	public DealRequest(Deal deal, DealSalesOrder so) {
		this.deal = deal;
		this.salesOrder = so;
	}

	public DealRequest(Deal deal, DealProformaInvoice pi) {
		this.deal = deal;
		this.dealProformaInvoice = pi;
	}

	public DealRequest(Deal deal, DealInvoice inv) {
		this.deal = deal;
		this.dealInvoice = inv;
	}

	private Deal deal;

	private List<DealProducts> dealProducts;

	private List<InstituteContact> instituteContacts;

	private DealQuotation dealQuotation;

	private DealSalesOrder salesOrder;

	private DealPurchaseOrder purchaseOrder;

	private DealInvoice dealInvoice;

	private DealProformaInvoice dealProformaInvoice;

	private DealPayments payment;

	private String exportType = "";
	private String templateName = "";
	private boolean addRoundSeal = false;
	private boolean addFullSeal = false;
	private boolean addSign = false;
	private boolean detailedPricing = true;
	private String signatureBy = "";
	private String preamble = "";
	private String preamblePosition = "Preamble First";

	private String designation = "";
	private String fileAsBase64 = "";
	private String filename = "";
	private String receiptContent = "";
	private String paymentDate = "";
	private String billingAddress = "";

	public boolean isDetailedPricing() {
		return detailedPricing;
	}

	public void setDetailedPricing(boolean detailedPricing) {
		this.detailedPricing = detailedPricing;
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

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public List<DealProducts> getDealProducts() {
		return dealProducts;
	}

	public void setDealProducts(List<DealProducts> dealProducts) {
		this.dealProducts = dealProducts;
	}

	public List<InstituteContact> getInstituteContacts() {
		return instituteContacts;
	}

	public void setInstituteContacts(List<InstituteContact> instituteContacts) {
		this.instituteContacts = instituteContacts;
	}

	public DealQuotation getDealQuotation() {
		return dealQuotation;
	}

	public void setDealQuotation(DealQuotation dealQuotation) {
		this.dealQuotation = dealQuotation;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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

	public DealSalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(DealSalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public DealInvoice getDealInvoice() {
		return dealInvoice;
	}

	public void setDealInvoice(DealInvoice dealInvoice) {
		this.dealInvoice = dealInvoice;
	}

	public DealProformaInvoice getDealProformaInvoice() {
		return dealProformaInvoice;
	}

	public void setDealProformaInvoice(DealProformaInvoice dealProformaInvoice) {
		this.dealProformaInvoice = dealProformaInvoice;
	}

	public String getSignatureBy() {
		return signatureBy;
	}

	public void setSignatureBy(String signatureBy) {
		this.signatureBy = signatureBy;
	}

	public String getPreamble() {
		return preamble;
	}

	public void setPreamble(String preamble) {
		this.preamble = preamble;
	}

	public String getPreamblePosition() {
		return preamblePosition;
	}

	public void setPreamblePosition(String preamblePosition) {
		this.preamblePosition = preamblePosition;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Transient
	public String getBillingToAddress() {
		String billing_to = "";
		if (this.getDeal().getBillingTo() != null && !this.getDeal().getBillingTo().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingTo() + ",<br>";
		if (this.getDeal().getInstitute().getInstituteName() != null
				&& !this.getDeal().getInstitute().getInstituteName().isEmpty())
			billing_to = billing_to + this.getDeal().getInstitute().getInstituteName() + ",<br>";
		if (this.getDeal().getBillingStreet1() != null && !this.getDeal().getBillingStreet1().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingStreet1() + ",<br>";
		if (this.getDeal().getBillingStreet2() != null && !this.getDeal().getBillingStreet2().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingStreet2() + ",<br>";

		if (this.getDeal().getBillingCity() != null && !this.getDeal().getBillingCity().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingCity() + ", ";

		if (this.getDeal().getBillingState() != null && !this.getDeal().getBillingState().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingState() + ", ";

		if (this.getDeal().getBillingCountry() != null && !this.getDeal().getBillingCountry().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingCountry() + " - ";

		if (this.getDeal().getBillingZIPCode() != null && !this.getDeal().getBillingZIPCode().isEmpty())
			billing_to = billing_to + this.getDeal().getBillingZIPCode() + ".<br>";

		if (this.getDeal().getInstitute().getGstno() != null && !this.getDeal().getInstitute().getGstno().isEmpty())
			billing_to = billing_to + "GSTIN : " + this.getDeal().getInstitute().getGstno();

		return billing_to;
	}

	@Transient
	public String getShippingToAddress() {
		String shipping_to = "";
		if (this.getDeal().getShippingTo() != null && !this.getDeal().getShippingTo().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingTo() + ",<br>";
		if (this.getDeal().getInstitute().getInstituteName() != null
				&& !this.getDeal().getInstitute().getInstituteName().isEmpty())
			shipping_to = shipping_to + this.getDeal().getInstitute().getInstituteName() + ",<br>";
		if (this.getDeal().getShippingStreet1() != null && !this.getDeal().getShippingStreet1().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingStreet1() + ",<br>";
		if (this.getDeal().getShippingStreet2() != null && !this.getDeal().getShippingStreet2().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingStreet2() + ",<br>";

		if (this.getDeal().getShippingCity() != null && !this.getDeal().getShippingCity().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingCity() + ", ";

		if (this.getDeal().getShippingState() != null && !this.getDeal().getShippingState().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingState() + ", ";

		if (this.getDeal().getShippingCountry() != null && !this.getDeal().getShippingCountry().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingCountry() + " - ";

		if (this.getDeal().getShippingZIPCode() != null && !this.getDeal().getShippingZIPCode().isEmpty())
			shipping_to = shipping_to + this.getDeal().getShippingZIPCode() + ".<br>";

		if (this.getDeal().getInstitute().getGstno() != null && !this.getDeal().getInstitute().getGstno().isEmpty())
			shipping_to = shipping_to + "GSTIN : " + this.getDeal().getInstitute().getGstno();

		return shipping_to;
	}

	public DealPayments getPayment() {
		return payment;
	}

	public void setPayment(DealPayments payment) {
		this.payment = payment;
	}

	public DealPurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(DealPurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getReceiptContent() {
		return receiptContent;
	}

	public void setReceiptContent(String receiptContent) {
		this.receiptContent = receiptContent;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String isDiscountApplied() {
		return this.dealProducts.stream().filter(dp -> dp.getRateAmount() != dp.getPrice()).findFirst().isPresent()
				? "YES"
				: "NO";
	}

	@Override
	public String toString() {
		return "DealRequest [deal=" + deal + ", dealProducts=" + dealProducts + ", instituteContacts="
				+ instituteContacts + ", dealQuotation=" + dealQuotation + ", salesOrder=" + salesOrder
				+ ", purchaseOrder=" + purchaseOrder + ", dealInvoice=" + dealInvoice + ", dealProformaInvoice="
				+ dealProformaInvoice + ", payment=" + payment + ", templateName=" + templateName + ", addRoundSeal="
				+ addRoundSeal + ", addFullSeal=" + addFullSeal + ", addSign=" + addSign + ", signatureBy="
				+ signatureBy + ", preamble=" + preamble + ", preamblePosition=" + preamblePosition + ", designation="
				+ designation + ", fileAsBase64=" + fileAsBase64 + ", filename=" + filename + ", receiptContent="
				+ receiptContent + ", paymentDate=" + paymentDate + ", billingAddress=" + billingAddress + "]";
	}

}
