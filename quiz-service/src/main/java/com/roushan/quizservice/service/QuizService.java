package com.roushan.quizservice.service;

import com.roushan.quizservice.dao.QuizDao;
import com.roushan.quizservice.feign.QuizInterface;
import com.roushan.quizservice.model.QuestionWrapper;
import com.roushan.quizservice.model.Quiz;
import com.roushan.quizservice.model.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    @CircuitBreaker(name = "questionService", fallbackMethod = "createQuizFallback")
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> createQuizFallback(String category, int numQ, String title, Exception e) {
        log.error("createQuiz fallback triggered for category='{}', numQ={}: {}", category, numQ, e.toString(), e);
        return new ResponseEntity<>("Question Service is currently unavailable. Cannot create quiz.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name = "questionService", fallbackMethod = "getQuizQuestionsFallback")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        // Guard the DB lookup separately from the Feign call so a missing
        // quiz id (404-worthy) never gets misreported as an "unavailable"
        // question-service (503) - these are different failure modes.
        Optional<Quiz> quizOpt = quizDao.findById(id);
        if (quizOpt.isEmpty()) {
            log.warn("getQuizQuestions: no quiz found for id={}", id);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOpt.get();
        List<Integer> questionIds = quiz.getQuestionIds();

        if (questionIds == null || questionIds.isEmpty()) {
            log.warn("getQuizQuestions: quiz id={} has no question ids stored", id);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        log.debug("Fetching {} question(s) from question-service for quiz id={}: {}",
                questionIds.size(), id, questionIds);

        return quizInterface.getQuestionsFromId(questionIds);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsFallback(Integer id, Exception e) {
        // Logging the real cause is what turns a mystery 503 into an
        // actionable stack trace - check this log line first.
        log.error("getQuizQuestions fallback triggered for quiz id={}: {}", id, e.toString(), e);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name = "questionService", fallbackMethod = "calculateResultFallback")
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        return quizInterface.getScore(responses);
    }

    public ResponseEntity<Integer> calculateResultFallback(Integer id, List<Response> responses, Exception e) {
        log.error("calculateResult fallback triggered for quiz id={}: {}", id, e.toString(), e);
        return new ResponseEntity<>(-1, HttpStatus.SERVICE_UNAVAILABLE);
    }
}