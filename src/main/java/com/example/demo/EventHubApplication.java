package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "Model")
@EnableJpaRepositories(basePackages = "Repository") 
@ComponentScan(basePackages = "SeedData")
public class EventHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventHubApplication.class, args);
	}

}
