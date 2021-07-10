package com.example.CFT_SHIFT_2021.controller;

import com.example.CFT_SHIFT_2021.service.UnReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UnReadController {

    @Autowired
    private UnReadService unReadService;

    @RequestMapping(value = "/messages/unread",method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity getUnRead(@RequestParam Long userId){
        try {
            return ResponseEntity.ok(unReadService.getAllUnRead(userId, null));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: MESSAGE_NOT_FOUND");
        }
    }

    @RequestMapping(value = "/messages/unread",method = RequestMethod.GET, params = {"userId", "chatId"})
    public ResponseEntity getUnRead(@RequestParam Long userId, @RequestParam Long chatId){
        try {
            return ResponseEntity.ok(unReadService.getAllUnRead(userId, chatId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: MESSAGE_NOT_FOUND");
        }
    }
}
