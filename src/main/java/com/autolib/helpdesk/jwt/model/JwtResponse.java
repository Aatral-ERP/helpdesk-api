package com.autolib.helpdesk.jwt.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String jwtrefreshtoken;
	private final String message;
	private final byte[] photo;

	public JwtResponse(String jwttoken, String jwtrefreshtoken, String message, byte[] photo) {
		this.jwttoken = jwttoken;
		this.message = message;
		this.jwtrefreshtoken = jwtrefreshtoken;
		this.photo = photo;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public String getMessage() {
		return message;
	}

	public String getRefreshtoken() {
		return jwtrefreshtoken;
	}

	public byte[] getPhoto() {
		return photo;
	}

}
