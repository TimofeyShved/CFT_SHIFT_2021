package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
import com.example.CFT_SHIFT_2021.entity.UnReadEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.model.MessageModel;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.repository.ParticipantsCRUD;
import com.example.CFT_SHIFT_2021.repository.UnReadCRUD;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import com.example.CFT_SHIFT_2021.sorting.SortMessageTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@EnableScheduling
public class UnReadService {

    @Autowired
    private UnReadCRUD unReadCRUD;

    @Autowired
    private UserCRUD userCRUD;

    @Autowired
    private MessageCRUD messageCRUD;

    @Autowired
    private ParticipantsCRUD participantsCRUD;
    @Autowired
    private ParticipantsService participantsService;

    public void registration(MessageEntity message) throws Exception {
        // ------------------------------------------------ это приватная переписка?
        if (message.getChatId()!=null){
            // параметры поиска
            ParticipantsEntity p = new ParticipantsEntity();
            p.setUserId(message.getUserId());
            p.setChatId(message.getChatId());

            if (participantsService.ParticipantsCoincidence(p)==null){    // человек в ней состоит?
                throw new UserNotFoundException("code: CHAT_NOT_FOUND");    // выдать ошибку о том что он не найден
            }else {
                for(ParticipantsEntity pr:participantsCRUD.findAll()){ // указать непрочитаные сообщения чату
                    if ((message.getUserId()!=pr.getUserId())&&(pr.getChatId()==message.getChatId())){
                        UnReadEntity unReadEntity = new UnReadEntity();
                        unReadEntity.setUserId(pr.getUserId());
                        unReadEntity.setMessageId(message.getMessageId());
                        unReadCRUD.save(unReadEntity);
                    }
                }
            }
        }else {
            for(UserEntity u:userCRUD.findAll()) { // указать нерочитаное сообщение для всех
                if (message.getUserId()!=u.getUserId()){
                    UnReadEntity unReadEntity = new UnReadEntity();
                    unReadEntity.setUserId(u.getUserId());
                    unReadEntity.setMessageId(message.getMessageId());
                    unReadCRUD.save(unReadEntity);
                }
            }
        }
    }


    public ArrayList<MessageModel> getAllUnRead(Long userId, Long chatId) throws Exception{
        ArrayList<MessageModel> arrayListMessage = new ArrayList<>();
        for(UnReadEntity u:unReadCRUD.findAll()){
            if ((u.getUserId()==userId)&&(((messageCRUD.findById(u.getMessageId())).get()).getChatId()==chatId)){
                arrayListMessage.add(MessageModel.toMpdel((messageCRUD.findById(u.getMessageId())).get()));
                deleteUnRead(u.getUnReadId());
            }
        }
        return SortMessageTime.sort(arrayListMessage);
    }

    public void deleteUnRead(Long getUnReadId){
        unReadCRUD.deleteById(getUnReadId);
    }
}
