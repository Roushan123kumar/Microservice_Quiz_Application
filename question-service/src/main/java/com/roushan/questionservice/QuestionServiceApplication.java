package com.roushan.questionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//is a combination of 3 annotations:
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan  ---Spring scans all classes inside: com.roushan.questionservice
//                   and all its sub-packages: service  dao model controller
public class QuestionServiceApplication {

	public static void main(String[] args) {
		//When this runs, Spring Boot starts the entire application.
		SpringApplication.run(QuestionServiceApplication.class, args);
	}

}
