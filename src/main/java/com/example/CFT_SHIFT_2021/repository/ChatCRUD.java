package com.example.CFT_SHIFT_2021.repository;

import com.example.CFT_SHIFT_2021.entity.ChatEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatCRUD extends CrudRepository<ChatEntity, Long> { // наследуемый интерфейс для изменения данных в бд
    ChatEntity findChatEntityByName(String name);
}
