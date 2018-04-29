package com.example.demo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controllers.RestController;
import com.example.demo.dao.DaoAccesRepo;
import com.example.demo.services.JpaService;
@RunWith(SpringRunner.class)
@WebMvcTest(RestController.class)
public class WebControllerTest {

	@MockBean
	private RestController rest;
	
	@MockBean
	private JpaService service;
	
	@MockBean
	private DaoAccesRepo dao;
	
	@Test
	public void rest_test() {
		when(rest.increment(anyString())).thenReturn(1);
		
	}
	
	
	
	
	
	
	
}
