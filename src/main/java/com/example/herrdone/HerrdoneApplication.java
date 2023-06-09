package com.example.herrdone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class HerrdoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerrdoneApplication.class, args);
	}

}