package com.example.CFT_SHIFT_2021.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UnReadController {

    @RequestMapping(value = "/messages/unread",method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity getMessages(@RequestParam Long userId){
        try {
            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: MESSAGE_NOT_FOUND");
        }
    }

    @RequestMapping(value = "/messages/unread",method = RequestMethod.GET, params = {"userId", "chatID"})
    public ResponseEntity getMessages(@RequestParam Long userId, @RequestParam Long chatId){
        try {
            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: MESSAGE_NOT_FOUND");
        }
    }
}
