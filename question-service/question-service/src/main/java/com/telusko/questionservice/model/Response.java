package com.telusko.questionservice.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    // it will give question id and the options selected by the user
    private Integer id;
    private String response;
}
