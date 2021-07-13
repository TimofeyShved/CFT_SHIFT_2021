package com.example.CFT_SHIFT_2021.thread;

import com.example.CFT_SHIFT_2021.entity.ChatEntity;
import com.example.CFT_SHIFT_2021.repository.ChatCRUD;
import com.example.CFT_SHIFT_2021.repository.MessageCRUD;
import com.example.CFT_SHIFT_2021.service.MessageService;
import com.example.CFT_SHIFT_2021.service.RssLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


// ----------------------------------------------------------------------------------------------------  rsslink
@Component
public class RssLinkChatThread {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatCRUD chatCRUD;

    @Autowired
    private RssLinkService rssLinkService;


    @Scheduled(cron = "*/40 * * * * *") // Формат:  секунда, минута, час, день, месяц, день недели
    public void runRssLink() throws Exception {
        for (ChatEntity chat:chatCRUD.findAll()){
            if (chat.getRssLink()!=null){
                System.out.println(rssLinkService.readRSS(chat.getRssLink()));
            }
        }
    }

}




