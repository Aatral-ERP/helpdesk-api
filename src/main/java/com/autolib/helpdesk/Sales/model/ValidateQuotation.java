/**
 * 
 */
package com.autolib.helpdesk.Sales.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.common.Util;

/**
 * @author Kannadasan
 *
 */
public class ValidateQuotation {

	/**
	 * 
	 */
	public ValidateQuotation() {
		// TODO Auto-generated constructor stub
	}

	public static List<Map<String, String>> validateSaveDeal(DealRequest deal) {
		List<Map<String, String>> resp = new ArrayList<>();

		if (deal.getDeal().getInstitute() == null) {
			resp.add(Util.invalidMessage("Institute Cannot be empty"));
		}
		if (deal.getDeal().getBillingTo().isEmpty() && deal.getDeal().getBillingStreet1().isEmpty()
				&& deal.getDeal().getBillingStreet2().isEmpty() && deal.getDeal().getBillingCity().isEmpty()
				&& deal.getDeal().getBillingState().isEmpty() && deal.getDeal().getBillingCountry().isEmpty()
				&& deal.getDeal().getBillingZIPCode().isEmpty()) {
			resp.add(Util.invalidMessage("Billing Address should not be empty."));
		}

		if (deal.getDeal().getShippingTo().isEmpty() && deal.getDeal().getShippingStreet1().isEmpty()
				&& deal.getDeal().getShippingStreet2().isEmpty() && deal.getDeal().getShippingCity().isEmpty()
				&& deal.getDeal().getShippingState().isEmpty() && deal.getDeal().getShippingCountry().isEmpty()
				&& deal.getDeal().getShippingZIPCode().isEmpty()) {
			resp.add(Util.invalidMessage("Shipping Address should not be empty."));
		}

		if (deal.getDealProducts() == null || deal.getDealProducts().size() == 0) {
			resp.add(Util.invalidMessage("Atleast One Product should be Added."));
		}

		for (DealProducts quoteProd : deal.getDealProducts()) {
			if (quoteProd.getProductId() == 0)
				resp.add(Util.invalidMessage("One of the Product is Empty."));
			if (quoteProd.getName() == null || quoteProd.getName().isEmpty())
				resp.add(Util.invalidMessage("One of the ProductName is Empty."));
			if (quoteProd.getPrice() == 0)
				resp.add(Util.invalidMessage("One of the Product Price is 0."));
			if (quoteProd.getDiscount() > quoteProd.getPrice())
				resp.add(Util.invalidMessage(quoteProd.getName() + " discount is greater than Product Price."));
			if (quoteProd.getQuantity() == 0)
				resp.add(Util.invalidMessage(quoteProd.getName() + " quantity should be atleast 1."));
		}

		return resp;

	}

}
