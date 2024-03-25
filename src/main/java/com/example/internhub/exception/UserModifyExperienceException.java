package com.example.internhub.exception;

public class UserModifyExperienceException extends Exception{
    public UserModifyExperienceException () {
        super("Cannot modify user's experience profile that isn't yours.");
    }
}
