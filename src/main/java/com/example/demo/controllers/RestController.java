package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Person;
import com.example.demo.services.JpaService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {
	@Autowired
	private JpaService service;

	@GetMapping("/getById/{id}")
	public Person getById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}



	@PutMapping("/save")
	public Person save(@RequestBody Person p) {
		return service.save(p);
	}


	@DeleteMapping("/deletePersonById/{id}")
	public Integer deletePersonById(@PathVariable Integer id) {
		return service.deletePersonById(id);
	}

	@GetMapping("/getNameById/{id}")
	public String getNameById(@PathVariable("id") Integer id) {
		return service.getById(id).getName();
	}

	@GetMapping("/getSurnameById/{id}")
	public String getSurnameById(@PathVariable("id") Integer id) {
		return service.getById(id).getSurname();
	}

	@GetMapping("/getRoleById/{id}")
	public String getRoleById(@PathVariable("id") Integer id) {
		return service.getById(id).getRole();
	}

	@GetMapping("/getLoginById/{id}")
	public String getLoginById(@PathVariable("id") Integer id) {
		return service.getById(id).getLogin();
	}

	@GetMapping("/getPasswordById/{id}")
	public String getPasswordById(@PathVariable("id") Integer id) {
		return service.getById(id).getPassword();
	}

	@GetMapping("/getAgeById/{id}")
	public Integer getAgeById(@PathVariable("id") Integer id) {
		return service.getById(id).getAge();
	}



	public Object increment(String anyString) {
		// TODO Auto-generated method stub
		return null;
	}
}
