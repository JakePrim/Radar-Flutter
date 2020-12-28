package com.homework.homeword01.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 获取日期对象 格式化后的字符串
     * @return
     */
    public static String getDateFormart(){

        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String format = sdf.format(date);

        return format;
    }
}
