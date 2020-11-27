package system_demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeTest {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println("获取到的当前日期:" + now);
        LocalTime now1 = LocalTime.now();
        System.out.println("获取到的当前时间:" + now1);
        LocalDateTime now2 = LocalDateTime.now();
        System.out.println("获取到的当前日期时间是:" + now2);

        //设置指定年月日时分秒
        LocalDateTime time = LocalDateTime.of(2008, 8, 8, 8, 8, 8);
        System.out.println("指定的日期时间：" + time);//2008-08-08T08:08:08
        //调用对象本身的数据内容不会改变，返回值相当于创建了一个新的对象，由此证明不可变性
        LocalDateTime localDateTime = time.withYear(2012);
        System.out.println("localDateTime:" + localDateTime);//2012-08-08T08:08:08
        System.out.println("time:" + time);//2008-08-08T08:08:08
        LocalDateTime localDateTime1 = localDateTime.withMonth(12);
        System.out.println("localDateTime1:" + localDateTime1);//2012-12-08T08:08:08

        LocalDateTime localDateTime2 = localDateTime1.plusDays(2);//日增加两天
        System.out.println("localDateTime2:" + localDateTime2);//2012-12-10T08:08:08
        System.out.println("localDateTime1:" + localDateTime1);//2012-12-08T08:08:08

        LocalDateTime localDateTime3 = localDateTime2.plusHours(3);
        System.out.println("localDateTime3:" + localDateTime3);//2012-12-10T11:08:08
    }
}
