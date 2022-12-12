package com.autolib.helpdesk.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.RoleMaster;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

//	public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60; // 1 hr
//	public static final long JWT_REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60; // 7 days

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.token.validity}")
	public long JWT_TOKEN_VALIDITY; // 1 hr
	@Value("${jwt.refresh.token.validity}")
	public long JWT_REFRESH_TOKEN_VALIDITY; // 7 days

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public void isValidToken(String token) {
		try {
			if (token == null || token == "" || isTokenExpired(token))
				throw new Exception("Unauthorized Access or Token may expired");
		} catch (Exception Ex) {
			System.out.println(Ex.getMessage());
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Ex.getMessage());
		}
	}

	// generate token for user
	public String generateToken(Institute instituteDetails, InstituteContact icDetails) {
		Map<String, Object> claims = new HashMap<>();
		icDetails.setPassword("masked");
		claims.put("InstituteDetails", instituteDetails);
		claims.put("userLoginDetails", icDetails);
		claims.put("InstituteContactDetails", icDetails);
		return doGenerateToken(claims, icDetails.getEmailId());
	}

	// generate token for user
	public String generateRefreshToken(Institute instituteDetails, InstituteContact icDetails) {
		Map<String, Object> claims = new HashMap<>();
		icDetails.setPassword("masked");
		claims.put("InstituteDetails", instituteDetails);
		claims.put("userLoginDetails", icDetails);
		claims.put("InstituteContactDetails", icDetails);
		return doGenerateToken(claims, icDetails.getEmailId());
	}

	// generate token for user
	public String generateAgentToken(Agent agent, RoleMaster role) {
		Map<String, Object> claims = new HashMap<>();

		agent.setPassword("masked");
		agent.setPhoto(null);
		agent.setSignature(null);

		claims.put("AgentDetails", agent);
		claims.put("Role", role);
		return doGenerateToken(claims, agent.getEmailId());
	}

	// generate token for user
	public String generateAgentRefreshToken(Agent agent, RoleMaster role) {
		Map<String, Object> claims = new HashMap<>();

		agent.setPassword("masked");
		agent.setPhoto(null);
		agent.setSignature(null);

		claims.put("AgentDetails", agent);
		claims.put("Role", role);
		return doGenerateToken(claims, agent.getEmailId());
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

//	private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
//
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY))
//				.signWith(SignatureAlgorithm.HS512, secret).compact();
//	}

	// validate token
	public Boolean validateToken(String token, InstituteContact icDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(icDetails.getEmailId()) && !isTokenExpired(token));
	}
}