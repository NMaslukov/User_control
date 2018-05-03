package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity

@Table(name = "person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Integer id;
	
	public Person(Integer id, @Size(min = 2) String name, @Size(min = 2) String surname, Integer age, @NotNull String role, @Size(min = 2) String login, @Size(min = 2) String password) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.role = role;
		this.login = login;
		this.password = password;
	}
	public Person() {
		
	}
	
	@Size(min = 2)
	@Column(name = "Name")
	private String name = "";
	
	@Size(min = 2)
	@Column(name = "Surname")
	private String surname = "";
	

	@Column(name = "Age")
	private Integer age = 0;
	
	@NotNull
	@Column(name = "Role")
	private String role = "";
	
	@Size(min = 2)
	@Column(name = "Login", unique = true)
	private String login = "";
	
	@Size(min = 2)
	@Column(name = "Password")
	private String password = "";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + ", role=" + role + ", login=" + login + ", password=" + password + "]";
	}

}
