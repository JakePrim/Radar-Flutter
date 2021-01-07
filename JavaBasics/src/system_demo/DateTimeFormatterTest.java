package system_demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(now);
        System.out.println("format:"+format);//2020-11-19 06:23:50
        TemporalAccessor parse = dateTimeFormatter.parse(format);
        System.out.println("parse:"+parse);//{},ISO resolved to 2020-11-19T06:23:50
    }
}
