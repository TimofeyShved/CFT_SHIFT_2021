package com.example.CFT_SHIFT_2021.thread;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

// ----------------------------------------------------------------------------------------------------  TimeLife
@Component
@Scope("prototype")
public class TimeLifeMessageThread{

    ArrayList<MessageEntity> arrayListMessage=new ArrayList<>();

    @Autowired
    public MessageCRUD messageCRUD;

    public void set(MessageEntity message) {
        this.arrayListMessage.add(message);
    }

    @Scheduled(fixedRate = 1000)
    public void run() {
        if (arrayListMessage!=null) {
            ArrayList<MessageEntity> newArrayListMessage = new ArrayList<>();
            for(MessageEntity m:arrayListMessage){

                if(m.getDelaySec()>=0){
                    m.setDelaySec(m.getDelaySec()-1);

                    if (m.getDelaySec()==-1){
                        m.setMessageId((messageCRUD.save(m)).getMessageId());
                    }
                }
                else {
                    if(m.getLifetimeSec()>0){
                        m.setLifetimeSec(m.getLifetimeSec()-1);

                        if (m.getLifetimeSec()==0){
                            messageCRUD.deleteById(m.getMessageId());
                        }
                    }else {
                        m=null;
                    }
                }
                if (m!=null) {
                    newArrayListMessage.add(m);
                }
            }
            arrayListMessage = newArrayListMessage;
        }
    }
}