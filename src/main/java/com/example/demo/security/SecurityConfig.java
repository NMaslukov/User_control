package com.example.demo.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import com.example.demo.auth.TokenService;
import com.example.demo.filters.MyTokenFilter;
import com.example.demo.filters.RedirectFilter;
import com.example.demo.filters.RoleFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	private static final String WEB = "/web/**"; 
	private static final String REST = "/rest/**";

	public final String[] role_admin = {"ROLE_ADMIN"};
	private final Map<String,String[]> access = new HashMap<>();
	
	public SecurityConfig() throws InterruptedException { // beta version, fix later
		access.put("/web/allPersons", role_admin);
	}
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().sessionManagement() 
	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		http.requestMatchers().
		antMatchers(WEB,REST).and().addFilter(requestHeaderAuthenticationFilter()).addFilterAfter(new RedirectFilter(), MyTokenFilter.class)
		.addFilterAfter(new RoleFilter(access), RedirectFilter.class);
			

		
	
	}
		
	  public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() {  
		  
	   MyTokenFilter requestHeaderAuthenticationFilter = new MyTokenFilter();
	    requestHeaderAuthenticationFilter.setPrincipalRequestHeader(TokenService.TOKEN_NAME);
	    requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);
	    requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(true);
	    
	    return requestHeaderAuthenticationFilter;
	  }
	
	
}
