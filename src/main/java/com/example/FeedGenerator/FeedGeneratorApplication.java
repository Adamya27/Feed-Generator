package com.example.FeedGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FeedGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedGeneratorApplication.class, args);
	}

}
