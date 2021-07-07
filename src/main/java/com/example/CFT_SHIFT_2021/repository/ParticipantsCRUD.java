package com.example.CFT_SHIFT_2021.repository;

import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantsCRUD extends CrudRepository<ParticipantsEntity, Long> { // наследуемый интерфейс для изменения данных в бд
    UserEntity findParticipantsEntityByUserId(Long userId);
    UserEntity findParticipantsEntityByChatId(Long chatId);
}
