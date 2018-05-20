package com.example.demo.entity;

public class Vacancy implements Comparable<Vacancy>{
	private String  post;
	private String  vacancy_url;
	private Integer corresponding;
	private String requirments;
	
	public Vacancy(){
		
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

	@Override
	public String toString() {
		return "Vacancy [post=" + post + ", vacancy_url=" + vacancy_url + "]";
	}
	public String getRequirments() {
		return requirments;
	}
	public void setRequirments(String requirments) {
		this.requirments = requirments;
	}
	@Override
	public int compareTo(Vacancy o) {
		
		if(this.corresponding >= o.getCorresponding())
			return -1;
		else return 1;
	}
	
}
