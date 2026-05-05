package com.tuevento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TueventoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TueventoApplication.class);
		app.setAllowCircularReferences(true);
		app.run(args);
	}
}