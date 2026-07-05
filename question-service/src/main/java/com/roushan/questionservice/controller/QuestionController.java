package com.roushan.questionservice.controller;

//model
import com.roushan.questionservice.model.Question;
import com.roushan.questionservice.model.QuestionWrapper;
import com.roushan.questionservice.model.Response;

//service
import com.roushan.questionservice.service.QuestionService;

//It automatically creates object of service & environment.
import org.springframework.beans.factory.annotation.Autowired;
//Used to read application.properties values.
import org.springframework.core.env.Environment;
//Used to send proper HTTP response (status + body).
import org.springframework.http.ResponseEntity;
//Contains Spring REST annotations like:@RestController @GetMapping etc
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
//Used because we are returning List<> in many methods.
import java.util.List;

// iska questiondb se kuch lena dena nhi hai
// kuch v naam de sakte hai yaha jaise roushan,etc
// http://localhost:8080/roushan/.....
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, 
            @RequestParam Integer numQuestions
    ){
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(
            @RequestBody List<Integer> questionIds
    ){
        System.out.println("Current active instance port: " + environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(
            @RequestBody List<Response> responses
    ){
        return questionService.getScore(responses);
    }
}

    // generate
    // getQuestions (questionid)
    // getScore

