package com.example.casemanagementplatform.common.exceptions;

public class FieldDefinitionAlreadyExistsException extends RuntimeException{
    public FieldDefinitionAlreadyExistsException(String message){
        super(message);
    }
}
