package com.rmnnorbert.InquireNet.customExceptionHandler;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException() {
        super("Wrong username or password");
    }
}
