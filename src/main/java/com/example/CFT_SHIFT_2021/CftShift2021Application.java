package com.example.CFT_SHIFT_2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CftShift2021Application {

	public static void main(String[] args) {
		SpringApplication.run(CftShift2021Application.class, args);
	}

}
