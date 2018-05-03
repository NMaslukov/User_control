package com.example.demo.filters;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.controllers.CV;

public class RoleFilter extends GenericFilterBean {
	public static final Logger logger = LoggerFactory.getLogger(RoleFilter.class);

	private final Map<String,String[]> access;

	
	public RoleFilter(Map<String,String[]> access){
	
		this.access = access;
	}
	
	private final String REDIRECT_URL =  CV.MAPPING_LOGIN + CV.PARAM_NOT_RESPECTED;

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpServletRequest request = (HttpServletRequest) req;
		String key = normalizeUrl(request.getRequestURI());
		String[] ROLES = access.get(key);
		
		if(ROLES != null && response.getStatus() != MyTokenFilter.RESPONSE_CODE)
				sendRedirectIfNotAllowed(authentication, ROLES);
		
		chain.doFilter(request, response);
	}


	private void sendRedirectIfNotAllowed(Authentication authentication, String[] ROLES) throws IOException {
		for (String ROLE : ROLES) 
			if(!hasRole(authentication, ROLE))
				sendRedirect();
				
	}


	private void sendRedirect() throws IOException {
		getResponse().sendRedirect(REDIRECT_URL + RedirectFilter.TARGET_URL_PARAM + getRequest().getRequestURL());
		logger.debug("No admin authority. Send redirect to " + REDIRECT_URL);
	}


	private HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	private HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	private boolean hasRole(Authentication authentication, String ROLE) {
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).
			collect(Collectors.toList())
		   .contains(ROLE);
	}


    private String normalizeUrl(String key) {
		int last = key.length() - 1;
		if(String.valueOf(key.charAt(last)).equals("/")) //delete double "/"
			key = key.substring(0, last);

		return key;
	}

}
