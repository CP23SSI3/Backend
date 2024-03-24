package com.example.internhub.exception;

public class NoEditedDataException extends Exception{
    public NoEditedDataException() {
        super("There is no data that need to be edited.");
    }
}
