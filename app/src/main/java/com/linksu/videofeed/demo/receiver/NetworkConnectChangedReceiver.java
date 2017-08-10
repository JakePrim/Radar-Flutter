package com.linksu.videofeed.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：8/10 0010
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (listener != null) {
                        Log.e("linksu",
                                "onReceive(NetworkConnectChangedReceiver.java:34) 切换到数据流量");
                        listener.dataNetwork(true);
                    }
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (listener != null) {
                        Log.e("linksu",
                                "onReceive(NetworkConnectChangedReceiver.java:40) 切换到WiFi");
                        listener.wifiNetwork(true);
                    }
                }
            } else {
                if (listener != null) {
                    Log.e("linksu",
                            "onReceive(NetworkConnectChangedReceiver.java:47) 无网络连接");
                    listener.notNetWork();
                }
            }
        } else {
            if (listener != null) {
                Log.e("linksu",
                        "onReceive(NetworkConnectChangedReceiver.java:54) 无网络连接");
                listener.notNetWork();
            }
        }
    }

    private ConnectChangedListener listener;

    public void setConnectChangeListener(ConnectChangedListener listener) {
        this.listener = listener;
    }

    public interface ConnectChangedListener {
        void wifiNetwork(boolean flag);

        void dataNetwork(boolean flag);

        void notNetWork();

    }
}
