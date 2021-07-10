package com.example.CFT_SHIFT_2021.entity;

import javax.persistence.*;

@Entity
public class UnReadEntity {// ----------------------------------------------- наше с вами сообщение

    // ----------------------------------------------- переменные UnReadController
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    private long unReadId;

    @JoinColumn(name = "userId")
    private Long userId;

    @JoinColumn(name = "messageId")
    private Long messageId;

    public UnReadEntity(){ // конструктор
    }

    // ----------------------------------------------- гетеры и сетеры


    public long getUnReadId() {
        return unReadId;
    }

    public void setUnReadId(long unReadId) {
        this.unReadId = unReadId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
