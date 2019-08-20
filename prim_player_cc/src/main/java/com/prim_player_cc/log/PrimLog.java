package com.prim_player_cc.log;

import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志信息
 * @time 2018/7/24 - 上午11:41
 */
public class PrimLog {
    public static Boolean LOG_OPEN = false;

    public static String KEY = " PRIM ";

    public static void d(String tag, String msg) {
        if (LOG_OPEN) {
            Log.d(KEY + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_OPEN) {
            Log.e(KEY + tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_OPEN) {
            Log.w(KEY + tag, msg);
        }
    }

    public static void printStackTrace(String tag) {
        if (!LOG_OPEN) return;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            Log.e(tag, stackTraceElement.toString());
        }
        Log.e(tag, "\n" + "--------------------------" + "\n");
    }

}
