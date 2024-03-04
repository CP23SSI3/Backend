package com.example.internhub.exception;

public class UserCreateCompanyException extends Exception{
    public UserCreateCompanyException() {
        super("User cannot create company.");
    }
}
