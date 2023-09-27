package com.vmo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ManageFresherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageFresherApplication.class, args);
	}

}
