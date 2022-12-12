//package com.autolib.helpdesk.jwt;
//
//import java.io.IOException;
//import java.util.Enumeration;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//public class HttpRequestFilter extends OncePerRequestFilter {
//
//	@Autowired
//	JwtTokenUtil jwtUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//
//		final String requestTokenHeader = request.getHeader("Authorization");
//		System.out.println("requestTokenHeader:::" + requestTokenHeader);
//
//		System.out.println("requestTokenHeader:::" + request.getRequestURI());
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		Enumeration<String> headerNames = httpRequest.getHeaderNames();
//
//		if (headerNames != null) {
//			while (headerNames.hasMoreElements()) {
//				String header = headerNames.nextElement();
//				System.out.println("key  : " + header);
//				System.out.println("value: " + httpRequest.getHeader(header));
//			}
//		}
//		try {
//			if (!request.getRequestURI().contains("authenticate")) {
//				jwtUtil.isValidToken(requestTokenHeader);
//			}
//
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
////			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//
//			((HttpServletResponse) response).setHeader("Content-Type", "application/json");
//			((HttpServletResponse) response).setStatus(401);
//			response.getOutputStream().write(401);
//
//			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
//					"Session Expired / Unauthorized Access.");
//			return;
//		}
//
//		chain.doFilter(request, response);
//	}
//}
