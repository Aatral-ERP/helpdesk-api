package com.autolib.helpdesk.jwt;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
//import com.autolib.helpdesk.jwt.model.UserLoginDetails;

public interface JwtDAO {

//	UserLoginDetails loadUserByEmailId(String userName);
	
	InstituteContact loadInstituteContactDetails(String emailId);
	
	Agent loadAgentDetails(String emailId);

}
