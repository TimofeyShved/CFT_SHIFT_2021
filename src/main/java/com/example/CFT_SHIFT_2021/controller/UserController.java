package com.example.CFT_SHIFT_2021.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                        // так как порт 8080 был занят, я его поменял на http://localhost:8070/user/ ------- не знаю, как у вас там всё устроено ¯\_(ツ)_/¯
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public ResponseEntity getUser(){
        try {
            return ResponseEntity.ok("RUN SERVER, ok!");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("error");
        }
    }
}
