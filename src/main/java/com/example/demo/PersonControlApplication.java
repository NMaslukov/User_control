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
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(PersonControlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initUsers();
	}

	private void initUsers() {
		accesRepo.save(new Person(0, "admin", "admin", 1, "ROLE_ADMIN", "admin", "admin"));
		accesRepo.save(new Person(0, "user", "user", 2, "ROLE_USER", "user", "user"));
		accesRepo.save(new Person(0, "test", "test", 22, "ROLE_USER", "test", "test"));
	}


}
