package com.prim_player_cc.decoder_cc;

import android.os.Bundle;

import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.status.PlayerStatus;
import com.prim_player_cc.status.Status;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc this is  the basic decoder component that
 * needs to inherit this base component
 * @time 2018/7/24 - 下午3:08
 */
public abstract class BaseDecoderCC implements IDecoder {
    protected @PlayerStatus
    int mCurrentState = Status.STATE_IDEL;

    protected WeakReference<OnPlayerEventListener> weakPlayingListener;
    protected WeakReference<OnErrorEventListener> weakErrorListener;
    protected WeakReference<OnBufferingUpdateListener> weakBufferListener;
    protected WeakReference<OnTimerUpdateListener> weakTimerUpdateListener;
    protected int mBufferPercentage;

    @Override
    public int getState() {
        return mCurrentState;
    }

    /**
     * 更新当前的状态
     *
     * @param state PlayerStatus int
     */
    protected void updateState(@PlayerStatus int state) {
        this.mCurrentState = state;
        Bundle bundle = new Bundle();
        bundle.putInt(EventCodeKey.PLAYER_UPDATE_STATUS, state);
        triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_STATUS_CHANGE, bundle);
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
    public void setPlayerEventListener(OnPlayerEventListener onPlayingListener) {
        weakPlayingListener = new WeakReference<>(onPlayingListener);
    }


    @Override
    public void setOnErrorEventListener(OnErrorEventListener onErrorListener) {
        weakErrorListener = new WeakReference<>(onErrorListener);
    }


    @Override
    public void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        weakBufferListener = new WeakReference<>(onBufferingUpdateListener);
    }


    @Override
    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        weakTimerUpdateListener = new WeakReference<>(onTimerUpdateListener);
    }


    /**
     * 触发播放事件
     */
    protected void triggerPlayerEvent(int eventCode, Bundle bundle) {
        if (weakPlayingListener != null && weakPlayingListener.get() != null) {
            weakPlayingListener.get().onPlayerEvent(eventCode, bundle);
        }
    }

    /**
     * 触发播放错误事件
     *
     * @param bundle    自定义传递的参数
     * @param eventCode 事件码
     */
    protected void triggerErrorEvent(Bundle bundle, int eventCode) {
        if (weakErrorListener != null && weakErrorListener.get() != null) {
            weakErrorListener.get().onError(bundle, eventCode);
        }
    }

    /**
     * 触发缓存事件
     *
     * @param bundle 传递的参数
     * @param buffer 缓存进度
     */
    protected void triggerBufferUpdate(Bundle bundle, int buffer) {
        this.mBufferPercentage = buffer;
        if (weakBufferListener != null && weakBufferListener.get() != null) {
            weakBufferListener.get().onBufferingUpdate(bundle, buffer);
        }
    }


    /**
     * 触发进度更新事件
     *
     * @param current          当前进度
     * @param duration         总进度
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

        this.mCurrentState = Status.STATE_IDEL;
    }

    private void resetListener() {
        if (weakBufferListener != null && weakBufferListener.get() != null) {
            weakBufferListener.clear();
            weakBufferListener = null;
        }

        if (weakErrorListener != null && weakErrorListener.get() != null) {
            weakErrorListener.clear();
            weakErrorListener = null;
        }

        if (weakPlayingListener != null && weakPlayingListener.get() != null) {
            weakPlayingListener.clear();
            weakPlayingListener = null;
        }

        if (weakTimerUpdateListener != null && weakTimerUpdateListener.get() != null) {
            weakTimerUpdateListener.clear();
            weakTimerUpdateListener = null;
        }
    }
}
