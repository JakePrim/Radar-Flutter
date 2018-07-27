package com.linksu.videofeed.demo;

import android.app.Application;
import android.content.Context;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.data.PlayerWrapper;
import com.prim_player_cc.player_cc.DefaultPlayer;

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

        //视频播放器组件配置
        PlayerCC_Config.configBuild()
                .setLogEnable(true)
                .addPlayer(new PlayerWrapper(11, DefaultPlayer.class, "default player"))
                .setUsePlayerId(11)
                .init(this);
    }

    public static Context getAppContext() {
        return mInstantce.getApplicationContext();
    }
}
