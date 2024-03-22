package com.example.internhub.exception;

public class UserModifyEducationException extends Exception {
    public UserModifyEducationException() {
        super("Cannot modify user's education profile that isn't yours.");
    }
}
