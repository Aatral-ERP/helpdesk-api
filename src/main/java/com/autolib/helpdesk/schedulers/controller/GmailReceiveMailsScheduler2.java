//package com.autolib.helpdesk.schedulers.controller;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.autolib.helpdesk.Institutes.model.Institute;
//import com.autolib.helpdesk.Institutes.model.InstituteContact;
//import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
//import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;
//import com.autolib.helpdesk.schedulers.repository.GoogleMailAsTicketRepository;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.Base64;
//import com.google.api.client.util.StringUtils;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.Gmail.Users.Messages.Attachments.Get;
//import com.google.api.services.gmail.GmailScopes;
//import com.google.api.services.gmail.model.ListMessagesResponse;
//import com.google.api.services.gmail.model.Message;
//import com.google.api.services.gmail.model.MessagePart;
//import com.google.api.services.gmail.model.MessagePartBody;
//
//@RequestMapping("scheduler")
//@Controller
//@RestController
//@CrossOrigin("*")
//public class GmailReceiveMailsScheduler {
//
//	private final Logger logger = LogManager.getLogger(this.getClass());
//
//	@Autowired
//	GoogleMailAsTicketRepository gmRepo;
//	@Autowired
//	InstituteContactRepository icRepo;
//
//	@Value("${al.ticket.content-path}")
//	private String contentPath;
//
//	private final String APPLICATION_NAME = "Gmail API Java Quickstart";
//	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//	private final String TOKENS_DIRECTORY_PATH = "tokens";
//
//	/**
//	 * Global instance of the scopes required by this quickstart. If modifying these
//	 * scopes, delete your previously saved tokens/ folder.
//	 */
//	private final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
//	private final String CREDENTIALS_FILE_PATH = "/cred-gmail-api.json";
//
//	/**
//	 * Creates an authorized Credential object.
//	 * 
//	 * @param HTTP_TRANSPORT The network HTTP Transport.
//	 * @return An authorized Credential object.
//	 * @throws IOException If the credentials.json file cannot be found.
//	 */
//
//	@GetMapping("GmailReceiveMailsScheduler")
//	@Scheduled(cron = "0 0/10 * * * *")
//	void execute() throws IOException, GeneralSecurityException {
//		logger.info("GmailReceiveMailsScheduler stars:::");
//
//		// Build a new authorized API client service.
//		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//				.setApplicationName(APPLICATION_NAME).build();
//
//		List<GoogleMailAsTicket> masts = new ArrayList<GoogleMailAsTicket>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		System.out.println(sdf.format(new Date()));
//		Gmail.Users.Messages.List request = service.users().messages().list("me")
//				.setQ("in:inbox after:" + sdf.format(new Date()));
//
//		ListMessagesResponse messageResponse = request.execute();
//		request.setPageToken(messageResponse.getNextPageToken());
//
//		List<Message> messages = messageResponse.getMessages();
//		for (Message msg : messages) {
//			GoogleMailAsTicket mast = new GoogleMailAsTicket();
//			try {
//				System.out.println(msg.toPrettyString());
//
//				Message message = service.users().messages().get("me", msg.getId()).execute();
//
//				mast.setIdMail(message.getId());
//				mast.setEmailId(getFromMailId(message));
//				mast.setEmailUpdates(getMailCC(message));
//				mast.setSubject(getMailSubject(message));
//				String attachments = "";
//				if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/alternative")) {
//					String emailBody = StringUtils.newStringUtf8(
//							Base64.decodeBase64(message.getPayload().getParts().get(0).getBody().getData()));
//					mast.setSummary(emailBody);
//				} else if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/mixed")) {
//					System.out.println(message.getPayload().getParts().size());
//					MessagePart mps = message.getPayload().getParts().get(0);
//					for (MessagePart mp : mps.getParts()) {
//						if (mp.getMimeType().equalsIgnoreCase("text/plain")) {
//							mast.setSummary(StringUtils.newStringUtf8(Base64.decodeBase64(mp.getBody().getData())));
//						}
//					}
//
//					List<MessagePart> mp2 = message.getPayload().getParts();
//					for (int k = 1; mp2.size() > k; k++) {
//						MessagePart mp2s = mp2.get(k);
//						String attachId = mp2s.getBody().getAttachmentId();
//						Get mpb = service.users().messages().attachments().get("me", mast.getIdMail(), attachId);
//						MessagePartBody mpd = mpb.execute();
//						byte[] data = Base64.decodeBase64(mpd.getData());
//						File directory = new File(contentPath + "_google_email_attachments/" + mast.getIdMail() + "/");
//						System.out.println(directory.getAbsolutePath());
//						if (!directory.exists()) {
//							System.out.println("Directory created::" + directory.getAbsolutePath());
//							directory.mkdir();
//						}
//						File convertFile = new File(directory.getAbsolutePath() + "/" + mp2s.getFilename());
//						convertFile.createNewFile();
//						FileOutputStream fout = new FileOutputStream(convertFile);
//						fout.write(data);
//						fout.close();
//						attachments = attachments + mp2s.getFilename() + ";";
//					}
//				}
//				mast.setAttachments(attachments);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			masts.add(mast);
//		}
//		saveTickets(masts);
//	}
//
//	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//		// Load client secrets.
//
//		InputStream in = this.getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
//		if (in == null) {
//			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//		}
//		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//		// Build flow and trigger user authorization request.
//		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
//				clientSecrets, SCOPES)
//						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//						.setAccessType("offline").build();
//		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(4200).build();
//		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//	}
//
//	String getFromMailId(Message msg) {
//		String mailFrom = "";
//		try {
//			for (Map<?, ?> header : msg.getPayload().getHeaders()) {
//				if (header.get("name").equals("Return-Path")) {
//					mailFrom = String.valueOf(header.get("value")).replaceAll("<", "").replaceAll(">", "");
//				}
//			}
//		} catch (Exception e) {
//
//		}
//		return mailFrom;
//	}
//
//	String getMailCC(Message msg) {
//		String mailCC = "";
//		try {
//			for (Map<?, ?> header : msg.getPayload().getHeaders()) {
//				if (header.get("name").equals("Cc")) {
//					mailCC = String.valueOf(header.get("value")).replaceAll("<", "").replaceAll(">", "");
//					mailCC = mailCC.replaceAll(",", ";").replaceAll(" ", "");
//				}
//			}
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		return mailCC;
//	}
//
//	String getMailSubject(Message msg) {
//		String MailSubject = "";
//		try {
//			for (Map<?, ?> header : msg.getPayload().getHeaders()) {
//				if (header.get("name").equals("Subject")) {
//					MailSubject = String.valueOf(header.get("value"));
//				}
//			}
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		return MailSubject;
//	}
//
//	void saveTickets(List<GoogleMailAsTicket> tickets) {
//		for (GoogleMailAsTicket tkt : tickets) {
//			System.out.println(tkt);
//			InstituteContact ic = icRepo.findByEmailId(tkt.getEmailId());
//			if (ic != null) {
//				tkt.setInstitute(new Institute(ic.getInstituteId(), null, null, null, null));
//				if (gmRepo.findByIdMail(tkt.getIdMail()) == null)
//					gmRepo.save(tkt);
//			}
//		}
//	}
//
//}
