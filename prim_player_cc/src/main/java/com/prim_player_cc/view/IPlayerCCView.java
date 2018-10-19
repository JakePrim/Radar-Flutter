package com.prim_player_cc.view;

import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.status.PlayerStatus;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/25 - 上午10:36
 */
public interface IPlayerCCView {
    void switchDecoder(int playerId);

    void setDataSource(PlayerSource dataSource);

    void setRenderView(int type);

    void setCoverGroup(ICoverGroup coverGroup);

    BusPlayerView getBusPlayerView();

    ICoverGroup getCoverGroup();

    void usedDefaultCoverGroup();

    void destroy();

    @PlayerStatus
    int getState();
}
