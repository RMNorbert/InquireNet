package com.rmnnorbert.InquireNet.utils;

import org.springframework.http.ResponseEntity;

public class Response {
    public static ResponseEntity<String> successful(String message){
        return ResponseEntity.ok(message + " successfully");
    }
}
