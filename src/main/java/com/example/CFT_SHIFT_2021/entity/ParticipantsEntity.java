package com.example.CFT_SHIFT_2021.entity;

import javax.persistence.*;

@Entity
public class ParticipantsEntity {// ----------------------------------------------- наше с вами сообщение

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    private long participantsId;

    @JoinColumn(name = "userId")
    private Long userId;

    @JoinColumn(name = "chatId")
    private Long chatId;

    public ParticipantsEntity(){ // конструктор
    }

    // ----------------------------------------------- гетеры и сетеры

    public long getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(long participantsId) {
        this.participantsId = participantsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
