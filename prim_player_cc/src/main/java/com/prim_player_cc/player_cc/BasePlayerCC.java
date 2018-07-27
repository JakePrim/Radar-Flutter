package com.prim_player_cc.player_cc;

import android.os.Bundle;

import com.prim_player_cc.IPlayer;
import com.prim_player_cc.state.PlayerState;
import com.prim_player_cc.state.State;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc this is  the basic player component that
 * needs to inherit this base component
 * @time 2018/7/24 - 下午3:08
 */
public abstract class BasePlayerCC implements IPlayer {
    protected @PlayerState
    int mCurrentState = State.STATE_IDEL;

    protected WeakReference<OnPreparedListener> weakPreparedListener;
    protected WeakReference<OnPlayingListener> weakPlayingListener;
    protected WeakReference<OnInfoListener> weakInfoListener;
    protected WeakReference<OnErrorListener> weakErrorListener;
    protected WeakReference<OnCompletionListener> weakCompletionListener;
    protected WeakReference<OnBufferingUpdateListener> weakBufferListener;

    protected int mBufferPercentage;

    @Override
    public int getState() {
        return mCurrentState;
    }

    protected void updateState(@PlayerState int state) {
        this.mCurrentState = state;
    }

    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public void setPreparedListener(OnPreparedListener onPreparedListener) {
        weakPreparedListener = new WeakReference<>(onPreparedListener);
    }

    @Override
    public void setPlayingListener(OnPlayingListener onPlayingListener) {
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

    protected void triggerPrepared(Bundle bundle, int i) {
        if (weakPreparedListener != null && weakPreparedListener.get() != null) {
            weakPreparedListener.get().onPrepared(bundle, i);
        }
    }

    protected void triggerPlaying(long current, long duration, @PlayerState int state) {
        if (weakPlayingListener != null && weakPlayingListener.get() != null) {
            weakPlayingListener.get().onPlaying(current, duration, state);
        }
    }

    protected void triggerError(Bundle bundle, int errorCode) {
        if (weakErrorListener != null && weakErrorListener.get() != null) {
            weakErrorListener.get().onError(bundle, errorCode);
        }
    }

    protected void triggerInfo(Bundle bundle, int var1, int var2) {
        if (weakInfoListener != null && weakInfoListener.get() != null) {
            weakInfoListener.get().onInfo(bundle, var1, var2);
        }
    }

    protected void triggerCompletion(Bundle bundle) {
        if (weakCompletionListener != null && weakCompletionListener.get() != null) {
            weakCompletionListener.get().onCompletion(bundle);
        }
    }

    protected void triggerBufferUpdate(Bundle bundle, int buffer) {
        this.mBufferPercentage = buffer;
        if (weakBufferListener != null && weakBufferListener.get() != null) {
            weakBufferListener.get().onBufferingUpdate(bundle, buffer);
        }
    }

    @Override
    public void destroy() {
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

        this.mCurrentState = State.STATE_IDEL;
    }
}
