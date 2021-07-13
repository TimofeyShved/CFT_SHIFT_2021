package com.example.CFT_SHIFT_2021.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class RssLinkService {

    public static String readRSS(String URLAdress){ // наш метод с URL ссылкой
        try {

            URL rssURL = new URL(URLAdress); // запихиваем наш URL
            BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream())); // считываем поток данных в буфер

            String sourceCode = ""; // пустая строчка с будущим текстом

            String line; // переменная строка
            //в котору при помощи цикла, будем считывать по строчки из буфера

            while ((line=in.readLine())!=null){ // пока не пустой
                if (line.contains("<title>")){ // если находит в строчке наше слово

                    int firstPosition = line.indexOf("<title>"); // запоминаем его место
                    String temp = line.substring(firstPosition); // во временую строчку закиним новую, но обрезаную
                    temp = temp.replace("<title>", ""); // избавимься от нашего слова в строчке

                    int lastPosition = temp.indexOf("</title>"); // находим место окончания
                    temp = temp.substring(0, lastPosition); // обрезаем

                    sourceCode += temp+"\n"; // закидываем то что получилось в сточку которую вернём
                }
            }
            in.close(); // закрыть буфер
            return sourceCode; // вернуть значения
        }catch (Exception e){
            System.out.println(e); // ошибка
        }
        return null;
    }

}
