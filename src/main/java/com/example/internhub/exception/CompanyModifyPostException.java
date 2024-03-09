package com.example.internhub.exception;

public class CompanyModifyPostException extends  Exception{
    public CompanyModifyPostException() {
        super ("Cannot create posts for the company that the user isn't in.");
    }
}
