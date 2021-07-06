package com.example.CFT_SHIFT_2021.repository;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageCRUD extends CrudRepository<MessageEntity, Long> { // наследуемый интерфейс для изменения данных в бд
    MessageEntity findMessageEntityByText(String text);
}
