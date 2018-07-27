package com.prim_player_cc.config;

import android.app.Application;
import android.content.Context;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/24 - 下午4:47
 */
public class ApplicationAttach {
    public static Context mContext;

    public static void attach(Application application) {
        if (application == null) {
            throw new RuntimeException("player need use context,must set PlayerCC_Config.configBuild.init(application)");
        }
        mContext = application.getApplicationContext();
    }

    public static Context getApplicationContext() {
        if (mContext == null) {
            throw new RuntimeException("player need use context,must set PlayerCC_Config.configBuild.init(application)");
        }
        return mContext;
    }
}
