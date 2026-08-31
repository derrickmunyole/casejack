package com.example.casemanagementplatform.common.exceptions;

public class ObjectDefinitionAlreadyExistsException extends RuntimeException {
    public ObjectDefinitionAlreadyExistsException(String message) {
        super(message);
    }
}
