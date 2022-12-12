/**
 * 
 */
package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentLedger;

/**
 * @author Kannadasan
 *
 */
public interface AgentLedgerRepository extends JpaRepository<AgentLedger, String> {

	/**
	 * @param emailId
	 * @return
	 */
	List<AgentLedger> findByAgentEmailId(String emailId);

	AgentLedger findById(int ledgerId);

}
