package com.prim_player_cc.decoder_cc;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.decoder_cc.listener.OnSeekCompleteListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnVideoSizeChangedListener;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnCompletionListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorListener;
import com.prim_player_cc.decoder_cc.listener.OnInfoListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayListener;
import com.prim_player_cc.decoder_cc.listener.OnPreparedListener;
import com.prim_player_cc.state.PlayerState;

/**
 * @author prim
 * @version 1.0.0
 * @desc player interface
 * @time 2018/7/24 - 上午11:27
 */
public interface IDecoder {

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

    //当使用播放器为MediaPlayer 时用以下两个方法
    void setSurface(Surface surface);

    void setDisplay(SurfaceHolder surfaceHolder);

    void bindVideoView(View videoView);

    View getRenderView();

    int getVideoWidth();

    int getVideoHeight();

    //以下为播放器的监听事件
    void setPreparedListener(OnPreparedListener onPreparedListener);

    void setPlayingListener(OnPlayListener onPlayingListener);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setCompletionListener(OnCompletionListener onCompletionListener);

    void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener);

    void setOnVideoSizeChangeListener(OnVideoSizeChangedListener onVideoSizeChangeListener);
}
