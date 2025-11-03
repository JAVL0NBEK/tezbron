package com.example.bron.exception;

import java.util.List;

public class ResourceConflictException extends RuntimeException {
    private final List<String> details;
    public ResourceConflictException(String message, List<String> details) {
        super(message);
        this.details = details;
    }
}
