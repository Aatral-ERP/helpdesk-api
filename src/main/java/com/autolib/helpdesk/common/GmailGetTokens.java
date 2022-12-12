package com.autolib.helpdesk.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

public class GmailGetTokens {

	/*
	 * 1.Get code : https://accounts.google.com/o/oauth2/v2/auth?
	 * scope=https://mail.google.com& access_type=offline&
	 * redirect_uri=http://localhost& response_type=code& client_id=[Client ID]
	 * 
	 * 2. Get access_token and refresh_token curl \ --request POST \ --data
	 * "code=[Authentcation code from authorization link]&client_id=[Application Client Id]&client_secret=[Application Client Secret]&redirect_uri=http://localhost&grant_type=authorization_code"
	 * \ https://accounts.google.com/o/oauth2/token
	 * 
	 * 3.Get new access_token using refresh_token curl \ --request POST \ --data
	 * "client_id=[your_client_id]&client_secret=[your_client_secret]&refresh_token=[refresh_token]&grant_type=refresh_token"
	 * \ https://accounts.google.com/o/oauth2/token
	 * 
	 */
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String user = "me";
	static Gmail service = null;
	private static final String CREDENTIALS_FILE_PATH = "/cred-gmail-api.json";
	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	public static void main(String[] args) throws IOException, GeneralSecurityException {

		Credential cred = getCredentials(GoogleNetHttpTransport.newTrustedTransport());
		System.out.println("AccessToken : " + cred.getAccessToken());
		System.out.println("RefreshToken : " + cred.getRefreshToken());

	}

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.

		InputStream in = GmailGetTokens.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(4200).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	private static String getAccessToken() {

		try {
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("grant_type", "refresh_token");
			params.put("client_id", "YOUR_CLIENT_ID"); // Replace this
			params.put("client_secret", "YOUR_CLIENT_SECRET"); // Replace this
			params.put("refresh_token", "YOUR_REFRESH_TOKEN"); // Replace this

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0) {
					postData.append('&');
				}
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.getOutputStream().write(postDataBytes);

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				buffer.append(line);
			}

			JSONObject json = new JSONObject(buffer.toString());
			String accessToken = json.getString("access_token");
			return accessToken;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}