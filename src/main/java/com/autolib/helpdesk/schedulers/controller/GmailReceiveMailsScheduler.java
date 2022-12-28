package com.autolib.helpdesk.schedulers.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
import com.autolib.helpdesk.common.GmailServiceThroughRefreshToken;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;
import com.autolib.helpdesk.schedulers.repository.GoogleMailAsTicketRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

@Controller
@RestController
@CrossOrigin("*")
public class GmailReceiveMailsScheduler {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	GoogleMailAsTicketRepository gmRepo;
	@Autowired
	InstituteContactRepository icRepo;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	private final String APPLICATION_NAME = "HelpDesk";
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static Gmail service = null;

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private final String CREDENTIALS_FILE_PATH = "/cred-gmail-api.json";

//	@GetMapping("DeleteGmailReceiveMailsScheduler")
//	@Scheduled(cron = "35 12 * * * *")
	void delete() throws IOException, GeneralSecurityException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);
		System.out.println("DeleteGmailReceiveMailsScheduler::" + Util.sdfFormatter(cal.getTime()));
		gmRepo.deleteOldMails(cal.getTime());
	}

//	@GetMapping("GmailReceiveMailsScheduler")
//	@Scheduled(cron = "0 0/30 * * * *")
	void execute() throws IOException, GeneralSecurityException {

		getGmailService();

		logger.info("GmailReceiveMailsScheduler stars:::");

		List<GoogleMailAsTicket> masts = new ArrayList<GoogleMailAsTicket>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println(sdf.format(new Date()));
//		Gmail.Users.Messages.List request = service.users().messages().list("me")
//				.setQ("in:inbox after:2021/11/30 before:2021/12/3");
		
		Gmail.Users.Messages.List request = service.users().messages().list("me")
				.setQ("in:inbox after:2021/10/30 before:"+sdf.format(new Date()));

		ListMessagesResponse messageResponse = request.execute();
		request.setPageToken(messageResponse.getNextPageToken());

		List<Message> messages = messageResponse.getMessages();
		if (messages == null)
			return;

		for (Message msg : messages) {

			GoogleMailAsTicket mast = new GoogleMailAsTicket();
			try {
				System.out.println(msg.toPrettyString());

				Message message = service.users().messages().get("me", msg.getId()).execute();

				mast.setIdMail(message.getId());
				mast.setEmailId(getFromMailId(message));
				mast.setEmailUpdates(getMailCC(message));
				mast.setSubject(getMailSubject(message));
				String attachments = "";
				if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/alternative")) {
					for (MessagePart mp : message.getPayload().getParts()) {
						if (mp.getMimeType().equalsIgnoreCase("text/html")) {
							mast.setSummary(StringUtils
									.newStringUtf8(Base64.encodeBase64((Base64.decodeBase64(mp.getBody().getData())))));
						}
					}
				} else if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/mixed")) {
					System.out.println(message.getPayload().getParts().size());
					MessagePart mps = message.getPayload().getParts().get(0);
					for (MessagePart mp : mps.getParts()) {
						if (mp.getMimeType().equalsIgnoreCase("text/html")) {
							mast.setSummary(StringUtils
									.newStringUtf8(Base64.encodeBase64((Base64.decodeBase64(mp.getBody().getData())))));
						}
					}

					List<MessagePart> mp2 = message.getPayload().getParts();
					for (int k = 1; mp2.size() > k; k++) {
						MessagePart mp2s = mp2.get(k);
						attachments = attachments + mp2s.getFilename() + ";";
					}
				}
				mast.setAttachments(attachments);
			} catch (Exception e) {
				e.printStackTrace();
			}
			masts.add(mast);
		}
		saveTickets(masts);
	}

	public Gmail getGmailService() throws IOException, GeneralSecurityException {

		InputStream in = GmailServiceThroughRefreshToken.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Credential builder

		Credential authorize = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport())
				.setJsonFactory(JSON_FACTORY)
				.setClientSecrets(clientSecrets.getDetails().getClientId().toString(),
						clientSecrets.getDetails().getClientSecret().toString())
				.build().setAccessToken(getAccessToken()).setRefreshToken(
						"1//0g1uFdIz-n13qCgYIARAAGBASNwF-L9IrSmhRPH9yihPa97RRSrvAYSaAPcgdaAw44BD7anFgzBpcYTckX0nk9L3wLAsG6jOLZ-E");// Replace
																																	// this

		// Create Gmail service
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize).setApplicationName(APPLICATION_NAME)
				.build();

		return service;

	}

	String getAccessToken() {

		try {
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("grant_type", "refresh_token");
			params.put("client_id", "493032653944-fl1b2h0jv81gva0g630q53j637gal2br.apps.googleusercontent.com"); // Replace
																													// this
			params.put("client_secret", "AgEKzNHzcpZ-sldNCXkNViFJ"); // Replace this
			params.put("refresh_token",
					"1//0g1uFdIz-n13qCgYIARAAGBASNwF-L9IrSmhRPH9yihPa97RRSrvAYSaAPcgdaAw44BD7anFgzBpcYTckX0nk9L3wLAsG6jOLZ-E"); // Replace
																																// this

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

	String getFromMailId(Message msg) {
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

	String getMailCC(Message msg) {
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

	String getMailSubject(Message msg) {
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

	void saveTickets(List<GoogleMailAsTicket> tickets) {
		for (GoogleMailAsTicket tkt : tickets) {
			System.out.println(tkt);
			InstituteContact ic = icRepo.findByEmailId(tkt.getEmailId());
			if (ic != null) {
				tkt.setInstitute(new Institute(ic.getInstituteId(), null, null, null, null));
				if (gmRepo.findByIdMail(tkt.getIdMail()) == null)
					gmRepo.save(tkt);
			}
		}
	}

}
