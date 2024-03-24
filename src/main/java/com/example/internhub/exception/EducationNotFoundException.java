package com.example.internhub.exception;

public class EducationNotFoundException extends Exception{
    public EducationNotFoundException(String educationId) {
        super("Education id " + educationId+ " not found.");
    }
}
