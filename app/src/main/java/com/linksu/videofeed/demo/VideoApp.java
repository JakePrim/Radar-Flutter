package com.linksu.videofeed.demo;

import android.app.Application;
import android.content.Context;

import com.linksu.videofeed.demo.test.ImageLoader;
import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.decoder_cc.DecoderWrapper;
import com.prim_player_cc.decoder_cc.DefaultDecoder;

import ndk.config.com.ijkplayer.IjkPlayer;

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
        //视频播放器组件配置初始化
        PrimPlayerCC.getInstance().init()
                .setLogEnable(true)//log
                .addDecoder(new DecoderWrapper(11, DefaultDecoder.class, "default player this id:11"))
                .addDecoder(new DecoderWrapper(12, IjkPlayer.class, "ijk player this id:12"))
                .setUseDecoderId(12)//设置使用的解码器
                .setImageLoader(new ImageLoader())//加载图片
                .build(this);//构建多媒体框架
    }

    public static Context getAppContext() {
        return mInstantce.getApplicationContext();
    }
}
