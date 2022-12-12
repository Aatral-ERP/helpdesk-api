package com.autolib.helpdesk.Chat.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Chat.dao.ChatDAO;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatDAO chatDAO;

	@Override
	public Map<String, Object> getChatSettings() {
		return chatDAO.getChatSettings();
	}

	@Override
	public Map<String, Object> saveChatSettings(Map<String, Object> req) {
		return chatDAO.saveChatSettings(req);
	}

	@Override
	public Map<String, Object> getMessages(Map<String, Object> req) {
		return chatDAO.getMessages(req);
	}

	@Override
	public Map<String, Object> getRecentMessageMembers(Map<String, Object> req) {
		return chatDAO.getRecentMessageMembers(req);
	}

	@Override
	public Map<String, Object> getUnreadMessageCount(String chatEmployeeId) {
		return chatDAO.getUnreadMessageCount(chatEmployeeId);
	}

	@Override
	public Map<String, Object> getUserAgentMessages(Map<String, Object> req) {
		return chatDAO.getUserAgentMessages(req);
	}

	@Override
	public Map<String, Object> getRecentMessageUser(Map<String, Object> req) {
		return chatDAO.getRecentMessageUser(req);
	}

}
