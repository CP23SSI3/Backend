package com.example.internhub.exception;

public class EmailExistedException extends Exception{
    public EmailExistedException() {
        super("A registered email has an existing account.");
    }
}
