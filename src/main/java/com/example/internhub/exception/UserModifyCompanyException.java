package com.example.internhub.exception;

public class UserModifyCompanyException extends Exception {
    public UserModifyCompanyException() {
        super("Cannot modify company's profile that you aren't in.");
    }
}
