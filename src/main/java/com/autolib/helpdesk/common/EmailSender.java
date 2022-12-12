
package com.autolib.helpdesk.common;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Config.MailConfig.MailProperties;
import com.autolib.helpdesk.Config.MailConfig.MailSenderConfigurations;

@Service
public class EmailSender {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private MailSenderConfigurations mailConf;

	@Value("${spring.mail.username}")
	private String sendingMailId;

	public int sendmail(EmailModel emailModel) {
		logger.info("Sending Mail to :::" + emailModel);
		int i = 0;

		MailProperties mailProperties = mailConf.getProperties(emailModel.getSenderConf());

		if (mailProperties != null) {

			JavaMailSender javaMailSender = mailConf.getSender(emailModel.getSenderConf());

			if (javaMailSender != null) {
				i = sendmailImpl(emailModel, javaMailSender);
			} else {
				i = 2;
				logger.error("JavaMailSender not Found :: " + emailModel.getSenderConf());
			}
		} else {
			i = 2;
			logger.error("MailProperties not Found :: " + emailModel.getSenderConf());
		}

		return i;
	}

	private int sendmailImpl(EmailModel emailModel, JavaMailSender javaMailSender) {

		int i = 0;
		try {

			MimeMessage msg = javaMailSender.createMimeMessage();

			msg.setFrom(new InternetAddress(sendingMailId, emailModel.getFromName()));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailModel.getMailTo()));

			if (emailModel.getMailList().length > 0) {
				for (String email : emailModel.getMailList()) {
					if (Util.validateEmailID(email))
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
				}
			}

			msg.setSubject(emailModel.getMailSub());
			msg.setContent(emailModel.getMailText(), "text/html; charset=utf-8");

			if (!emailModel.getContent_path().isEmpty()) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(msg.getContent(), "text/html; charset=utf-8");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				MimeBodyPart attachPart = new MimeBodyPart();

				attachPart.attachFile(emailModel.getContent_path());
				multipart.addBodyPart(attachPart);
				msg.setContent(multipart);
			}

			if (emailModel.getContentPaths().size() > 0) {

				for (int k = 0; k < emailModel.getContentPaths().size(); k++) {

					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(msg.getContent(), "text/html; charset=utf-8");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					MimeBodyPart attachPart = new MimeBodyPart();

					attachPart.attachFile(emailModel.getContentPaths().get(k));
					multipart.addBodyPart(attachPart);
					msg.setContent(multipart);

				}
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					javaMailSender.send(msg);
					logger.info("Mail Sent ::" + emailModel.getMailTo());
				}
			}).start();

			i = 1;
		} catch (Exception Ex) {
			i = 0;
			Ex.printStackTrace();
		}

		return i;
	}

}
