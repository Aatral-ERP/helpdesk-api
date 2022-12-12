package com.autolib.helpdesk.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

public class GmailQuickstart {
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/cred-gmail-api.json";

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.

		InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();

		List<GoogleMailAsTicket> masts = new ArrayList<GoogleMailAsTicket>();

		Gmail.Users.Messages.List request = service.users().messages().list("me").setQ("in:inbox  after:2020/10/19");

		ListMessagesResponse messageResponse = request.execute();
		request.setPageToken(messageResponse.getNextPageToken());

		List<Message> messages = messageResponse.getMessages();
		for (Message msg : messages) {
			GoogleMailAsTicket mast = new GoogleMailAsTicket();
			try {
				System.out.println(msg.toPrettyString());

				Message message = service.users().messages().get("me", msg.getId()).execute();

				mast.setIdMail(message.getId());
				mast.setEmailId(getFromMailId(message));
				mast.setEmailUpdates(getMailCC(message));
				mast.setSubject(getMailSubject(message));

				System.out.println(message.getPayload().getMimeType());

				if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/alternative")) {
					String emailBody = StringUtils.newStringUtf8(
							Base64.decodeBase64(message.getPayload().getParts().get(0).getBody().getData()));
					mast.setSummary(emailBody);
				} else if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/mixed")) {
					MessagePart mps = message.getPayload().getParts().get(0);
					for (MessagePart mp : mps.getParts()) {
						if (mp.getMimeType().equalsIgnoreCase("text/plain")) {
							mast.setSummary(StringUtils.newStringUtf8(Base64.decodeBase64(mp.getBody().getData())));
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(mast.toString());
			masts.add(mast);
		}

	}

	static String getFromMailId(Message msg) {
		String mailFrom = "";
		try {
			for (Map<?, ?> header : msg.getPayload().getHeaders()) {
				if (header.get("name").equals("Return-Path")) {
					mailFrom = String.valueOf(header.get("value")).replaceAll("<", "").replaceAll(">", "");
				}
			}
		} catch (Exception e) {

		}
		return mailFrom;
	}

	static String getMailCC(Message msg) {
		String mailCC = "";
		try {
			for (Map<?, ?> header : msg.getPayload().getHeaders()) {
				if (header.get("name").equals("Cc")) {
					mailCC = String.valueOf(header.get("value")).replaceAll("<", "").replaceAll(">", "");
					mailCC = mailCC.replaceAll(",", ";").replaceAll(" ", "");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return mailCC;
	}

	static String getMailSubject(Message msg) {
		String MailSubject = "";
		try {
			for (Map<?, ?> header : msg.getPayload().getHeaders()) {
				if (header.get("name").equals("Subject")) {
					MailSubject = String.valueOf(header.get("value"));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return MailSubject;
	}

}