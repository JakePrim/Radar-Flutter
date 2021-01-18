package com.prim.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期类型转换器
 * Converter<String, Date>: String类型 转换为 Date类型
 */
public class DateConverter implements Converter<String, Date> {
    /**
     * @param s 表单传递过来的请求参数
     * @return
     */
    @Override
    public Date convert(String s) {
        //把日期类型的字符串转换成日期对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
