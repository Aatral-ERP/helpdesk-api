package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Agents.entity.AgentToken;

public interface AgentTokenRepository extends JpaRepository<AgentToken, Integer> {

	AgentToken findByEmployeeEmailId(String employeeEmailId);

	List<AgentToken> findByEmployeeEmailIdIn(List<String> emailIds);

}
