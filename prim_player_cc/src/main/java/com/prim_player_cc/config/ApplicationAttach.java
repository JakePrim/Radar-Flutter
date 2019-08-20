package com.prim_player_cc.config;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/24 - 下午4:47
 */
public class ApplicationAttach {
    private static WeakReference<Context> mContext;

    public static void attach(Application application) {
        if (application == null) {
            throw new RuntimeException("player need use context,must set PrimPlayerConfig.configBuild.build(application)");
        }
        mContext = new WeakReference<>(application.getApplicationContext());
    }

    public static Context getApplicationContext() {
        if (mContext == null) {
            throw new RuntimeException("player need use context,must set PrimPlayerConfig.configBuild.build(application)");
        }
        return mContext.get();
    }
}
