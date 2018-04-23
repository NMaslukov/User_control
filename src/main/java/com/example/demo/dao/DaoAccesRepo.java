package com.example.demo.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Person;

@Repository
public interface DaoAccesRepo extends CrudRepository<Person, Integer> {
	@Modifying
	@Transactional
	@Query("delete from Person p where p.id = ?1")
	Integer deletePersonById(Integer id);

	@Query("select p from Person p group by p.id")
	Iterable<Person> selectAndGroupById();

	@Query("select p.login from Person p where id = ?1")
	String getLoginById(Integer id);

	@Modifying
	@Transactional
	@Query("update Person p set p.name = ?2, p.surname = ?3, p.age = ?4, p.role = ?5, p.login = ?6, p.password = ?7 where id = ?1")
	void updateById(Integer id, String name, String surname, int age, String role, String login, String password);

	Person getPersonByLogin(String login);

}
