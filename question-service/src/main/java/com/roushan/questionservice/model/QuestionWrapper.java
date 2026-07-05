package com.roushan.questionservice.model;

// Question without answer (for quiz)

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the object question-service SERIALIZES into JSON and sends back
 * to quiz-service. It must stay field-identical to
 * quiz-service's com.roushan.quizservice.model.QuestionWrapper, since that's
 * the class Feign deserializes the response into on the other end.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionWrapper {

    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}