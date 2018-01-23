package com.primplayer.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PlayerState;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：1/23 0023
 * 描    述：
 * 修订历史：2.0.0 version
 * ================================================
 */
interface IPrimPlayerView {

    void initPlayer(Context context);


    void setPlayerOptions(AVOptions options);


    void setSeekTo(long position);

    void recycleVideoView();

    /**
     * @param isWifi
     *         true WiFi ；false netData
     */
    void setWifiState(boolean isWifi);


    void addController();


    void removeController();


    void setDataSource(String path);


    void startPlayer();


    void currentPlayer();


    void stopPlayer();


    void completePlayer();


    void replayed();


    void pausePlayer();


    boolean isPlayerComplete();


    boolean isPlayerPause();


    boolean isPlayer();

    PlayerState getPlayerState();


    void setPlayerVolume(float var1, float var2);


    void onConfigurationChanged(Configuration newConfig);


    void setDisplayOrientation(int rotation);
}
