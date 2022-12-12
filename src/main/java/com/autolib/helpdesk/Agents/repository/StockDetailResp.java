package com.autolib.helpdesk.Agents.repository;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.entity.StockEntry;
import com.autolib.helpdesk.Institutes.model.AMCDetails;

public class StockDetailResp {
	
	private Product Product;
	private StockEntry StockEntry;
	
	
	public StockDetailResp(com.autolib.helpdesk.Agents.entity.StockEntry stockEntry,
			com.autolib.helpdesk.Agents.entity.Product product) {
		super();
		StockEntry = stockEntry;
		Product = product;
	}
	
	public Product getProduct() {
		return Product;
	}

	public void setProduct(Product product) {
		Product = product;
	}

	public StockEntry getStockEntry() {
		return StockEntry;
	}

	public void setStockEntry(StockEntry stockEntry) {
		StockEntry = stockEntry;
	}

	

}
