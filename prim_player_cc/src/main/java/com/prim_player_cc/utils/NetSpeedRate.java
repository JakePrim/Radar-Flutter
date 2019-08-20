package com.prim_player_cc.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

public class NetSpeedRate {
    private static final String TAG = NetSpeedRate.class.getSimpleName();
    private static long lastTotalRxBytes = 0;
    private static long lastTimeStamp = 0;

    public static String getNetSpeed(int uid) {
        long nowTotalRxBytes = getTotalRxBytes(uid);
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return String.valueOf(speed) + " kb/s";
    }

    //getApplicationInfo().uid
    public static long getTotalRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }

    @SuppressLint("WrongConstant")
    public static long getUidRxBytes(Context context, String packageName) { //获取总的接受字节数，包含Mobile和WiFi等
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return TrafficStats.getUidRxBytes(ai.uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);
    }


}
