package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.demo.dao.DaoAccesRepo;
import com.example.demo.entity.Person;
import com.example.demo.userConfig.MyUserDetails;
/*
 * public method authenticate() gets an Authentication  object that actually represents token.
 * This method extract token value, create and returns fully populated  Authentication object.
 */
@Component
public class TokenAuthManager implements AuthenticationManager{
	@Autowired
	DaoAccesRepo dao;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//authentication.getName() returns token value
		Integer extracted_id = TokenService.getUserIdFromToken(authentication.getName());
		Person person = dao.findById(extracted_id).get();
		MyUserDetails details = new MyUserDetails(person);
		
		MyAuthentication auth = new MyAuthentication(details);
		
		return auth;
	}

}


