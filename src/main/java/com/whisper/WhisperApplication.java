package com.whisper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WhisperApplication {
	public static void main(String[] args) {
		SpringApplication.run(WhisperApplication.class, args);
	}

}
