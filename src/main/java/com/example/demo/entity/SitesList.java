package com.example.demo.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SitesList {
	
	private List<Site> items;

	public List<Site> getItems() {
		return items;
	}

	public void setItems(List<Site> sites) {
		this.items = sites;
	}
}
