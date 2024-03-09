package com.example.internhub.exception;

public class CompanyModifyPostException extends  Exception{
    public CompanyModifyPostException() {
        super ("Cannot modify posts for the company that the user isn't in.");
    }
}
