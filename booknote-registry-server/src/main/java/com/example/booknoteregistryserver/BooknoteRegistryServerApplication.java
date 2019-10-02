package com.example.booknoteregistryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BooknoteRegistryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooknoteRegistryServerApplication.class, args);
	}

}
