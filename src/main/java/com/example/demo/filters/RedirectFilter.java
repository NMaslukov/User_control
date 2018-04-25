package com.example.demo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.controllers.BasicController;

public class RedirectFilter extends GenericFilterBean{
	public static final String REDIRECT_URL_NOT_AUTH =   BasicController.MAPPING_LOGIN + BasicController.PARAM_NOT_AUTHORIZED;
	public static final Logger logger = LoggerFactory.getLogger(RedirectFilter.class);
	public static final String TARGET_URL_PARAM = "&TARGET_URL=";
	public static final String TARGET_URL = "TARGET_URL";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		Authentication auth = null;
		
		auth = SecurityContextHolder.getContext().getAuthentication();
//
		if(auth == null) {
			resp.sendRedirect(REDIRECT_URL_NOT_AUTH + TARGET_URL_PARAM + req.getRequestURI());
			resp.setStatus(MyTokenFilter.RESPONSE_CODE);
			logger.debug("Not authenticated. Sent redirect to " + REDIRECT_URL_NOT_AUTH);
			return;
		}
		
		chain.doFilter(req, resp);
	}
		
}