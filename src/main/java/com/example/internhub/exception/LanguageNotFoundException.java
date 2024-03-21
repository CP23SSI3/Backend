package com.example.internhub.exception;

public class LanguageNotFoundException extends Exception {
    public LanguageNotFoundException (String languageId)  {
        super("Post id " + languageId + " not found.");
    }
}
