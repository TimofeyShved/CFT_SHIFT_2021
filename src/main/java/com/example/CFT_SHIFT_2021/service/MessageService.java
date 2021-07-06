package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.MessageNotFoundException;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageCRUD messageCRUD; // создаём интерфейс для взаимодействия с бд

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    public MessageEntity registration(MessageEntity message) throws Exception {
        UserEntity user = userCRUD.findById(message.getUserId()).get();
        if (user==null){
            throw new UserNotFoundException("code: USER_NOT_FOUND");
        }
        return messageCRUD.save(message);
    }
}
