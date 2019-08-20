package com.prim_player_cc.utils;

import android.net.TrafficStats;

import java.text.NumberFormat;

public class NetSpeed {
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public String getNetSpeed(int uid) {
        long nowTotalRxBytes = getTotalRxBytes(uid);
        long nowTimesStemp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimesStemp - lastTimeStamp));
        lastTimeStamp = nowTimesStemp;
        lastTotalRxBytes = nowTotalRxBytes;
        Double d = (double) (speed / 1024);
        return String.valueOf(d) + "M/s";
    }


    public long getTotalRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }


    private long preRxBytes, preSeBytes;
    private long rxBytes, seBytes;

    public String calculateNetDownSpeed() {
        rxBytes = TrafficStats.getTotalRxBytes();
        seBytes = TrafficStats.getTotalTxBytes() - rxBytes;
        double downloadSpeed = (rxBytes - preRxBytes) / 2;
        preRxBytes = rxBytes;
        preSeBytes = seBytes;
        //根据范围决定显示单位
        String downSpeed = null;
        NumberFormat df = java.text.NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(2);
        if (downloadSpeed > 1024 * 1024) {
            downloadSpeed /= (1024 * 1024);
            downSpeed = df.format(downloadSpeed) + "MB/s";
        } else if (downloadSpeed > 1024) {
            downloadSpeed /= (1024);
            downSpeed = df.format(downloadSpeed) + "KB/s";
        } else {
            downSpeed = df.format(downloadSpeed) + "B/s";
        }
        return downSpeed;
    }

    public String calculateNetUpSpeed() {
        rxBytes = TrafficStats.getTotalRxBytes();
        seBytes = TrafficStats.getTotalTxBytes() - rxBytes;
        double uploadSpeed = (seBytes - preSeBytes) / 2;
        preRxBytes = rxBytes;
        preSeBytes = seBytes;
        //根据范围决定显示单位
        String upSpeed = null;
        NumberFormat df = java.text.NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(2);
        if (uploadSpeed > 1024 * 1024) {
            uploadSpeed /= (1024 * 1024);
            upSpeed = df.format(uploadSpeed) + "MB/s";
        } else if (uploadSpeed > 1024) {
            uploadSpeed /= (1024);
            upSpeed = df.format(uploadSpeed) + "KB/s";
        } else {
            upSpeed = df.format(uploadSpeed) + "B/s";
        }
        return upSpeed;
    }
}
