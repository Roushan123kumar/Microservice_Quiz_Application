package com.roushan.quizservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mirrors question-service's QuestionWrapper field-for-field.
 * This is the object Jackson must deserialize the Feign response into,
 * so its shape (names + types) MUST exactly match the JSON the
 * question-service controller actually returns.
 *
 * @JsonIgnoreProperties(ignoreUnknown = true) is a safety net: if the
 * question-service side ever adds a field (e.g. rightAnswer, category),
 * this side won't blow up trying to map it.
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