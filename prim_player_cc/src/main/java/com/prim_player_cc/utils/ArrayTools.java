package com.prim_player_cc.utils;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/22 - 3:37 PM
 */
public class ArrayTools {
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }
}
