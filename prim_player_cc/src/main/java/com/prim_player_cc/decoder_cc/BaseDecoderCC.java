package com.prim_player_cc.decoder_cc;

import android.os.Bundle;

import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnCompletionListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorListener;
import com.prim_player_cc.decoder_cc.listener.OnInfoListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayListener;
import com.prim_player_cc.decoder_cc.listener.OnPreparedListener;
import com.prim_player_cc.decoder_cc.listener.OnSeekCompleteListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnVideoSizeChangedListener;
import com.prim_player_cc.state.PlayerState;
import com.prim_player_cc.state.State;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc this is  the basic decoder component that
 * needs to inherit this base component
 * @time 2018/7/24 - 下午3:08
 */
public abstract class BaseDecoderCC implements IDecoder {
    protected @PlayerState
    int mCurrentState = State.STATE_IDEL;

    protected WeakReference<OnPreparedListener> weakPreparedListener;
    protected WeakReference<OnPlayListener> weakPlayingListener;
    protected WeakReference<OnInfoListener> weakInfoListener;
    protected WeakReference<OnErrorListener> weakErrorListener;
    protected WeakReference<OnCompletionListener> weakCompletionListener;
    protected WeakReference<OnBufferingUpdateListener> weakBufferListener;
    protected WeakReference<OnSeekCompleteListener> weakSeekListener;
    protected WeakReference<OnTimerUpdateListener> weakTimerUpdateListener;
    protected WeakReference<OnVideoSizeChangedListener> weakVideoSizeListener;
    protected int mBufferPercentage;

    @Override
    public int getState() {
        return mCurrentState;
    }

    /**
     * 更新当前的状态
     *
     * @param state PlayerState int
     */
    protected void updateState(@PlayerState int state) {
        this.mCurrentState = state;
    }

    /**
     * 获取当前缓存的进度
     *
     * @return int %
     */
    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public void setPreparedListener(OnPreparedListener onPreparedListener) {
        weakPreparedListener = new WeakReference<>(onPreparedListener);
    }

    @Override
    public void setPlayingListener(OnPlayListener onPlayingListener) {
        weakPlayingListener = new WeakReference<>(onPlayingListener);
    }

    @Override
    public void setOnInfoListener(OnInfoListener onInfoListener) {
        weakInfoListener = new WeakReference<>(onInfoListener);
    }

    @Override
    public void setOnErrorListener(OnErrorListener onErrorListener) {
        weakErrorListener = new WeakReference<>(onErrorListener);
    }

    @Override
    public void setCompletionListener(OnCompletionListener onCompletionListener) {
        weakCompletionListener = new WeakReference<>(onCompletionListener);
    }

