package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.MessageEntity;
import org.hibernate.engine.spi.ManagedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class RssLinkService {

    public static ArrayList<MessageEntity> readRSS(String URLAdress){ // наш метод с URL ссылкой
        try {
            ArrayList<MessageEntity> messageEntityArrayList = new ArrayList<>();

            URL rssURL = new URL(URLAdress); // запихиваем наш URL
            BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream())); // считываем поток данных в буфер

            String sourceCode = ""; // пустая строчка с будущим текстом
            MessageEntity message = new MessageEntity();

            String line; // переменная строка
            //в котору при помощи цикла, будем считывать по строчки из буфера

            while ((line=in.readLine())!=null){ // пока не пустой
                if (line.contains("<item>")){
                    message = new MessageEntity();
                }

                if (line.contains("<title>")){ // если находит в строчке наше слово

                    int firstPosition = line.indexOf("<title>"); // запоминаем его место
                    String temp = line.substring(firstPosition); // во временую строчку закиним новую, но обрезаную
                    temp = temp.replace("<title>", ""); // избавимься от нашего слова в строчке

                    int lastPosition = temp.indexOf("</title>"); // находим место окончания
                    temp = temp.substring(0, lastPosition); // обрезаем
                    //sourceCode += temp+"\n"; // закидываем то что получилось в сточку которую вернём
                    message.setText(temp);
                }

                if (line.contains("<link>")) { // если находит в строчке наше слово
                    int firstPosition = line.indexOf("<link>"); // запоминаем его место
                    String temp = line.substring(firstPosition); // во временую строчку закиним новую, но обрезаную
                    temp = temp.replace("<link>", ""); // избавимься от нашего слова в строчке

                    int lastPosition = temp.indexOf("</link>"); // находим место окончания
                    temp = temp.substring(0, lastPosition); // обрезаем
                    message.setText(message.getText()+"["+temp+"]");
                }

                if (line.contains("<![CDATA[")) { // если находит в строчке наше слово
                    int firstPosition = line.indexOf("<![CDATA["); // запоминаем его место
                    String temp = line.substring(firstPosition); // во временую строчку закиним новую, но обрезаную
                    temp = temp.replace("<![CDATA[", ""); // избавимься от нашего слова в строчке

                    int lastPosition = temp.indexOf("]]>"); // находим место окончания
                    temp = temp.substring(0, lastPosition); // обрезаем
                    message.setText(message.getText()+temp);
                }

                if (line.contains("<pubDate>")) { // если находит в строчке наше слово
                    int firstPosition = line.indexOf("<pubDate>"); // запоминаем его место
                    String temp = line.substring(firstPosition); // во временую строчку закиним новую, но обрезаную
                    temp = temp.replace("<pubDate>", ""); // избавимься от нашего слова в строчке

                    int lastPosition = temp.indexOf("</pubDate>"); // находим место окончания
                    temp = temp.substring(0, lastPosition); // обрезаем

                    DateFormat linkDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzzzz", Locale.ENGLISH);
                    java.util.Date date = linkDateFormat.parse(temp);
                    java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

                    DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
                    message.setSendTime(myDateFormat.format(sqlStartDate)); // отправить дату в переменую
                }

                if (line.contains("</item>")){
                    // ------------------------------------------------ устанавливаем дату в сообщении
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
                    Calendar cal = Calendar.getInstance(); // вытащить дату из системы
                    cal.add(Calendar.HOUR, -1);
                    java.util.Date sqlStartDate = cal.getTime(); // отправить дату в переменую

                    if (sqlStartDate.before(dateFormat.parse(message.getSendTime()))){
                        messageEntityArrayList.add(message);
                    }
                }
            }
            in.close(); // закрыть буфер
            return messageEntityArrayList; // вернуть значения
        }catch (Exception e){
            System.out.println(e); // ошибка
        }
        return null;
    }

}
