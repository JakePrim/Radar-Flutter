package com.jakeprim.utils;

import java.util.UUID;

/**
 * UUID是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的。
 *
 * UUID由以下几部分的组合：
 *  1.当前日期和时间，UUID的第一个部分与时间有关，如果你在生成一个UUID之后，过几秒又生成一个UUID，则第一个部分不同，其余相同。
 *  2.时钟序列。
 *  3.全局唯一的IEEE机器识别号，如果有网卡，从网卡MAC地址获得，没有网卡以其他方式获得。
 *
 */
public class UUIDUtils {

    //获取唯一ID的 方法
    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }

}
