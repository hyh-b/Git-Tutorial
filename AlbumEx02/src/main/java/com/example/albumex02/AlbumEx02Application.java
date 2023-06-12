package com.example.albumex02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.model","com.example.controller","com.example.albumex02"})
public class AlbumEx02Application {

	public static void main(String[] args) {
		SpringApplication.run(AlbumEx02Application.class, args);
	}

}
