package com.autolib.helpdesk.Chat.dao;

import java.util.Map;

public interface ChatDAO {

	Map<String, Object> getChatSettings();

	Map<String, Object> saveChatSettings(Map<String, Object> req);

	Map<String, Object> getMessages(Map<String, Object> req);

	Map<String, Object> getRecentMessageMembers(Map<String, Object> req);

	Map<String, Object> getUnreadMessageCount(String chatEmployeeId);

	Map<String, Object> getUserAgentMessages(Map<String, Object> req);

	Map<String, Object> getRecentMessageUser(Map<String, Object> req);

}
