package com.example.demo.controllers;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.example.demo.auth.TokenService;
import com.example.demo.entity.Person;
import com.example.demo.filters.RedirectFilter;
import com.example.demo.services.JpaServiceImpl;
import com.example.demo.services.StackExchangerService;

@Controller
public class BasicController {

	public static final Logger logger = LoggerFactory.getLogger(BasicController.class);
	
	@Autowired
	StackExchangerService rest;
	
	@Autowired
	private JpaServiceImpl service;
	
	@GetMapping("/")
	public String hello() {
		logger.info("MAPPINT README");
		return CV.VIEW_README;
	}
	
	@GetMapping(CV.MAPPING_LOGIN)
	public String LoginPage(
		@RequestParam(value = CV.PARAM_EROR, required = false) String error,
		@RequestParam(value = CV.PARAM_NOT_AUTHORITY, required = false) String no_authority,
		@RequestParam(value = CV.PARAM_NOT_AUTHORIZE, required = false) String not_authorized,
		@RequestParam(value = RedirectFilter.URL_BEFORE_REDIRECT, required = false) String target_url,
		Model model) {
		
		login_setAttributes(error, no_authority, not_authorized, normalizeURL(target_url), model);
		return CV.VIEW_LOGIN;
	}

	private void login_setAttributes(String error, String no_authority, String not_authorized, String target_url, Model model) {
		model.addAttribute(CV.PARAM_EROR, error);
		model.addAttribute(CV.PARAM_NOT_AUTHORITY, no_authority);
		model.addAttribute(CV.PARAM_NOT_AUTHORIZE, not_authorized);
		model.addAttribute(RedirectFilter.URL_BEFORE_REDIRECT, target_url);
	}
	
	//fix "/" problem
	private String normalizeURL(String target_url) {
		if(notEmpty(target_url) && hasSlesh(target_url)) 
				target_url = target_url.substring(0, target_url.length()-1);
		
		return target_url;
	}

	private boolean hasSlesh(String target_url) {
		return String.valueOf(target_url.charAt(target_url.length() - 1)).equals("/");
	}

	private boolean notEmpty(String target_url) {
		return target_url != null && target_url.length() != 0;
	}

	@PostMapping(CV.MAPPING_LOGIN)
	public String ProcessLoginData(
		HttpServletResponse response,
		@RequestParam(CV.PARAM_PASSWORD) String password,
		@RequestParam(CV.PARAM_USERNAME) String login,
		@RequestParam(value = RedirectFilter.URL_BEFORE_REDIRECT, required = false) String target_url) {
		
		if(isLogPassValid(login, password)) {
			setToken(response, service.getPersonByLogin(login));
				if(isTargetUrlPresent(target_url))
					return CV.MAPPING_REDIRECT + target_url;
					else
					return CV.MAPPING_REDIRECT + "/";
		}
		return CV.MAPPING_REDIRECT_LOGIN_ERROR_URL + target_url;
	}

	private boolean isTargetUrlPresent(String target_url) {
		return target_url.length() != 0 && target_url != null;
	}
	
	@GetMapping(CV.MAPPING_LOGOUT)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		deleteToken(request, response);
		return CV.REDIRECT_LOGIN;
	}

	private void deleteToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(TokenService.TOKEN_NAME, null);
        cookie.setMaxAge(0); 
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
	public boolean isLogPassValid(String login, String password) {
		Person person = service.getPersonByLogin(login);
		return (person != null && password.equals(person.getPassword()));
	}

	private void setToken(HttpServletResponse response, Person person) {
		String token = TokenService.createJWT(person.getId(), TokenService.EXPIRATION_TIME);
		Cookie c = new Cookie(TokenService.TOKEN_NAME, token);
		c.setPath("/"); 
		response.addCookie(c);
	}
	
	@GetMapping(CV.MAPPING_SITES_SERVICE)
	public String getSites(Model model) {
		try {
			model.addAttribute(CV.ATTRIBUTE_LIST_SITES,rest.getListOfSites());
		} catch (RestClientException | URISyntaxException e) {
			e.printStackTrace();
		}
		return "sites";
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		rest.testListPerson();
		return CV.VIEW_README;
	}
	
	/*
	 * TODO test
	 */
	@GetMapping(value = "/testList")
	@ResponseBody
	public List<Person> test(HttpServletResponse response){
		List<Person> list = new ArrayList<>();
		for(int i = 0;i<100;i++) {
			list.add(new Person(i,"name"+i,"surname"+1,i,"some_role","login","password"));
		}

		return list;
	}
	
	@GetMapping("/sites_angularImpl_service")
	public String sites_angular_service() {
		return "sites_angularImpl_service";
	}
	
	@GetMapping("/sites_ajaxImpl_service")
	public String sites_ajaxImpl_service() {
		return "sites_ajaxImpl_service";
	}
	
}
