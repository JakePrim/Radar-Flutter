package com.prim_player_cc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;

import java.util.Locale;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/24 - 3:53 PM
 */
public class Tools {

    public static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    public static int generateTimeSeconds(long position) {
        int seconds = (int) (position / 1000);
        return seconds;
    }

    public static boolean isFullScreen(Activity activity) {
        int requestedOrientation = Tools.scanForActivity(activity).getRequestedOrientation();
        return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) return null;
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    public static void setRequestedOrientation(Context context, int orientation) {
        try {
            if (Tools.getAppCompActivity(context) != null) {
                Tools.getAppCompActivity(context).setRequestedOrientation(
                        orientation);
            } else {
                Tools.scanForActivity(context).setRequestedOrientation(
                        orientation);
            }
        } catch (Exception e) {
            Log.e("Tools", "set video orientation" + e.getMessage());
        }
    }

}
