package com.autolib.helpdesk.jwt.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;
	private String googleEmailId;
	private String googleUserName;
	private String googleId;
	private String googleToken;

	// need default constructor for JSON Parsing
	public JwtRequest() {

	}

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGoogleEmailId() {
		return googleEmailId;
	}

	public void setGoogleEmailId(String googleEmailId) {
		this.googleEmailId = googleEmailId;
	}

	public String getGoogleUserName() {
		return googleUserName;
	}

	public void setGoogleUserName(String googleUserName) {
		this.googleUserName = googleUserName;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getGoogleToken() {
		return googleToken;
	}

	public void setGoogleToken(String googleToken) {
		this.googleToken = googleToken;
	}

}
