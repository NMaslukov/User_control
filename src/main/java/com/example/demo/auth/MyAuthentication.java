package com.example.demo.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.userConfig.MyUserDetails;


public class MyAuthentication implements Authentication {

	private static final long serialVersionUID = 7136176630225373822L;
	private final MyUserDetails userDetails;
	private boolean isAuthenticated;


	public MyAuthentication(MyUserDetails userDetails) {
		this.userDetails = userDetails;
		isAuthenticated = true;
	}

	
	@Override
	public String getName() {
		return userDetails.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDetails.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return userDetails.getPassword();
	}

	@Override
	public Object getDetails() {
		return userDetails;
	}

	@Override
	public Object getPrincipal() {
		return userDetails;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

}
