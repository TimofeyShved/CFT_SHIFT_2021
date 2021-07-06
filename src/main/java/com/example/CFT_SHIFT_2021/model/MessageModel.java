package com.example.CFT_SHIFT_2021.model;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;

public class MessageModel {
    private Long userId;
    private String text;
    private String sendTime;

    public static MessageModel toMpdel (MessageEntity entity){
        MessageModel model = new MessageModel();
        model.setUserId(entity.getUserId());
        model.setText(entity.getText());
        model.setSendTime(entity.getSendTime());
        return model;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
