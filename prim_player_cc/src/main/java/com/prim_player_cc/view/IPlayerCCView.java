package com.prim_player_cc.view;

import android.graphics.Bitmap;
import android.view.View;

import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.status.PlayerStatus;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/25 - 上午10:36
 */
public interface IPlayerCCView {
    boolean dynamicChangedDecoder(int playerId);

    void setRenderView(int type);

    void setCoverGroup(ICoverGroup coverGroup);

    BusPlayerView getBusPlayerView();

    Bitmap getShortcut();

    ICoverGroup getCoverGroup();

    void usedDefaultCoverGroup();

    void destroy();

    void autoPlayNext(boolean autoNext);

    //设置解码器配置
    void setAVOptions(AVOptions avOptions);
}
