package com.prim_player_cc.utils;

import java.util.Arrays;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/23 - 6:53 PM
 */
public class ObjectEquals {
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
