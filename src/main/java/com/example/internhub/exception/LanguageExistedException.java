package com.example.internhub.exception;

public class LanguageExistedException extends Exception{
    public LanguageExistedException(){
        super("The language skill is existed for the user's.");
    }
}
