package com.autolib.helpdesk.Agents.entity;

import java.util.List;

public class RawMaterialRequestResponse {

	private RawMaterialRequest rawMaterialRequest;

	private List<RawMaterialRequestProducts> rawMaterialRequestProducts;

	public RawMaterialRequestResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RawMaterialRequestResponse(RawMaterialRequest rawMaterialRequest,
			List<RawMaterialRequestProducts> rawMaterialRequestProducts) {

		this.rawMaterialRequest = rawMaterialRequest;
		this.rawMaterialRequestProducts = rawMaterialRequestProducts;

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

}
