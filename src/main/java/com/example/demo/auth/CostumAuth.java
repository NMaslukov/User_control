package com.example.demo.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.userConfig.MyUserDetails;


public class CostumAuth implements Authentication {

	private static final long serialVersionUID = 7136176630225373822L;
	private final MyUserDetails user;
	private boolean isAuth;


	public CostumAuth(MyUserDetails user) {
		this.user = user;
		isAuth = true;
	}

	
	@Override
	public String getName() {

		return user.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return user.getAuthorities();
	}

	@Override
	public Object getCredentials() {

		return user.getPassword();
	}

	@Override
	public Object getDetails() {

		return user;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuth;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		isAuth = isAuthenticated;

	}

}
