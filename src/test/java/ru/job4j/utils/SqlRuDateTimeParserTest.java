package ru.job4j.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class SqlRuDateTimeParserTest {

    @Test
    public void dateTimeParseNow() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        String date = "сегодня, 22:14";
        LocalDate now = LocalDate.now();
        LocalTime localtime = LocalTime.of(22, 14);
        LocalDateTime inspect = LocalDateTime.of(now,localtime);
        assertThat(sqlRuDateTimeParser.parse(date),is(inspect));
    }

    @Test
    public void dateTimeParseYesterday() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        String date = "вчера, 22:14";
        LocalDate now = LocalDate.now().minusDays(1);
        LocalTime localtime = LocalTime.of(22, 14);
        LocalDateTime inspect = LocalDateTime.of(now,localtime);
        assertThat(sqlRuDateTimeParser.parse(date),is(inspect));
    }

    @Test
    public void dateTimeParseDate() {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        String date = "21 май 21, 08:06";
        LocalDate now = LocalDate.of(2021,5,21);
        LocalTime localtime = LocalTime.of(8, 6);
        LocalDateTime inspect = LocalDateTime.of(now,localtime);
        assertThat(sqlRuDateTimeParser.parse(date),is(inspect));
    }
}