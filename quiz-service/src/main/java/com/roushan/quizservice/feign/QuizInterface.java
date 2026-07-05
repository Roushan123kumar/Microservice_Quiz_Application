package com.roushan.quizservice.feign;

import com.roushan.quizservice.model.QuestionWrapper;
import com.roushan.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Explicit url = "http://127.0.0.1:8080" bypasses Eureka/Ribbon load-balancing
 * (and any local "localhost" -> ::1 vs 127.0.0.1 DNS loopback weirdness on
 * Windows) and forces every call straight at question-service's known port.
 *
 * name = "QUESTION-SERVICE" is still required by @FeignClient even when a
 * fixed url is supplied - it's just used to name the Feign client / circuit
 * breaker registry entry, it is NOT used for service discovery here.
 */
@FeignClient(name = "QUESTION-SERVICE", url = "http://127.0.0.1:8080")
public interface QuizInterface {

    @GetMapping(value = "/question/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("numQuestions") Integer numQuestions
    );

    @PostMapping(
            value = "/question/getQuestions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(
            @RequestBody List<Integer> questionIds
    );

    @PostMapping(
            value = "/question/getScore",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Integer> getScore(
            @RequestBody List<Response> responses
    );
}