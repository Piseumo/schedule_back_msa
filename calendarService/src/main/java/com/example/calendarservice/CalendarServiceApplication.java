package com.example.calendarservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class CalendarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarServiceApplication.class, args);
	}

}
