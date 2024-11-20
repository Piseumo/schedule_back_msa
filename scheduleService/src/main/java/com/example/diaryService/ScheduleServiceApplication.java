package com.example.diaryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScheduleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleServiceApplication.class, args);
	}

}
