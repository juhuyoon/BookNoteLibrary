package com.example.booknoteconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BooknoteConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooknoteConfigServerApplication.class, args);
	}

}
