package com.linksu.videofeed.demo;

import android.app.Application;
import android.content.Context;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：8/10 0010
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class VideoApp extends Application {

    private static VideoApp mInstantce;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstantce = this;
    }

    public static Context getAppContext() {
        return mInstantce.getApplicationContext();
    }
}
