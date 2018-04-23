package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.example.demo.dao.DaoAccesRepo;
import com.example.demo.entity.Person;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
@EnableCaching
public class PersonControlApplication implements CommandLineRunner{
	 @Value("${spring.datasource.url}")
	  private String dbUrl;

	  @Autowired
	  private DataSource dataSource;

	@Autowired
	DaoAccesRepo accesRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(PersonControlApplication.class, args);

	}
	  @Bean
	  public DataSource dataSource() throws SQLException {
	    if (dbUrl == null || dbUrl.isEmpty()) {
	      return new HikariDataSource();
	    } else {
	      HikariConfig config = new HikariConfig();
	      config.setJdbcUrl(dbUrl);
	      return new HikariDataSource(config);
	    }
	  }
	@Override
	public void run(String... args) throws Exception {
		 try (Connection connection = dataSource.getConnection()) {
		      Statement stmt = connection.createStatement();
		      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
		      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
		      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

		      ArrayList<String> output = new ArrayList<String>();
		      while (rs.next()) {
		        output.add("Read from DB: " + rs.getTimestamp("tick"));
		      }

		    
		    } catch (Exception e) {
		     
		    }
	accesRepo.save(new Person(0, "admin", "admin", 1, "ROLE_ADMIN", "admin", "admin"));
	//	accesRepo.save(new Person(0, "user", "user", 2, "ROLE_USER", "user", "user"));
	//	accesRepo.save(new Person(0, "test", "DWAWDA", 22, "ROLE_USER", "DWAWDA", "DWAWDA"));
		
	}




}
