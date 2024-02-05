package com.example.internhub.exception;

public class EmptyPositionListException extends Exception {
    public EmptyPositionListException() {
        super("At least one position is required.");
    }
}
