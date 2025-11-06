package com.example.bron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class BronApplication {

	public static void main(String[] args) {
		SpringApplication.run(BronApplication.class, args);
	}

}
