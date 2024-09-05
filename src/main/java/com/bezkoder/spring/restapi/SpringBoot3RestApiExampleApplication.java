package com.bezkoder.spring.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.bezkoder.spring.restapi")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.bezkoder.spring.restapi.repository")

public class SpringBoot3RestApiExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3RestApiExampleApplication.class, args);
	}
}
