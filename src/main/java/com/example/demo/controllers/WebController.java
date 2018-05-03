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
@RequestMapping(CV.WEB)
public class WebController {
	public static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private JpaService service;

	@GetMapping(CV.MAPPING_ALL_PERSONS)
	public ModelAndView allPersons( Authentication authentication) {
		
		ModelAndView m = new ModelAndView(CV.VIEW_SHOW_ALL);
		m.addObject(CV.OBJ_PERSONS, service.selectAndGroupById());
		m.addObject(CV.OBJ_NEW_PERSON, new Person());
		m.addObject(CV.OBJ_UPDATE_PERSON, new Person());

		return m;
	}


	@PostMapping(CV.MAPPING_ADD_PERSON)
	public String addNewPerson(@Valid @ModelAttribute(CV.OBJ_NEW_PERSON) Person p, BindingResult br, Model model) {
		if (br.hasErrors()) {
			model.addAttribute(CV.OBJ_PERSONS, service.selectAndGroupById());
			model.addAttribute(CV.OBJ_UPDATE_PERSON, new Person());
			return CV.VIEW_SHOW_ALL;
		}
		service.save(p);
		
		return CV.MAPPING_REDIRECT_WEB + CV.MAPPING_ALL_PERSONS;
	}


	@PostMapping(CV.MAPPING_UPDATE_EXISTING_PERSON)
	public String updateExistingPerson(@Valid @ModelAttribute(CV.OBJ_UPDATE_PERSON) Person p, BindingResult br, Model model) {
		if (br.hasErrors()) {
			model.addAttribute(CV.OBJ_PERSONS, service.selectAndGroupById());
			model.addAttribute(CV.OBJ_NEW_PERSON, new Person());
			return CV.VIEW_SHOW_ALL;
		}
		service.updatePerson(p);

		return CV.MAPPING_REDIRECT_WEB + CV.MAPPING_ALL_PERSONS;
	}

	@PostMapping(CV.MAPPING_DELETE_PERSON)
	public String deletePerson(@RequestParam(CV.PARAM_ID_DELETE) Integer id) {
		service.deletePersonById(id);
		return CV.MAPPING_REDIRECT_WEB + CV.MAPPING_ALL_PERSONS;
	}

	@GetMapping(CV.MAPPING_HELLO)
	public String hello() {
		return CV.VIEW_HELLO;
	}
}



