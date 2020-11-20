package com.prim.springmvc.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义全局日期格式转换器
 *
 * @author prim
 */
public class DateConverter implements Converter<String, Date> {
    public Date convert(String s) {
        //s 表示输入的时间字符串
        //把字符串转换成日期类
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            //不符合格式的日期字符串处理
            return null;
        }
    }
}
