package com.example.bootjdbctemplateboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.controller","com.example.model","com.example.bootjdbctemplateboard"})
public class BootJdbcTemplateBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootJdbcTemplateBoardApplication.class, args);
	}

}
