package com.autolib.helpdesk.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.jwt.model.JwtRequest;
//import com.autolib.helpdesk.jwt.model.UserLoginDetails;

@Service
public class JwtUserDetailsService {

	@Autowired
	JwtDAO jwtDAO;

//	public UserLoginDetails loadUserLoginDetails(String username) {
//		return jwtDAO.loadUserByEmailId(username);
//	}

	public InstituteContact loadInstituteContactDetails(String username) {
		return jwtDAO.loadInstituteContactDetails(username);
	}

	public Agent loadAgentDetails(String username) {
		return jwtDAO.loadAgentDetails(username);
	}

	void authenticate(InstituteContact icDetails, JwtRequest jwtRequest) throws Exception {
		if (icDetails == null) {
			throw new Exception("EmailId Not Found");
		} else if (icDetails.getIsBlocked() > 0) {
			throw new Exception("Email Id '" + icDetails.getEmailId() + "' is Blocked.");
		}
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		boolean matched = pwdEncoder.matches(jwtRequest.getPassword(), icDetails.getPassword());
		System.out.println(icDetails.getPassword() + "  " + pwdEncoder.encode(jwtRequest.getPassword()));
		System.out.println(jwtRequest.getPassword());
		System.out.println(matched);
		if (!matched) {
			throw new Exception("Bad Credentials");
		}

	}

	void authenticateAgent(Agent agent, JwtRequest jwtRequest) throws Exception {

		if (agent == null) {
			throw new Exception("Email Id Not Found");
		} else if (agent.isBlocked()) {
			throw new Exception("Email Id '" + agent.getEmailId() + "' is Blocked.");
		}

		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		boolean matched = pwdEncoder.matches(jwtRequest.getPassword(), agent.getPassword());
		System.out.println(agent.getPassword() + "  " + pwdEncoder.encode(jwtRequest.getPassword()));
		System.out.println(jwtRequest.getPassword());
		System.out.println(matched);
		if (!matched) {
			throw new Exception("Bad Credentials");
		}

	}
}
