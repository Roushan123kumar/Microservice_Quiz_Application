package com.telusko.quizservice;

import com.telusko.quizservice.dao.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@EnableFeignClients


public class QuizServiceApplication {


	public static void main(String[] args) {

		SpringApplication.run(QuizServiceApplication.class, args);
	}
}
