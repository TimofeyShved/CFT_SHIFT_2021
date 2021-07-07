package com.example.CFT_SHIFT_2021.controller;

import com.example.CFT_SHIFT_2021.entity.ChatEntity;
import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
import com.example.CFT_SHIFT_2021.exception.ChatExistsException;
import com.example.CFT_SHIFT_2021.repository.ChatCRUD;
import com.example.CFT_SHIFT_2021.repository.ParticipantsCRUD;
import com.example.CFT_SHIFT_2021.service.ChatService;
import com.example.CFT_SHIFT_2021.service.ParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ParticipantsController {

    @Autowired
    private ParticipantsService participantsService;

    @Autowired
    private ParticipantsCRUD participantsCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("chat/enter") // создать
    public ResponseEntity enterParticipants(@RequestBody ParticipantsEntity participantsEntity) throws Exception {
        try {
            participantsService.enterParticipants(participantsEntity);
            return ResponseEntity.ok(participantsEntity);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR_YOU_ARE_LOGGED_IN_TO_THE_CHAT");
        }
    }

    @PostMapping("chat/leave") // создать
    public ResponseEntity leaveParticipants(@RequestBody ParticipantsEntity participantsEntity) throws Exception {
        try {
            participantsService.leaveParticipants(participantsEntity);
            return ResponseEntity.ok(participantsEntity);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR_YOU_ARE_DONT_LOGGED_IN_TO_THE_CHAT");
        }
    }
}
