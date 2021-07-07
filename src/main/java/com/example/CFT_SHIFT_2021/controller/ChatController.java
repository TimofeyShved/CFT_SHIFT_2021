package com.example.CFT_SHIFT_2021.controller;

import com.example.CFT_SHIFT_2021.entity.ChatEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.ChatExistsException;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.repository.ChatCRUD;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import com.example.CFT_SHIFT_2021.service.ChatService;
import com.example.CFT_SHIFT_2021.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatCRUD chatCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/chat") // создать
    public ResponseEntity registration(@RequestBody ChatEntity chat) throws Exception {
        try {
            chatService.registration(chat);
            return ResponseEntity.ok(chat);
        }catch (ChatExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR_CHAT_EXISTS");
        }
    }

    @GetMapping("/chats") // взять
    public ResponseEntity getChats(){
        try {
            return ResponseEntity.ok(chatCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: CHAT_NOT_FOUND");
        }
    }
}