    @Override
    public void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        weakBufferListener = new WeakReference<>(onBufferingUpdateListener);
    }


    @Override
    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        weakSeekListener = new WeakReference<>(onSeekCompleteListener);
    }

    @Override
    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        weakTimerUpdateListener = new WeakReference<>(onTimerUpdateListener);
    }

    @Override
    public void setOnVideoSizeChangeListener(OnVideoSizeChangedListener onVideoSizeChangeListener) {
        weakVideoSizeListener = new WeakReference<>(onVideoSizeChangeListener);
    }

    /**
     * 触发准备监听事件
     *
     * @param bundle 可自定义传递参数
     * @param i      what
     */
    protected void triggerPrepared(Bundle bundle, int i) {
        if (weakPreparedListener != null && weakPreparedListener.get() != null) {
            weakPreparedListener.get().onPrepared(bundle, i);
        }
    }

    /**
     * 触发其他播放事件
     */
    protected void triggerPlaying(int eventCode, Bundle bundle) {
        if (weakPlayingListener != null && weakPlayingListener.get() != null) {
            weakPlayingListener.get().onPlaying(eventCode, bundle);
        }
    }

    /**
     * 触发播放错误事件
     *
     * @param bundle    自定义传递的参数
     * @param errorCode 错误码
     */
    protected void triggerError(Bundle bundle, int errorCode) {
        if (weakErrorListener != null && weakErrorListener.get() != null) {
            weakErrorListener.get().onError(bundle, errorCode);
        }
    }

    /**
     * 触发播放信息事件
     *
     * @param bundle 自定义传递的参数
     * @param var1   what
     * @param var2
     */
    protected void triggerInfo(Bundle bundle, int var1, int var2) {
        if (weakInfoListener != null && weakInfoListener.get() != null) {
            weakInfoListener.get().onInfo(bundle, var1, var2);
        }
    }

    /**
     * 触发播放完成事件
     *
     * @param bundle
     */
    protected void triggerCompletion(Bundle bundle) {
        if (weakCompletionListener != null && weakCompletionListener.get() != null) {
            weakCompletionListener.get().onCompletion(bundle);
        }
    }

    /**
     * 触发缓存事件
     *
     * @param bundle
     * @param buffer
     */
    protected void triggerBufferUpdate(Bundle bundle, int buffer) {
        this.mBufferPercentage = buffer;
        if (weakBufferListener != null && weakBufferListener.get() != null) {
            weakBufferListener.get().onBufferingUpdate(bundle, buffer);
        }
    }

    /**
     * 触发seek完成事件
     *
     * @param o MediaPlayer
     */
    protected void triggrtSeekCompletion(Object o) {
        if (weakSeekListener != null && weakSeekListener.get() != null) {
            weakSeekListener.get().onSeekComplete(o);
        }
    }

    /**
     * 触发视频大小改变事件
     *
     * @param o      MediaPlayer
     * @param bundle 自定义参数
     * @param width  宽
     * @param height 高
     */
    protected void triggerVideoSizeChanged(Object o, Bundle bundle, int width, int height) {
        if (weakVideoSizeListener != null && weakVideoSizeListener.get() != null) {
            weakVideoSizeListener.get().onVideoSizeChanged(o, bundle, width, height);
        }
    }

    /**
     * 触发进度更新事件
     * @param current 当前进度
     * @param duration 总进度
     * @param bufferPercentage 缓存进度
     */
    protected void triggerTimerUpdate(long current, long duration, int bufferPercentage) {
        if (weakTimerUpdateListener != null && weakTimerUpdateListener.get() != null) {
            weakTimerUpdateListener.get().onUpdate(current, duration, bufferPercentage);
        }
    }

    @Override
    public void destroy() {
        resetListener();

        this.mCurrentState = State.STATE_IDEL;
    }

    private void resetListener() {
        if (weakBufferListener != null && weakBufferListener.get() != null) {
            weakBufferListener.clear();
            weakBufferListener = null;
        }

        if (weakCompletionListener != null && weakCompletionListener.get() != null) {
            weakCompletionListener.clear();
            weakCompletionListener = null;
        }

        if (weakErrorListener != null && weakErrorListener.get() != null) {
            weakErrorListener.clear();
            weakErrorListener = null;
        }

        if (weakInfoListener != null && weakInfoListener.get() != null) {
            weakInfoListener.clear();
            weakInfoListener = null;
        }

        if (weakPlayingListener != null && weakPlayingListener.get() != null) {
            weakPlayingListener.clear();
            weakPlayingListener = null;
        }

        if (weakPreparedListener != null && weakPreparedListener.get() != null) {
            weakPreparedListener.clear();
            weakPreparedListener = null;
        }

        if (weakSeekListener != null && weakSeekListener.get() != null) {
            weakSeekListener.clear();
            weakSeekListener = null;
        }

        if (weakVideoSizeListener != null && weakVideoSizeListener.get() != null) {
            weakVideoSizeListener.clear();
            weakVideoSizeListener = null;
        }

        if (weakTimerUpdateListener != null && weakTimerUpdateListener.get() != null) {
            weakTimerUpdateListener.clear();
            weakTimerUpdateListener = null;
        }
    }
}
