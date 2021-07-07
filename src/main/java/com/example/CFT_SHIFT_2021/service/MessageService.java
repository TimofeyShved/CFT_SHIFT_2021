package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
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

    @Autowired
    private ParticipantsService participantsService;

    public MessageEntity registration(MessageEntity message) throws Exception { // добавить сообщение в бд
        // находим человека по Id
        UserEntity user = userCRUD.findById(message.getUserId()).get();

        if (user==null){    // он существует?
            throw new UserNotFoundException("code: USER_NOT_FOUND");    // выдать ошибку о том что он не найден
        }

        // устанавливаем дату в сообщении
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        Calendar cal = Calendar.getInstance(); // вытащить дату из системы
        message.setSendTime(dateFormat.format(cal.getTime())); // отправить дату в переменую

        if (message.getChatId()!=null){ // это приватная переписка?
            // параметры поиска
            ParticipantsEntity p = new ParticipantsEntity();
            p.setUserId(user.getUserId());
            p.setChatId(message.getChatId());

            if (participantsService.ParticipantsCoincidence(p)==null){    // человек в ней состоит?
                throw new UserNotFoundException("code: CHAT_NOT_FOUND");    // выдать ошибку о том что он не найден
            }
        }

        return messageCRUD.save(message); // сохранить
    }

    public ArrayList<MessageModel> getAllMessage() throws Exception{  // получить список всех сообщений из бд, которые для всех
        ArrayList<MessageModel> arrayListMessage = new ArrayList<>();
        for(MessageEntity m:messageCRUD.findAll()){
            if (m.getChatId()==null)
                arrayListMessage.add(MessageModel.toMpdel(m));
        }
        return SortMessageTime.sort(arrayListMessage);
    }
}
