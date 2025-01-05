package com.backend.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(BackendApplication.class);
		springApplication.setHeadless(false);
		springApplication.run(args);
	}

}
