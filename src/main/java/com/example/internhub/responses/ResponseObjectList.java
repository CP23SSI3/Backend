package com.example.internhub.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseObjectList {
    private int status;
    private String message;
    private List<?> data;
}