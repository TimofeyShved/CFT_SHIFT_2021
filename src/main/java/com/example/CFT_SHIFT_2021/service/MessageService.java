package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.MessageNotFoundException;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.model.MessageModel;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import com.example.CFT_SHIFT_2021.sorting.SortMessageTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        message.setSendTime(dateFormat.format(cal.getTime()));
        return messageCRUD.save(message);
    }

    public ArrayList<MessageModel> getAllMessage() throws Exception{
        ArrayList<MessageModel> arrayListMessage = new ArrayList<>();
        for(MessageEntity m:messageCRUD.findAll()){
            arrayListMessage.add(MessageModel.toMpdel(m));
        }
        return SortMessageTime.sort(arrayListMessage);
    }
}
