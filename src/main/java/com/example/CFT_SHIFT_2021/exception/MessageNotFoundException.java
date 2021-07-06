package com.example.CFT_SHIFT_2021.exception;

import org.springframework.http.ResponseEntity;

public class MessageNotFoundException extends Exception {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
