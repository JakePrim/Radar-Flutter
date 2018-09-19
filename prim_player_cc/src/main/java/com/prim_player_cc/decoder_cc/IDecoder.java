package com.prim_player_cc.decoder_cc;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.status.PlayerStatus;

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

    @PlayerStatus
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

    //---------------------------------- 解码器的播放事件监听 ---------------------------------//
    /**
     * 播放事件监听 包括了 准备事件 信息 完成 等其他事件
     * 自定义事件码
     * @param onPlayingListener
     */
    void setPlayerEventListener(OnPlayerEventListener onPlayingListener);

    /**
     * 播放错误事件监听
     * 自定义错误事件码
     * @param onErrorListener
     */
    void setOnErrorEventListener(OnErrorEventListener onErrorListener);

    /**
     * 视频缓存监听
     * @param onBufferingUpdateListener
     */
    void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    /**
     * 进度更新监听
     * @param onTimerUpdateListener
     */
    void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener);

}
