package com.example.internhub.exception;

public class UserModifyLanguageException extends Exception {
    public UserModifyLanguageException() {
        super("Cannot modify user's language profile that isn't yours.");
    }
}
