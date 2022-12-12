package com.autolib.helpdesk.Institutes.model;

import com.autolib.helpdesk.Agents.entity.Product;

public class AMCDetailResp {
	
	private AMCDetails AMCDetails;
	
	private Product Product;
	
	

	public AMCDetailResp(com.autolib.helpdesk.Institutes.model.AMCDetails aMCDetails,
			com.autolib.helpdesk.Agents.entity.Product product) {
		super();
		AMCDetails = aMCDetails;
		Product = product;
	}

	public AMCDetails getAMCDetails() {
		return AMCDetails;
	}

	public void setAMCDetails(AMCDetails aMCDetails) {
		AMCDetails = aMCDetails;
	}

	public Product getProduct() {
		return Product;
	}

	public void setProduct(Product product) {
		Product = product;
	}

	

}
