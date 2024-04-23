package com.example.internhub.exception;

public class EmailExistedException extends Exception{
    public EmailExistedException() {
        super("The registered email has an existing account.");
    }
}
