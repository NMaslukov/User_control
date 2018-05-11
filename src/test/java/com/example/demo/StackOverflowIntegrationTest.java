package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.Site;
import com.example.demo.services.StackExchangerService;
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = PersonControlApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-local.properties")
public class StackOverflowIntegrationTest {

	@Autowired
	StackExchangerService rest;
	@Test
	public void rest_test() {
		//Mockito.when(rest.getListOfSites()).thenReturn(new List<Sites>())
		List<Site> listOfSites = null;
		try {
			listOfSites = rest.getListOfSites();
		} catch (Exception e) {
			
		}
		listOfSites.stream().map(p -> p.getName()).forEach(System.out::println);
	}
	
	
	
	
	
	
	
}
