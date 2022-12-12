package com.autolib.helpdesk.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.RoleMaster;
import com.autolib.helpdesk.Agents.repository.RoleMasterRepository;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.jwt.model.JwtRefreshTokenRequest;
import com.autolib.helpdesk.jwt.model.JwtRequest;
import com.autolib.helpdesk.jwt.model.JwtResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("authenticate")
public class JwtAuthenticationController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private RoleMasterRepository roleRepo;

	@Autowired
	private InstituteRepository instRepo;

	@RequestMapping(value = "generate-token", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authRequest) throws Exception {

		String token = null, message = "success", refreshtoken = null;
		try {
			if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty())
				throw new Exception("Username is empty");
			if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty())
				throw new Exception("password is empty");

			final InstituteContact icDetails = userDetailsService
					.loadInstituteContactDetails(authRequest.getUsername());

			userDetailsService.authenticate(icDetails, authRequest);
			final Institute inst = instRepo.findByInstituteId(icDetails.getInstituteId());
			token = jwtTokenUtil.generateToken(inst, icDetails);
			refreshtoken = jwtTokenUtil.generateRefreshToken(inst, icDetails);

		} catch (Exception Ex) {
			message = Ex.getMessage();

			System.err.println("Exception :: " + Ex.getMessage());
		}
		return ResponseEntity.ok(new JwtResponse(token, refreshtoken, message, null));
	}

	@RequestMapping(value = "generate-token-google", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenGoogle(@RequestBody JwtRequest authRequest) throws Exception {

		String token = null, message = "success", refreshtoken = null;
		try {
			if (authRequest.getGoogleEmailId() == null || authRequest.getGoogleEmailId().isEmpty())
				throw new Exception("Email Id '" + authRequest.getGoogleEmailId() + "' is not valid");
			if (authRequest.getGoogleId() == null || authRequest.getGoogleId().isEmpty())
				throw new Exception("Google verification failed , Try agian later.");
			if (authRequest.getGoogleToken() == null || authRequest.getGoogleToken().isEmpty())
				throw new Exception("Google verification failed , Try agian later.");

			final InstituteContact icDetails = userDetailsService
					.loadInstituteContactDetails(authRequest.getGoogleEmailId());

			if (icDetails == null) {
				throw new Exception("Email Id  '" + authRequest.getGoogleEmailId() + "' Not Found");
			}
			if (icDetails.getIsBlocked() > 0) {
				throw new Exception("Email Id  '" + authRequest.getGoogleEmailId() + "' is Blocked.");
			}
			if (icDetails != null) {

				final Institute inst = instRepo.findByInstituteId(icDetails.getInstituteId());

				token = jwtTokenUtil.generateToken(inst, icDetails);
				refreshtoken = jwtTokenUtil.generateRefreshToken(inst, icDetails);

			}

		} catch (Exception Ex) {
			message = Ex.getMessage();

			System.out.println("Exception :: " + Ex.getMessage());
		}
		System.err.println(token);
		return ResponseEntity.ok(new JwtResponse(token, refreshtoken, message, null));
	}

	@RequestMapping(value = "refresh-token", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(@RequestBody JwtRefreshTokenRequest jwtRefreshTokenRequest)
			throws Exception {
		System.out.println(jwtRefreshTokenRequest.toString());
		String token = null, message = "";
		try {
			final InstituteContact icDetails = userDetailsService.loadInstituteContactDetails(
					jwtTokenUtil.getUsernameFromToken(jwtRefreshTokenRequest.getRefreshtoken()));

			if (icDetails == null) {
				throw new Exception("Email Id  '"
						+ jwtTokenUtil.getUsernameFromToken(jwtRefreshTokenRequest.getRefreshtoken()) + "' Not Found");
			}
			if (icDetails.getIsBlocked() > 0) {
				throw new Exception(
						"Email Id  '" + jwtTokenUtil.getUsernameFromToken(jwtRefreshTokenRequest.getRefreshtoken())
								+ "' is Blocked.");
			}

			if (jwtTokenUtil.isTokenExpired(jwtRefreshTokenRequest.getRefreshtoken()))
				throw new Exception("Refresh Token Expired");
			else if (!jwtTokenUtil.validateToken(jwtRefreshTokenRequest.getRefreshtoken(), icDetails))
				throw new Exception("Invalid Refresh Token - Subject Changed");

			final Institute inst = instRepo.findByInstituteId(icDetails.getInstituteId());
			token = jwtTokenUtil.generateToken(inst, icDetails);

		} catch (Exception Ex) {
			message = Ex.getMessage();

			System.err.println("Exception :: " + Ex.getMessage());
		}

		return ResponseEntity.ok(new JwtResponse(token, jwtRefreshTokenRequest.getRefreshtoken(), message, null));
	}

	@RequestMapping(value = "generate-token-agent", method = RequestMethod.POST)
	public ResponseEntity<?> createAgentAuthenticationToken(@RequestBody JwtRequest authRequest) throws Exception {

		String token = null, message = "success", refreshtoken = null;
		Agent agent = null;
		byte[] photo = null;
		try {
			if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty())
				throw new Exception("Username is empty");
			if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty())
				throw new Exception("password is empty");

			agent = userDetailsService.loadAgentDetails(authRequest.getUsername());

			photo = agent.getPhoto();

			userDetailsService.authenticateAgent(agent, authRequest);

			final RoleMaster role = roleRepo.findById(agent.getAgentType());

			token = jwtTokenUtil.generateAgentToken(agent, role);
			refreshtoken = jwtTokenUtil.generateAgentRefreshToken(agent, role);

		} catch (Exception Ex) {
			message = Ex.getMessage();

			System.err.println("Exception :: " + Ex.getMessage());
		}
		System.out.println(token);
		return ResponseEntity.ok(new JwtResponse(token, refreshtoken, message, photo));
	}

	@RequestMapping(value = "refresh-token-agent", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAgentAuthenticationToken(@RequestBody JwtRefreshTokenRequest jwtRefreshTokenRequest)
			throws Exception {
		System.out.println(jwtRefreshTokenRequest.toString());
		String token = null, message = "";
		Agent agent = null;
		byte[] photo = null;
		try {
			agent = userDetailsService
					.loadAgentDetails(jwtTokenUtil.getUsernameFromToken(jwtRefreshTokenRequest.getRefreshtoken()));

			if (jwtTokenUtil.isTokenExpired(jwtRefreshTokenRequest.getRefreshtoken()))
				throw new Exception("Refresh Token Expired");
			else if (agent.isBlocked())
				throw new Exception("Agent is Blocked");

			photo = agent.getPhoto();
			final RoleMaster role = roleRepo.findById(agent.getAgentType());
			token = jwtTokenUtil.generateAgentToken(agent, role);

		} catch (Exception Ex) {
			message = Ex.getMessage();
			System.err.println("Exception :: " + Ex.getMessage());
		}

		return ResponseEntity.ok(new JwtResponse(token, jwtRefreshTokenRequest.getRefreshtoken(), message, photo));
	}

	@RequestMapping(value = "generate-token-agent-google", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenAgentGoogle(@RequestBody JwtRequest authRequest)
			throws Exception {

		String token = null, message = "success", refreshtoken = null;
		Agent agent = null;
		byte[] photo = null;
		try {
			if (authRequest.getGoogleEmailId() == null || authRequest.getGoogleEmailId().isEmpty())
				throw new Exception("Email Id '" + authRequest.getGoogleEmailId() + "' is not valid");
			if (authRequest.getGoogleId() == null || authRequest.getGoogleId().isEmpty())
				throw new Exception("Google verification failed , Try agian later.");
			if (authRequest.getGoogleToken() == null || authRequest.getGoogleToken().isEmpty())
				throw new Exception("Google verification failed , Try agian later.");

			agent = userDetailsService.loadAgentDetails(authRequest.getGoogleEmailId());
			if (agent == null) {
				throw new Exception("Email Id  '" + authRequest.getGoogleEmailId() + "' Not Found");
			}
			if (agent.isBlocked()) {
				throw new Exception("Email Id  '" + authRequest.getGoogleEmailId() + "' is Blocked.");
			}

			photo = agent.getPhoto();
			final RoleMaster role = roleRepo.findById(agent.getAgentType());
			token = jwtTokenUtil.generateAgentToken(agent, role);
			refreshtoken = jwtTokenUtil.generateAgentRefreshToken(agent, role);

		} catch (Exception Ex) {
			message = Ex.getMessage();

			System.out.println("Exception :: " + Ex.getMessage());
		}
		return ResponseEntity.ok(new JwtResponse(token, refreshtoken, message, photo));
	}

}