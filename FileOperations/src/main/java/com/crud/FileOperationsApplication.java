package com.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.crud.entity")
@EnableMongoRepositories("com.crud.repo")
public class FileOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileOperationsApplication.class, args);
	}

}
