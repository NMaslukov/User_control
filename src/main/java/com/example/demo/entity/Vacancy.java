package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Vacancy {
	private String  post;
	private String  vacancy_url;
	private Integer corresponding;
	private List<String> requirments;
	public Vacancy(){
		requirments = new ArrayList<>();
		//requirments.add("test in constructor");
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getVacancy_url() {
		return vacancy_url;
	}
	public void setVacancy_url(String vacancy_url) {
		this.vacancy_url = vacancy_url;
	}
	public Integer getCorresponding() {
		return corresponding;
	}
	public void setCorresponding(Integer corresponding) {
		this.corresponding = corresponding;
	}
	public List<String> getRequirments() {
		return requirments;
	}
	public void setRequirments(List<String> requirments) {
		this.requirments = requirments;
	}
	@Override
	public String toString() {
		return "Vacancy [post=" + post + ", vacancy_url=" + vacancy_url + "]";
	}
	
}
