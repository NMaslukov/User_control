package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {
	public static final String REDIRECT_TO_HELLO = "redirect:/web/hello";
	public static final String WEB = "/web";
	public static final String MAPPING_LOGIN = "/login";
	public static final String MAPPING_LOGOUT = "/logout";
	
	public static final String ATR_EROR = "error";
	public static final String ATR_NOT_AUTHORITY = "no_authority";
	public static final String ATR_NOT_AUTHORIZE = "not_authorized";
	
	
	public static final String MAPPING_ALL_PERSONS = "/allPersons";
	public static final String MAPPING_REDIRECT_WEB = "redirect:/web";
	public static final String MAPPING_REDIRECT = "redirect:";

	public static final String PARAM_ERROR = "?error";
	public static final String PARAM_NOT_RESPECTED = "?no_authority";
	public static final String PARAM_NOT_AUTHORIZED = "?not_authorized";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_USERNAME = "username";
	
	public static final Logger logger = LoggerFactory.getLogger(BasicController.class);


	@GetMapping("/")
	public String hello() {
		
		return "readme";
	}
	
}
