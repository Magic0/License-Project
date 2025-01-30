package com.mitocode.licensejob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LicenseJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicenseJobApplication.class, args);
	}

}
