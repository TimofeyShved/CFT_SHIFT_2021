package com.example.CFT_SHIFT_2021.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class MessageEntity {// ----------------------------------------------- наше с вами сообщение

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    private long messageId;
    private String text;
    private long lifetimeSec, delaySec;
    private Date sendTime;
    private boolean unRead;

    @JoinColumn(name = "userId")
    private Long userId;

    public MessageEntity(){ // конструктор
    }

    // ----------------------------------------------- гетеры и сетеры


    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLifetimeSec() {
        return lifetimeSec;
    }

    public void setLifetimeSec(long lifetimeSec) {
        this.lifetimeSec = lifetimeSec;
    }

    public long getDelaySec() {
        return delaySec;
    }

    public void setDelaySec(long delaySec) {
        this.delaySec = delaySec;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isUnRead() {
        return unRead;
    }

    public void setUnRead(boolean unRead) {
        this.unRead = unRead;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
