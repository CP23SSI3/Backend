package com.example.internhub.exception;

public class AddressIsRequiredException extends Exception{
    public AddressIsRequiredException(){
        super("Address is required.");
    }
}
