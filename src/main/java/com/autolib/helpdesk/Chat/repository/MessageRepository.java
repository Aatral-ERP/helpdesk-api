package com.autolib.helpdesk.Chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Chat.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
		
	@Query(value = "SELECT COUNT(*) AS unreadmessage FROM chats WHERE is_read='0' AND agent_email_id=?", nativeQuery = true)
	public int findUnreadMessagesCount(String AgentEmailId);

}
