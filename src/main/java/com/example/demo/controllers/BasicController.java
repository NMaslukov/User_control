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

	@Autowired
	private JpaService service;
	
	@GetMapping("/")
	public String hello() {
		
		return "readme";
	}
	
	@GetMapping(MAPPING_LOGIN)
	public String LoginPage(
			@RequestParam(value = ATR_EROR, required = false) String error,
			@RequestParam(value = ATR_NOT_AUTHORITY, required = false) String no_authority,
			@RequestParam(value = ATR_NOT_AUTHORIZE, required = false) String not_authorized,
			@RequestParam(value = RedirectFilter.TARGET_URL, required = false) String target_url,
			Model model) {
		//fi4a
		if(target_url != null && target_url.length() != 0 && String.valueOf(target_url.charAt(target_url.length() - 1)).equals("/") )
		target_url = target_url.substring(0, target_url.length()-1);
		
		model.addAttribute(ATR_EROR, error);
		model.addAttribute(ATR_NOT_AUTHORITY, no_authority);
		model.addAttribute(ATR_NOT_AUTHORIZE, not_authorized);
		model.addAttribute(RedirectFilter.TARGET_URL, target_url);
		
		return MAPPING_LOGIN;
	}

	@PostMapping(MAPPING_LOGIN)
	public String PostLoginPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(PARAM_PASSWORD) String password,
			@RequestParam(PARAM_USERNAME) String login,
			@RequestParam(value = RedirectFilter.TARGET_URL, required = false) String target_url) {
		
		if(verify_log_pass(response, password, login)) {
			
				if(target_url != null)
				return BasicController.MAPPING_REDIRECT + target_url;
			
			return MAPPING_REDIRECT_WEB + MAPPING_ALL_PERSONS;
		}
		
		return MAPPING_REDIRECT +  MAPPING_LOGIN + PARAM_ERROR + RedirectFilter.TARGET_URL_PARAM +target_url;
	}
	
	
	@GetMapping(MAPPING_LOGOUT)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		
		Cookie cookie = new Cookie(TokenService.TOKEN_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);
		return MAPPING_REDIRECT + MAPPING_LOGIN;
	}
	
	public boolean verify_log_pass(HttpServletResponse response, String password, String login) {
		Person person = service.getPersonByLogin(login);
		try {
			if(person != null && password.equals(person.getPassword())){
			String token = TokenService.createJWT(person.getId(), TokenService.EXPIRATION_TIME);
			Cookie c = new Cookie(TokenService.TOKEN_NAME, token);
			c.setPath("/"); 
			response.addCookie(c);

			return true;
			}
		}catch(Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
}
