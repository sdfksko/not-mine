package com.example.nestco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.nestco")
public class NestcoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NestcoApplication.class, args);
	}

}
