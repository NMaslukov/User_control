package com.example.demo.controllers;
//ControllerVariables
public interface CV {
	public static final String WEB = "/web";
	public static final String REST = "/rest";
	
	public static final String MAPPING_LOGIN = "/login";
	public static final String MAPPING_LOGOUT = "/logout";
	
	public static final String PARAM_EROR = "error";
	public static final String PARAM_NOT_AUTHORITY = "no_authority";
	public static final String PARAM_NOT_AUTHORIZE = "not_authorized";
	public static final String PARAM_ID_DELETE = "id_delete";

	
	public static final String MAPPING_ALL_PERSONS = "/allPersons";
	public static final String MAPPING_REDIRECT_WEB = "redirect:/web";
	public static final String MAPPING_REDIRECT = "redirect:";
	public static final String MAPPING_REDIRECT_TO_HELLO = "redirect:/web/hello";
	public static final String MAPPING_UPDATE_EXISTING_PERSON = "/updateExistingPerson";
	public static final String MAPPING_DELETE_PERSON = "/deletePerson";
	public static final String MAPPING_ADD_PERSON = "/addNewPerson";
	public static final String MAPPING_HELLO = "/hello";
	
	public static final String PARAM_ERROR 			= "?error";
	public static final String PARAM_NOT_RESPECTED  = "?no_authority";
	public static final String PARAM_NOT_AUTHORIZED = "?not_authorized";
	public static final String PARAM_PASSWORD 		= "password";
	public static final String PARAM_USERNAME 		= "username";
	
	public static final String VIEW_LOGIN    = "login";
	public static final String VIEW_SHOW_ALL = "show_all";
	public static final String VIEW_HELLO 	 = "Hello";
	
	public static final String OBJ_PERSONS 		 = "persons";
	public static final String OBJ_NEW_PERSON	 = "new_person";
	public static final String OBJ_UPDATE_PERSON = "update_person";
	
	public static final String REDIRECT_WEB_ALL_PERSONS = "redirect:/web/allPersons";
}
