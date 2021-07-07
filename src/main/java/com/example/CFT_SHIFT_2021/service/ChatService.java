package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.ChatEntity;
import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.repository.ChatCRUD;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class ChatService {

    @Autowired
    private ChatCRUD chatCRUD; // создаём интерфейс для взаимодействия с бд

    public ChatEntity registration(ChatEntity chat) throws Exception {
        if (chatCRUD.findChatEntityByName(chat.getName())!=null) {
            throw new UserNotFoundException("code: USER_NOT_FOUND");
        }
        return chatCRUD.save(chat);
    }
    
}