package com.example.demo.services;

import java.math.BigInteger;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
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
 */

@Service
public class JpaService {
	public static final Logger logger = LoggerFactory.getLogger(JpaService.class);

	@Autowired
	DaoAccesRepo accesRepo;

	@Autowired
	EntityManager entityManager;

	// Update Person if already exist
	
	public Person save(Person p) {
		try {
			Person person = accesRepo.save(p);
			if (person != null) {
				return person;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback_sequence(); 
		}
		return null;

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
	
	private void rollback_sequence() {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		BigInteger val = (BigInteger) session.createNativeQuery("select next_val from hibernate_sequence").getSingleResult();

		int decrement = val.intValue();
		session.createNativeQuery("update hibernate_sequence set next_val = ?1").setParameter(1, --decrement).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
}
