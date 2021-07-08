package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.config.TimeLifeMessageThreadAppConfig;
import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import com.example.CFT_SHIFT_2021.entity.ParticipantsEntity;
import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.MessageNotFoundException;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.model.MessageModel;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import com.example.CFT_SHIFT_2021.sorting.SortMessageTime;
import com.example.CFT_SHIFT_2021.thread.TimeLifeMessageThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class MessageService {

    @Autowired
    private MessageCRUD messageCRUD; // создаём интерфейс для взаимодействия с бд

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    @Autowired
    private ParticipantsService participantsService;

    @Autowired
    private TimeLifeMessageThread timeLife;

    // ---------------------------------------------------------------------------------------------------- добавить сообщение в бд
    public MessageEntity registration(MessageEntity message) throws Exception {
        // находим человека по Id
        UserEntity user = userCRUD.findById(message.getUserId()).get();

        if (user==null){    // он существует?
            throw new UserNotFoundException("code: USER_NOT_FOUND");    // выдать ошибку о том что он не найден
        }

        // устанавливаем дату в сообщении
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        Calendar cal = Calendar.getInstance(); // вытащить дату из системы
        message.setSendTime(dateFormat.format(cal.getTime())); // отправить дату в переменую

        if (message.getChatId()!=null){ // это приватная переписка?
            // параметры поиска
            ParticipantsEntity p = new ParticipantsEntity();
            p.setUserId(user.getUserId());
            p.setChatId(message.getChatId());

            if (participantsService.ParticipantsCoincidence(p)==null){    // человек в ней состоит?
                throw new UserNotFoundException("code: CHAT_NOT_FOUND");    // выдать ошибку о том что он не найден
            }
        }

        if(message.getLifetimeSec()>0){ // время жизни есть?
            Long timel = message.getLifetimeSec()+message.getDelaySec(); // вытащить из сообщения
            Long timed = message.getDelaySec();
            //MessageEntity messageTime = message;
            //ApplicationContext ctx = new AnnotationConfigApplicationContext(TimeLifeMessageThreadAppConfig.class); // конфигурация потока
            //timeLife = (TimeLifeMessageThread) ctx.getBean("timeThread"); // обёртка
            //timeLife.set(timel, timed, message); //
            //timeLife.start();
            //new DynamicSchedulingConfig(timeLife, timeDelay, message, messageCRUD);
            //scheduleFixedDelayTask(timeLife, timeDelay, message);
            //System.out.println("//////////////////////////");
            Time time = new Time();
            time.set(timel, timed, message);
            time.start();
            return message;
        }
        else {
            return messageCRUD.save(message); // сохранить
        }
    }

    // ---------------------------------------------------------------------------- получить список всех сообщений из бд, которые для всех
    public ArrayList<MessageModel> getAllMessage(Long chatId) throws Exception{
        ArrayList<MessageModel> arrayListMessage = new ArrayList<>();
        for(MessageEntity m:messageCRUD.findAll()){
            if (m.getChatId()==chatId)
                arrayListMessage.add(MessageModel.toMpdel(m));
        }
        return SortMessageTime.sort(arrayListMessage);
    }

    // ---------------------------------------------------------------------------------------------------- Удалить сообщение
    public Long deleteOneMessage(Long id) throws UserNotFoundException {
        if (messageCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: USER_NOT_FOUND}");
        }
        messageCRUD.deleteById(id);
        return id;
    }

}

@Component
class Time{
    private Long timeLife=0L;
    private Long timeDelay=0L;
    private MessageEntity message=null;

    @Autowired
    private MessageCRUD messageCRUD; // создаём интерфейс для взаимодействия с бд

    public Time() {
    }

    public void set(Long timeLife, Long timeDelay, MessageEntity message) {
        this.timeLife=timeLife;
        this.timeDelay=timeDelay;
        this.message=message;
    }

    @Async
    public void start() {
        try {
            Thread.yield(); // даем возможность выбрать другой поток
            TimeUnit.SECONDS.sleep(timeDelay); // отправляем спать этот поток, не объязательно, но помогает, лишний раз не ждать
            message.setMessageId((messageCRUD.save(message)).getMessageId());
            Thread.yield();
            TimeUnit.SECONDS.sleep(timeLife-timeDelay);
            System.out.println(timeLife-timeDelay);
            messageCRUD.deleteById(message.getMessageId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
