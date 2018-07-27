package com.prim_player_cc.log;

import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/24 - 上午11:41
 */
public class PrimLog {
    public static Boolean LOG_OPEN = false;

    public static void d(String tag, String msg) {
        if (LOG_OPEN) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_OPEN) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_OPEN) {
            Log.w(tag, msg);
        }
    }

}
