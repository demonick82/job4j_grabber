package ru.job4j.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    private final String today = "сегодня";
    private final String yesterday = "вчера";
    private final Map<String, Integer> month = createMap();

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime localDateTime;
        String[] dateTime = parse.split(",");
        String[] time = dateTime[1].split(":");
        int hour = Integer.parseInt(time[0].trim());
        int minutes = Integer.parseInt(time[1].trim());
        LocalTime localTime = LocalTime.of(hour, minutes);
        if (dateTime[0].equals(today)) {
            localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
        } else if (dateTime[0].equals(yesterday)) {
            localDateTime = LocalDateTime.of(LocalDate.now().minusDays(1), localTime);
        } else {
            String[] date = dateTime[0].split("\\s");
            int day = Integer.parseInt(date[0]);
            int mouth = month.get(date[1]);
            int year = Integer.parseInt(String.format("20%s", date[2]));
            localDateTime = LocalDateTime.of(LocalDate.of(year, mouth, day), localTime);
        }
        return localDateTime;
    }


    private Map<String, Integer> createMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("янв", 1);
        map.put("фев", 2);
        map.put("мар", 3);
        map.put("апр", 4);
        map.put("май", 4);
        map.put("июн", 6);
        map.put("июл", 7);
        map.put("авг", 8);
        map.put("сен", 9);
        map.put("окт", 10);
        map.put("ноя", 11);
        map.put("дек", 12);
        return map;
    }
}
