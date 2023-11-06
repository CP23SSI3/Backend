package com.example.internhub.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ResponseObject {
    private int status;
    private String message;
    private ResponseData data;
}
