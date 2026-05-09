package com.example.reviewing_phase_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReviewingPhaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewingPhaseServiceApplication.class, args);
	}

}
