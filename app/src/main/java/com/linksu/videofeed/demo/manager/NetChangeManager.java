package com.linksu.videofeed.demo.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.linksu.videofeed.demo.VideoApp;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：8/10 0010
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NetChangeManager {
    private static NetChangeManager netChangeManager;
    private Context context;

    public NetChangeManager() {
    }

    public static NetChangeManager getInstance() {
        if (netChangeManager == null) {
            synchronized (NetChangeManager.class) {
                if (netChangeManager == null) {
                    netChangeManager = new NetChangeManager();
                }
            }
        }
        return netChangeManager;
    }

    /**
     * 获取网络类型
     *
     * @return 返回值 -1：没有网络  1：WIFI网络 2：wap网络3：net网络
     */
    public int getNetType() {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) VideoApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        Log.e("networkInfo", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo() == null) {
                netType = 2;
            } else {
                if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                    netType = 3;
                } else {
                    netType = 2;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

    /**
     * 当前是否有网络
     *
     * @return true - 有网  false 无网
     */
    public boolean hasNet() {
        return getNetType() != -1;
    }
}
