package com.example.internhub.exception;

public class PositionTagNotFoundException extends Exception{
    public PositionTagNotFoundException(String positionTagId) {
        super("Position tag id " + positionTagId + " not found.");
    }
}
