package com.example.internhub.exception;

public class AddressNotFoundException extends Exception{
    public AddressNotFoundException(String addressId) {
        super("Address id " + addressId + " not found.");
    }
}
