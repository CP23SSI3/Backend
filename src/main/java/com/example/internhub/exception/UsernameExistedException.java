package com.example.internhub.exception;

public class UsernameExistedException extends Exception{
    public UsernameExistedException() {
        super("The registered username has an existing account.");
    }
}
