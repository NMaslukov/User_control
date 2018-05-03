package com.example.demo.services;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DaoAccesRepo;
import com.example.demo.entity.Person;

/*
 * All methods returns null if Person does not exist. 
 *
 */

@Service
public class JpaService {
	public static final Logger logger = LoggerFactory.getLogger(JpaService.class);

	@Autowired
	DaoAccesRepo accesRepo;

	@Autowired
	EntityManager entityManager;

	public Person save(Person p) {
	if(isLoginUnique(p)) {
		accesRepo.save(p);
		return p;
	}
	
		return null;
	}

	private boolean isLoginUnique(Person p) {
		return accesRepo.getPersonByLogin(p.getLogin()) == null;
	}
	
	@Cacheable(value = CacheProvider.VAL_PERSON, key = CacheProvider.KEY_ID)
	public Person getById(Integer id) {
		Optional<Person> optional = accesRepo.findById(id);

		if (optional.isPresent()) 
			return optional.get();
		else
			return null;
	}


	@CacheEvict(value = CacheProvider.VAL_PERSON, key = CacheProvider.KEY_ID)
	public Integer deletePersonById(Integer id) {

		return accesRepo.deletePersonById(id);
	}
	

	public Iterable<Person> selectAndGroupById() {
		return accesRepo.selectAndGroupById();
	}


	@CachePut(value = CacheProvider.VAL_PERSON,key=CacheProvider.KEY_P_ID)
	public void updatePerson(Person p) {
		accesRepo.updateById(p.getId(), p.getName(), p.getSurname(), p.getAge(), p.getRole(), p.getLogin(), p.getPassword());

	}

	public Person getPersonByLogin(String login) {

		return accesRepo.getPersonByLogin(login);
	}
	
}
