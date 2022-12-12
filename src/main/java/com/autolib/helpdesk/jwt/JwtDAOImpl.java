package com.autolib.helpdesk.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
//import com.autolib.helpdesk.jwt.model.UserLoginDetails;
//import com.autolib.helpdesk.jwt.repository.UserLoginDetailsRepository;

@Repository
public class JwtDAOImpl implements JwtDAO {

//	@Autowired
//	UserLoginDetailsRepository ulRepo;

	@Autowired
	InstituteContactRepository icRepo;
	@Autowired
	AgentRepository agentRepo;

//	@Override
//	public UserLoginDetails loadUserByEmailId(String emailId) {
//		return ulRepo.findByEmailId(emailId);
//	}

	@Override
	public InstituteContact loadInstituteContactDetails(String emailId) {
		return icRepo.findByEmailId(emailId);
	}
	
	@Override
	public Agent loadAgentDetails(String emailId) {
		return agentRepo.findByEmailId(emailId);
		}

}
