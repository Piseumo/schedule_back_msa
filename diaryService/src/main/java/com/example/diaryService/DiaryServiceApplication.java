package com.example.diaryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaryServiceApplication.class, args);
	}

}
