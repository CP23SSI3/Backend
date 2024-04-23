package com.example.internhub.exception;

public class UserModifySkillException extends Exception{
    public UserModifySkillException() {
        super("Cannot modify user's skill profile that isn't yours.");
    }
}
