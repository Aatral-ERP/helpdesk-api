package com.autolib.helpdesk.Chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Chat.entity.LastMessageDetails;

public interface LastMessageDetailsRepository extends JpaRepository<LastMessageDetails, Integer> {

	LastMessageDetails findByChatId(String chatId);

}
