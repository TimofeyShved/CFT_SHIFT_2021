package com.example.CFT_SHIFT_2021.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ChatEntity {// ----------------------------------------------- наше с вами сообщение

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    private long chatId;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatId")
    private List<MessageEntity> message;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatId")
    private List<MessageEntity> participants;

    public ChatEntity(){ // конструктор
    }

    // ----------------------------------------------- гетеры и сетеры
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
