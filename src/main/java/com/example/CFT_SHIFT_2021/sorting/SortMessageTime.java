package com.example.CFT_SHIFT_2021.sorting;

import com.example.CFT_SHIFT_2021.model.MessageModel;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SortMessageTime {

    public static ArrayList<MessageModel> sort(ArrayList<MessageModel> data) {
        int dataLength = data.size();
        MessageModel swap;
        boolean sorted;

        for (int i = 0; i < dataLength; i++) {

            sorted = true;

            for (int a = 1; a < (dataLength - i); a++) {

                LocalDateTime localDateTime_1 = LocalDateTime.parse((data.get(a - 1)).getSendTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
                long millis_1 = localDateTime_1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                LocalDateTime localDateTime_2 = LocalDateTime.parse((data.get(a)).getSendTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
                long millis_2 = localDateTime_2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                if (millis_1 < millis_2) {
                    swap = data.get(a - 1);
                    data.set(a - 1, data.get(a));
                    data.set(a, swap);
                    sorted = false;
                }

            }

            // если отсортировано, выходим, пропуская ненужный цикл.
            if (sorted) break;
        }
        return data;
    }
}
