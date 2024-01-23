package com.example.database;

import com.example.database.entity.graal1.MyData1;
import com.example.database.entity.graal1.MyDataRepository1;
import com.example.database.entity.graal2.MyData2;
import com.example.database.entity.graal2.MyDataRepository2;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}

	@Autowired
	MyDataRepository1 repository1;

	@Autowired
	MyDataRepository2 repository2;

	@PostConstruct
	void init() {
		MyData1 data1 = new MyData1();
		data1.setId(String.valueOf(System.currentTimeMillis()));
		data1.setSomeData("Hello World 1!");
		repository1.save(data1);

		MyData2 data2 = new MyData2();
		data2.setId(String.valueOf(System.currentTimeMillis()));
		data2.setSomeData("Hello World 2!");
		repository2.save(data2);

	}
}
