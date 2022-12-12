package com.autolib.helpdesk.Agents.entity;

import java.util.List;

public class ProductRequest {

	private String emailId;

	private String currentAction;

	private Product product;

	private List<ProductsRawMaterials> productsRawMaterials;

	private RawMaterialRequest rawMaterialRequest;
	private List<RawMaterialRequestProducts> rawMaterialRequestProducts;

	public String getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public RawMaterialRequest getRawMaterialRequest() {
		return rawMaterialRequest;
	}

	public void setRawMaterialRequest(RawMaterialRequest rawMaterialRequest) {
		this.rawMaterialRequest = rawMaterialRequest;
	}

	public List<RawMaterialRequestProducts> getRawMaterialRequestProducts() {
		return rawMaterialRequestProducts;
	}

	public void setRawMaterialRequestProducts(List<RawMaterialRequestProducts> rawMaterialRequestProducts) {
		this.rawMaterialRequestProducts = rawMaterialRequestProducts;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ProductsRawMaterials> getProductsRawMaterials() {
		return productsRawMaterials;
	}

	public void setProductsRawMaterials(List<ProductsRawMaterials> productsRawMaterials) {
		this.productsRawMaterials = productsRawMaterials;
	}

}
