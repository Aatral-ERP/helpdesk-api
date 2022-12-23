/**
 * 
 */
package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	@Query(value = "SELECT ledger.* FROM agent_ledger ledger WHERE ledger.agent_email_id IN ?1 AND "
			+ "ledger.id = (SELECT MAX(led.id) FROM agent_ledger led WHERE led.agent_email_id =  ledger.agent_email_id)", nativeQuery = true)
	List<AgentLedger> findRecentLegderPerAgent(List<String> emailId);

}
