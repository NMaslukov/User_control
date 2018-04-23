package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.example.demo.dao.DaoAccesRepo;
import com.example.demo.entity.Person;

@SpringBootApplication
@EnableCaching
public class PersonControlApplication implements CommandLineRunner {

	@Autowired
	DaoAccesRepo accesRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(PersonControlApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		accesRepo.save(new Person(0, "admin", "admin", 1, "ROLE_ADMIN", "admin", "admin"));
		accesRepo.save(new Person(0, "user", "user", 2, "ROLE_USER", "user", "user"));
		accesRepo.save(new Person(0, "test", "DWAWDA", 22, "ROLE_USER", "DWAWDA", "DWAWDA"));
		
	}


}
