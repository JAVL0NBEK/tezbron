package com.example.bron.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final List<String> details;

    public ResourceNotFoundException(String message, List<String> details) {
        super(message);
        this.details = details;
    }

}
