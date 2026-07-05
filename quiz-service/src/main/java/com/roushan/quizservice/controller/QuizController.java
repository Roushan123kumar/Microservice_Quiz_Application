package com.roushan.quizservice.controller;

import com.roushan.quizservice.model.QuestionWrapper;
import com.roushan.quizservice.model.QuizDto;
import com.roushan.quizservice.model.Response;
import com.roushan.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(
            @RequestParam String category, 
            @RequestParam int numQ, 
            @RequestParam String title
    ) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") Integer id) {
        System.out.println(">>> ENTERED controller with id=" + id);
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("id") Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}