package com.example.CFT_SHIFT_2021.controller;

import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import com.example.CFT_SHIFT_2021.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.RequestWrapper;

@RestController              // так как порт 8080 был занят, я его поменял на http://localhost:8070/user/ ------- не знаю, как у вас там всё устроено ¯\_(ツ)_/¯
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/user") // создать
    public ResponseEntity registration(@RequestBody UserEntity user) throws Exception {
        try {
            userService.registration(user);
            return ResponseEntity.ok(user);
        }catch (UserExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @GetMapping("/users") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(userCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: USER_NOT_FOUND");
        }
    }

    @GetMapping("/user/{userId}") // взять
    public ResponseEntity getOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.getOne(userId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @DeleteMapping("/user/{userId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.deleteOne(userId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @PutMapping("/user/{userId}") // удалить
    public ResponseEntity updateOne(@PathVariable Long userId, @RequestBody UserEntity user) throws Exception {
        try {
            return ResponseEntity.ok(userService.updateOne(userId, user));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }
}
