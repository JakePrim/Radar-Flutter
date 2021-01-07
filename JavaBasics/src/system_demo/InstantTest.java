package system_demo;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class InstantTest {
    public static void main(String[] args) {
        //并不是当前系统的默认时区，本初子午线 差8小时
        Instant now = Instant.now();
        System.out.println("now:"+now);//2020-11-18T22:13:17.504Z
        //加上时区，所差的8个小时
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println("offsetDateTime:"+offsetDateTime);//2020-11-19T06:17:20.574+08:00

        //获取当前调用对象距离标准基准时间的毫秒数
        long epochSecond = now.toEpochMilli();
        System.out.println("获取到的毫秒差为:"+epochSecond);
        Instant instant = Instant.ofEpochMilli(epochSecond);
        System.out.println("instant:"+instant);
    }
}
