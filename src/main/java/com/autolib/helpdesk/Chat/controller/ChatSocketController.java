package com.autolib.helpdesk.Chat.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.autolib.helpdesk.Chat.entity.LastMessageDetails;
import com.autolib.helpdesk.Chat.entity.Message;
import com.autolib.helpdesk.Chat.entity.MessageMasComments;
import com.autolib.helpdesk.Chat.repository.LastMessageDetailsRepository;
import com.autolib.helpdesk.Chat.repository.MessageMasCommentsRepository;
import com.autolib.helpdesk.Chat.repository.MessageRepository;
import com.autolib.helpdesk.Notification.PushNotification;
import com.autolib.helpdesk.Notification.TokenRepository;

@CrossOrigin("*")
@Controller
public class ChatSocketController {
	private final Logger log = LogManager.getLogger(this.getClass());
	@Autowired
	MessageRepository msgRepo;
	@Autowired
	MessageMasCommentsRepository msgCommentsRepo;
	@Autowired
	SimpMessagingTemplate smTemp;
	@Autowired
	private TokenRepository tokenRepo;
	@Autowired
	LastMessageDetailsRepository lstmsgRepo;

	@MessageMapping("/hello/{chatid}")
	public Message greeting(Message message, @DestinationVariable("chatid") String chatid) throws Exception {

		try {
			System.out.println("greeting  starts:::::::" + chatid);
			message = msgRepo.save(message);

			String[] chatId = message.getChatId().split(Pattern.quote("||"));

			this.smTemp.convertAndSend("/topic/greetings/" + chatId[0], message);
			this.smTemp.convertAndSend("/topic/greetings/" + chatId[1], message);
			sendPushNotify(message, chatId);
			System.out.println(message.toString());

			UpdateLastMessageDetails(message);

		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
		return message;
	}

	@MessageMapping("/librarymsg/{msgcode}")
	public MessageMasComments librarymsg(MessageMasComments messageComments,
			@DestinationVariable("msgcode") String chatid) throws Exception {
		System.out.println("librarymsg  starts:::::::" + chatid);
		try {
			messageComments = msgCommentsRepo.save(messageComments);
			this.smTemp.convertAndSend("/topic/librarymessages/" + messageComments.getMsgcode(), messageComments);
		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
		return messageComments;
	}

	void sendPushNotify(Message message, String[] chatId) {

		for (String cid : chatId) {
			if (!cid.equalsIgnoreCase(message.getMessageBy())) {
				com.autolib.helpdesk.Notification.Token token = tokenRepo.findByUserId(cid);
				if (token != null) {
					if (token.getToken() != "" && token.getNotify().equals("YES")) {
						Map<String, String> notification = new HashMap<>();

						Map<String, Object> data = new HashMap<>();

						data.put("user_id", cid);
						data.put("action", "ADD_MESSAGE");

						data.put("notification_type", "ChatNotification");

						notification.put("title", message.getMessageBy().toUpperCase() + " messaged you");
						notification.put("body", message.getMessage());

						try {
							PushNotification.sentNotification(token.getToken(), data, notification);
						} catch (Exception Ex) {
							Ex.printStackTrace();
						}
					}
				}
			}
		}
	}

 void UpdateLastMessageDetails(Message message) {
		LastMessageDetails lm = lstmsgRepo.findByChatId(message.getChatId());

		if (lm == null) {
			lm = new LastMessageDetails();
			lm.setChatId(message.getChatId());
			lm.setUserMailId(message.getUserMailId());
			lm.setAgentMailId(message.getAgentMailId());
			lm.setLastmsgid(message.getId());
			lm.setLast_message(message.getMessage());
			lm.setLastMessageBy(message.getMessageBy());
			lm.setLastMessageDateTime(message.getMessageDateTime());

		if (message.getMessageBy().equalsIgnoreCase(message.getUserMailId()))
			lm.setIsReadByUser(1);
			else if (message.getMessageBy().equalsIgnoreCase(message.getAgentMailId()))
				lm.setIsReadByAgent(1);
		} else {
			lm.setLastmsgid(message.getId());
			lm.setLast_message(message.getMessage());
			lm.setLastMessageBy(message.getMessageBy());
			lm.setLastMessageDateTime(message.getMessageDateTime());

			if (message.getMessageBy().equalsIgnoreCase(message.getAgentMailId()))
				lm.setIsReadByUser(1);
			else if (message.getMessageBy().equalsIgnoreCase(message.getAgentMailId()))
				lm.setIsReadByAgent(1);
		}

		lstmsgRepo.save(lm);

	}
}
