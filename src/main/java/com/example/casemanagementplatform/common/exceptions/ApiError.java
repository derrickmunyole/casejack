package com.example.casemanagementplatform.common.exceptions;

import java.time.LocalDateTime;

public class ApiError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ApiError(LocalDateTime timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
