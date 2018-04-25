package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.demo.dao.DaoAccesRepo;
import com.example.demo.userConfig.MyUserDetails;
/*
 * public Authentication authenticate gets an Authentication  object that actually represents token.
 * This method extract token value, create and returns fully populated  Authentication object.
 */
@Component
public class TokenAuthManager implements AuthenticationManager{
	@Autowired
	DaoAccesRepo dao;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		Integer extracted_id = TokenService.parseJWT(authentication.getName());
		MyUserDetails details = new MyUserDetails(dao.findById(extracted_id).get());
		CostumAuth auth = new CostumAuth(details );
		
		return auth;
	}

}


