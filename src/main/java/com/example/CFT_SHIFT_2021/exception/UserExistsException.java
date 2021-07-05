package com.example.CFT_SHIFT_2021.exception;

import org.springframework.http.ResponseEntity;

public class UserExistsException  extends  Exception{
    public UserExistsException(String message) {
        super(message);
    }
}
