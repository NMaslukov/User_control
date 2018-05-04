package com.example.demo.services;

import com.example.demo.entity.Person;

public interface JpaService {

	Person save(Person p);

	Person getById(Integer id);

	Integer deletePersonById(Integer id);

	Iterable<Person> selectAndGroupById();

	void updatePerson(Person p);

	Person getPersonByLogin(String login);
}
