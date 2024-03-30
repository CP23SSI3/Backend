package com.example.internhub.exception;

public class ExperienceNotFoundException extends Exception{
    public ExperienceNotFoundException (String experienceId) {
        super("Experience id " + experienceId + " not found.");
    }
}
