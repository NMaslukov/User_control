package com.example.demo.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Site;
import com.example.demo.entity.SitesList;

/*
 * http://api.stackexchange.com/2.2/sites?filter=!2--Yion.2OJSStcKSpFvq
 */
@Component
public class StackOverflowService {
	HttpClient httpClien = HttpClientBuilder.create().build();
	ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClien);
	private RestTemplate restTemplate = new RestTemplate(requestFactory);
	public final String url = "http://api.stackexchange.com/2.2/sites?filter=!2--Yion.2OJSStcKSpFvq";
	
	
	public List<Site> getListOfSites() throws RestClientException, URISyntaxException{
		 ResponseEntity<SitesList> response = restTemplate.getForEntity(new URI(url), SitesList.class);
  		 return response.getBody().getItems();
	}
	
}
