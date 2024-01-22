package com.example.database;

import com.example.database.entity.MyData;
import com.example.database.entity.MyDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}

	@Autowired
	MyDataRepository repository;

	@PostConstruct
	void init() {
		MyData data = new MyData();
		data.setId(String.valueOf(System.currentTimeMillis()));
		data.setSomeData("Hello World!");
		repository.save(data);
	}
}
