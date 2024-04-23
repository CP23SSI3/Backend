package com.example.internhub.exception;

public class UserModifyUserException extends Exception {
    public UserModifyUserException(){
        super("Cannot modify user's information that isn't yours.");
    }
}
