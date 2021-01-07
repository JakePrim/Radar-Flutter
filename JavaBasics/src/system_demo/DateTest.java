package system_demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        System.out.println("当前系统的时间:" + date);//转回日期格式的结果为:Wed Nov 18 00:00:00 CST 2020
        Date date1 = new Date(1000);
        System.out.println("date1:" + date1);//1970 1 1 1
        System.out.println(date1.getTime());//1000
        date1.setTime(2000);
        System.out.println(date1.getTime());//2000

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        System.out.println("转换后的日期为:" + format);//转换后的日期为:2020-11-18
        Date parse = simpleDateFormat.parse(format);
        System.out.println("转回日期格式的结果为:" + parse);//转回日期格式的结果为:Wed Nov 18 00:00:00 CST 2020

        //1. 获取Calendar类型的引用
        Calendar instance = Calendar.getInstance();

        //设置指定的年月日
        instance.set(2008, Calendar.SEPTEMBER-1, 8, 8, 8, 8);
        //转换为Date类型的对象
        Date time = instance.getTime();
        String format1 = simpleDateFormat.format(time);
        System.out.println(format1);//2008-09-08 08:08:08 差了一个月 因为0代表1月份要减一



    }
}
