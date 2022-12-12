package com.autolib.helpdesk.jwt.model;

import java.io.Serializable;

public class JwtRefreshTokenRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String token;
	private String refreshtoken;

	// need default constructor for JSON Parsing
	public JwtRefreshTokenRequest() {
		super();
	}

	public JwtRefreshTokenRequest(String token, String refreshtoken) {
		this.token = token;
		this.refreshtoken = refreshtoken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshtoken() {
		return this.refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	@Override
	public String toString() {
		return "JwtRefreshTokenRequest [token=" + token + ", refreshtoken=" + refreshtoken + "]";
	}

}
