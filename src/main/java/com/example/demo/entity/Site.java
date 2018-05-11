package com.example.demo.entity;

import org.springframework.stereotype.Component;

@Component
public class Site {
	
	private String favicon_url;
	private String site_url;
	private String name;
	
	
	public Site(String favicon_url, String site_url, String name) {
		this.favicon_url = favicon_url;
		this.site_url = site_url;
		this.name = name;
	}
	
	public Site() {
	}

	//getters and setters
	public String getFavicon_url() {
	return favicon_url;
	}
	
	public void setFavicon_url(String favicon_url) {
	this.favicon_url = favicon_url;
	}
	
	public String getSite_url() {
	return site_url;
	}
	
	public void setSite_url(String site_url) {
	this.site_url = site_url;
	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}

}
