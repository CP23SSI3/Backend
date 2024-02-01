package com.example.internhub.exception;

public class PostNotFoundException extends Exception{
    public PostNotFoundException(String postId) {
        super("Post id " + postId + " not found.");
    }
}
