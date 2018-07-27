package com.prim_player_cc;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.data.PlayerSource;
import com.prim_player_cc.player_cc.OnBufferingUpdateListener;
import com.prim_player_cc.player_cc.OnCompletionListener;
import com.prim_player_cc.player_cc.OnErrorListener;
import com.prim_player_cc.player_cc.OnInfoListener;
import com.prim_player_cc.player_cc.OnPlayingListener;
import com.prim_player_cc.player_cc.OnPreparedListener;
import com.prim_player_cc.state.PlayerState;

/**
 * @author prim
 * @version 1.0.0
 * @desc player interface
 * @time 2018/7/24 - 上午11:27
 */
public interface IPlayer {

    void start();

    void start(long location);

    void pause();

    void resume();

    void reset();

    void stop();

    void destroy();

    void seek(int position);

    void setLooping(boolean loop);

    boolean isLooping();

    @PlayerState
    int getState();

    boolean isPlaying();

    long getCurrentPosition();

    long getDuration();

    int getBufferPercentage();

    void setVolume(float left, float right);

    void setSpeed(float m);

    void setDataSource(PlayerSource source);

    //以下为播放器的监听事件
    void setPreparedListener(OnPreparedListener onPreparedListener);

    void setPlayingListener(OnPlayingListener onPlayingListener);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setCompletionListener(OnCompletionListener onCompletionListener);

    void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    //当使用播放器为MediaPlayer 时用以下两个方法
    void setSurface(Surface surface);

    void setDisplay(SurfaceHolder surfaceHolder);

    void bindVideoView(View videoView);

}
