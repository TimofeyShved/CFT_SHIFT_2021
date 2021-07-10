package com.example.CFT_SHIFT_2021.controller;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.UnReadEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.service.MessageService;
import com.example.CFT_SHIFT_2021.service.UnReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.RequestWrapper;

@RestController
@RequestMapping
public class MessageController {


    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageCRUD messageCRUD; // создаём интерфейс для взаимодействия с бд

    @Autowired
    private UnReadService unReadService;

    @RequestMapping(value = "/message",method = RequestMethod.POST) // создать
    public ResponseEntity registration(@RequestBody MessageEntity message) throws Exception {
        try {
            MessageEntity NewMessage = messageService.registration(message);
            unReadService.registration(NewMessage);
            return ResponseEntity.ok(NewMessage);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: USER_NOT_FOUND_ERROR_MESSAGE");
        }
    }

    //@GetMapping("/messages") // взять
    @RequestMapping(value = "/messages",method = RequestMethod.GET, params = {"chatId"})
    public ResponseEntity getMessages(@RequestParam Long chatId){
        try {
            return ResponseEntity.ok(messageService.getAllMessage(chatId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: MESSAGE_NOT_FOUND");
        }
    }

    @RequestMapping(value = "/messages",method = RequestMethod.GET)
    public ResponseEntity getMessages(){
        try {
            return ResponseEntity.ok(messageService.getAllMessage(null));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: MESSAGE_NOT_FOUND");
        }
    }

    @RequestMapping(value = "/messages",method = RequestMethod.DELETE, params = {"messageId"})
    public ResponseEntity deleteOne(@PathVariable Long messageId) throws Exception {
        try {
            return ResponseEntity.ok(messageService.deleteOneMessage(messageId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

}
