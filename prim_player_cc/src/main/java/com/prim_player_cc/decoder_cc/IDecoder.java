package com.prim_player_cc.decoder_cc;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.decoder_cc.event_code.ErrorCode;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.PlayerSource;
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

    PlayerSource getDataSource();

    void setAVOptions(AVOptions avOptions);

    void updateState(@PlayerStatus int state);

    void prepareAsync() throws IllegalStateException;

    void start() throws IllegalStateException;

    void start(long location) throws IllegalStateException;

    void pause() throws IllegalStateException;

    void stop() throws IllegalStateException;

    void resume();

    void reset();

    void release();

    void destroy();

    void seek(long msec) throws IllegalStateException;

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

    void setSurface(Surface surface);

    void setDisplay(SurfaceHolder surfaceHolder);

    void bindVideoView(View videoView);

    IRenderView getRenderView();

    int getVideoWidth();

    int getVideoHeight();

    int getVideoSarNum();

    int getVideoSarDen();

    @Deprecated
    void setWakeMode(Context context, int mode);

    @SuppressWarnings("EmptyMethod")
    @Deprecated
    void setLogEnabled(boolean enabled);

    //---------------------------------- 解码器的播放事件监听 ---------------------------------//

    /**
     * 播放事件监听 包括了 准备事件 信息 完成 视频大小改变 跳转完成等其他事件
     * 自定义事件码{@link PlayerEventCode}
     *
     * @param onPlayingListener OnPlayerEventListener
     */
    void setPlayerEventListener(OnPlayerEventListener onPlayingListener);

    /**
     * 播放错误事件监听
     * 自定义错误事件码{@link ErrorCode}
     *
     * @param onErrorListener OnErrorEventListener
     */
    void setOnErrorEventListener(OnErrorEventListener onErrorListener);

    /**
     * 视频缓存监听
     *
     * @param onBufferingUpdateListener OnBufferingUpdateListener
     */
    void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);
}
