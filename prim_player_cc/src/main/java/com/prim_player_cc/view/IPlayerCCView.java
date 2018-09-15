package com.prim_player_cc.view;

import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.state.PlayerState;

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

    void setLooping(boolean loop);

    boolean isLooping();

    void usedDefaultCoverGroup();

    void start();

    void start(long location);

    void pause();

    void resume();

    void reset();

    void stop();

    void destroy();

    void seek(int position);

    @PlayerState
    int getState();

    boolean isPlaying();

    long getCurrentPosition();

    long getDuration();

    int getBufferPercentage();

    void setVolume(float left, float right);

    void setSpeed(float m);
}
