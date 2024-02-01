package com.example.internhub.exception;

public class CompNotFoundException extends Exception {
    public CompNotFoundException(String compId) {
        super("Company id " + compId + " not found.");
    }
}
