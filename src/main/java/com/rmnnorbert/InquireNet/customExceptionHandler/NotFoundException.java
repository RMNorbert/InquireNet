package com.rmnnorbert.InquireNet.customExceptionHandler;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message + " not found");
    }
}
