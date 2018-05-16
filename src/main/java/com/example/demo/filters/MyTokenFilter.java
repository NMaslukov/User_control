package com.example.demo.filters;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import com.example.demo.auth.TokenService;

/*
 * Finds and returns token from cookies.
 * Returns null if token isn't present.
 * If null was returned, filter will send the redirect to login page.
 */
public class MyTokenFilter  extends RequestHeaderAuthenticationFilter {
	
	public static int REDIRECT_CODE = 302;
	public static final Logger logger = LoggerFactory.getLogger(MyTokenFilter.class);

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return findTokenInCookies(request.getCookies());
	}
	
	public String findTokenInCookies(Cookie[] cookies) {
		String token = null;
		
		if(cookies != null) token = getToken(cookies);
			
		return token;
	}

	private String getToken(Cookie[] cookies) {
		String token = null;
		
		for (Cookie cookie : cookies) {
			 if(cookie.getName().equals(TokenService.TOKEN_NAME)) token = cookie.getValue();
		}
		return token;
	}

}
