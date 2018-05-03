package com.example.demo.controllers;

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

import com.example.demo.auth.TokenService;
import com.example.demo.entity.Person;
import com.example.demo.filters.RedirectFilter;
import com.example.demo.services.JpaService;

@Controller
public class BasicController {

	public static final Logger logger = LoggerFactory.getLogger(BasicController.class);

	@Autowired
	private JpaService service;
	
	@GetMapping("/")
	public String hello() {
		
		return "readme";
	}
	
	@GetMapping(CV.MAPPING_LOGIN)
	public String LoginPage(
			@RequestParam(value = CV.PARAM_EROR, required = false) String error,
			@RequestParam(value = CV.PARAM_NOT_AUTHORITY, required = false) String no_authority,
			@RequestParam(value = CV.PARAM_NOT_AUTHORIZE, required = false) String not_authorized,
			//url before redirect to the login page
			@RequestParam(value = RedirectFilter.TARGET_URL, required = false) String target_url,
			Model model) {
		
		target_url = normalizeURL(target_url);
		
		model.addAttribute(CV.PARAM_EROR, error);
		model.addAttribute(CV.PARAM_NOT_AUTHORITY, no_authority);
		model.addAttribute(CV.PARAM_NOT_AUTHORIZE, not_authorized);
		model.addAttribute(RedirectFilter.TARGET_URL, target_url);
		
		return CV.VIEW_LOGIN;
	}
	
	//fix "/" problem
	private String normalizeURL(String target_url) {
		if(target_url != null && target_url.length() != 0 && String.valueOf(target_url.charAt(target_url.length() - 1)).equals("/") )
		target_url = target_url.substring(0, target_url.length()-1);
		return target_url;
	}

	@PostMapping(CV.MAPPING_LOGIN)
	public String PostLoginPage(
			HttpServletResponse response,
			@RequestParam(CV.PARAM_PASSWORD) String password,
			@RequestParam(CV.PARAM_USERNAME) String login,
			@RequestParam(value = RedirectFilter.TARGET_URL, required = false) String target_url) {
		
		if(isLogPassValid(password, login)) {
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
	
	public boolean isLogPassValid(String password, String login) {
		Person person = service.getPersonByLogin(login);
		return (person != null && password.equals(person.getPassword()));

	}

	private void setToken(HttpServletResponse response, Person person) {
		String token = TokenService.createJWT(person.getId(), TokenService.EXPIRATION_TIME);
		Cookie c = new Cookie(TokenService.TOKEN_NAME, token);
		c.setPath("/"); 
		response.addCookie(c);
	}
}
