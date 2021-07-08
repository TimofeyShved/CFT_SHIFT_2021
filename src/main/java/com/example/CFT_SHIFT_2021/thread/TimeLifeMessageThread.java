package com.example.CFT_SHIFT_2021.thread;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

// ----------------------------------------------------------------------------------------------------  TimeLife
@Component
@Scope("prototype")
public class TimeLifeMessageThread extends Thread{
    Long timeLife;
    Long timeDelay;
    MessageEntity message;

    @Autowired
    private MessageCRUD messageCRUD;

    public void set(Long timeLife, Long timeDelay, MessageEntity message) {
        this.timeLife=timeLife;
        this.timeDelay=timeDelay;
        this.message=message;
    }

    @Override
    public void run() {
        try {
            Thread.yield(); // даем возможность выбрать другой поток
            TimeUnit.SECONDS.sleep(timeDelay); // отправляем спать этот поток, не объязательно, но помогает, лишний раз не ждать
            message.setMessageId((messageCRUD.save(message)).getMessageId());
            Thread.yield();
            TimeUnit.SECONDS.sleep(timeLife-timeDelay);
            System.out.println(timeLife-timeDelay);
            messageCRUD.deleteById(message.getMessageId());
            this.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}