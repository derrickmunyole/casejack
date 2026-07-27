package com.example.casemanagementplatform.common.exceptions;

public class CaseNotFoundException extends RuntimeException{
    public CaseNotFoundException(String message){
        super(message);
    }
}
