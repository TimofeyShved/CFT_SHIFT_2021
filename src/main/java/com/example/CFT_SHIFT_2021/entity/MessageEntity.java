package com.example.CFT_SHIFT_2021.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class MessageEntity {// ----------------------------------------------- наше с вами сообщение

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    private long messageId;
    private String text;
    private long lifetimeSec, delaySec;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "sendTime")
    private String sendTime;

    @JoinColumn(name = "userId")
    private Long userId;

    @JoinColumn(name = "chatId")
    private Long chatId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId")
    private List<UnReadEntity> unread;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
