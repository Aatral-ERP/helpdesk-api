package com.autolib.helpdesk.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	
	Token findByUserId(String userId);
}