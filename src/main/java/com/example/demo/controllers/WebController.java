package com.example.demo.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Person;
import com.example.demo.services.JpaService;

@Controller
@RequestMapping(BasicController.WEB)
public class WebController {
	public static final Logger logger = LoggerFactory.getLogger(WebController.class);

	public static final String MAPPING_UPDATE_EXISTING_PERSON = "/updateExistingPerson";
	public static final String MAPPING_DELETE_PERSON = "/deletePerson";
	public static final String MAPPING_ADD_PERSON = "/addNewPerson";
	public static final String MAPPING_HELLO = "/hello";

	public static final String OBJ_PERSONS = "persons";
	public static final String OBJ_NEW_PERSON = "new_person";
	public static final String OBJ_UPDATE_PERSON = "update_person";
	
	public static final String VIEW_SHOW_ALL = "show_all";
	public static final String VIEW_HELLO = "Hello";
	


	
	@Autowired
	private JpaService service;

	@GetMapping(BasicController.MAPPING_ALL_PERSONS)
	public ModelAndView allPersons( Authentication authentication) {
		
		ModelAndView m = new ModelAndView(VIEW_SHOW_ALL);

		m.addObject(OBJ_PERSONS, service.selectAndGroupById());
		m.addObject(OBJ_NEW_PERSON, new Person());
		m.addObject(OBJ_UPDATE_PERSON, new Person());

		return m;
	}


	@PostMapping(MAPPING_ADD_PERSON)
	public String addNewPerson(@Valid @ModelAttribute(OBJ_NEW_PERSON) Person p, BindingResult br, Model model) {
		if (br.hasErrors()) {
			Iterable<Person> all = service.selectAndGroupById();
			model.addAttribute(OBJ_PERSONS, all);
			model.addAttribute(OBJ_UPDATE_PERSON, new Person());
			
			return VIEW_SHOW_ALL;
		}
		
		service.save(p);
		
		return BasicController.MAPPING_REDIRECT_WEB + BasicController.MAPPING_ALL_PERSONS;
	}


	@PostMapping(MAPPING_UPDATE_EXISTING_PERSON)
	public String updateExistingPerson(@Valid @ModelAttribute(OBJ_UPDATE_PERSON) Person p, BindingResult br, Model model) {
		if (br.hasErrors()) {
			Iterable<Person> all = service.selectAndGroupById();
			model.addAttribute(OBJ_PERSONS, all);
			model.addAttribute(OBJ_NEW_PERSON, new Person());
			return VIEW_SHOW_ALL;
		}
		service.updatePerson(p);

		return BasicController.MAPPING_REDIRECT_WEB + BasicController.MAPPING_ALL_PERSONS;
	}

	@PostMapping(MAPPING_DELETE_PERSON)
	public String deletePerson(@RequestParam("id_delete") Integer id) {
		System.out.println(id);
		service.deletePersonById(id);

		return BasicController.MAPPING_REDIRECT_WEB + BasicController.MAPPING_ALL_PERSONS;
	}

	@GetMapping(MAPPING_HELLO)
	public String hello() {
		return VIEW_HELLO;
	}
	
}



