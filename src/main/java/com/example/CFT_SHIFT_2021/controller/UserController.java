package com.example.CFT_SHIFT_2021.controller;

import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController              // так как порт 8080 был занят, я его поменял на http://localhost:8070/user/ ------- не знаю, как у вас там всё устроено ¯\_(ツ)_/¯
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/") // создать
    public ResponseEntity registration(@RequestBody UserEntity user){
        try {
            userCRUD.save(user);
            return ResponseEntity.ok("Save user");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("error");
        }
    }

    @GetMapping("/") // взять
    public ResponseEntity getUser(){
        try {
            return ResponseEntity.ok("RUN SERVER, ok!");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("error");
        }
    }
}
