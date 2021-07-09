package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.ChatEntity;
import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.ChatExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.model.MessageModel;
import com.example.CFT_SHIFT_2021.repository.ChatCRUD;
import com.example.CFT_SHIFT_2021.repository.ParticipantsCRUD;
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParticipantsService {

    @Autowired
    private ParticipantsCRUD participantsCRUD; // создаём интерфейс для взаимодействия с бд

    public ParticipantsEntity enterParticipants(ParticipantsEntity participantsEntity) throws Exception {
        if (ParticipantsCoincidence(participantsEntity)!=null){
            throw new ChatExistsException("code: ERROR_YOU_ARE_LOGGED_IN_TO_THE_CHAT");
        }
        return participantsCRUD.save(participantsEntity);
    }

    public void leaveParticipants(ParticipantsEntity participantsEntity) throws Exception {
        Long participantsId = ParticipantsCoincidence(participantsEntity);
        if (participantsId!=null){
            participantsCRUD.deleteById(participantsId);
        }else {
            throw new ChatExistsException("code: ERROR_YOU_ARE_LOGGED_IN_TO_THE_CHAT");
        }
    }

    public Long ParticipantsCoincidence (ParticipantsEntity participantsEntity){
        ArrayList<ParticipantsEntity> arrayListParticipants = new ArrayList<>();
        for(ParticipantsEntity p:participantsCRUD.findAll()){
            arrayListParticipants.add(p);
        }
        Long userId = participantsEntity.getUserId();
        Long chatId = participantsEntity.getChatId();
        Long coincidence = null;
        for(ParticipantsEntity p:arrayListParticipants){
            if (((userId) == p.getUserId())&&((chatId) == p.getChatId())){
                coincidence = p.getParticipantsId();
                break;
            }
        }
        return coincidence;
    }
}
