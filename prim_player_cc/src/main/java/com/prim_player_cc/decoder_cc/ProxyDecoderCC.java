package com.prim_player_cc.decoder_cc;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.loader.DecoderLoader;
import com.prim_player_cc.status.Status;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc 本类使用代理模式
 * ProxyDecoderCC 也实现 IDecoder 接口，同时代理了{@link BaseDecoderCC}的具体实现者, 方便切换播放器组件和添加其他逻辑
 * @time 2018/7/24 - 上午11:26
 */
public class ProxyDecoderCC implements IDecoder {

    private static final String TAG = "ProxyDecoderCC";

    private BaseDecoderCC decoderCC;

    private int decoderId;

    private PlayerSource source;

    private WeakReference<OnPlayerEventListener> weakOnPlayerEventListener;

    private WeakReference<OnErrorEventListener> weakOnErrorListener;

    private WeakReference<OnBufferingUpdateListener> weakOnBufferingUpdateListener;

    private WeakReference<OnTimerUpdateListener> weakOnTimerUpdateListener;

    private TimerUpdateHelper mTimerUpdateHelper;

    /**
     * 默认加载配置的 player ID
     * {@link PlayerCC_Config#usedDecoderId}
     */
    public ProxyDecoderCC() {
        mTimerUpdateHelper = new TimerUpdateHelper(1000);
        loadDecoder(PlayerCC_Config.getUseDecoderId());
    }

    /**
     * load player 根据播放器ID 加载具体的播放器组件
     * {@link DecoderLoader#loadPlayer(int)}
     *
     * @param decoderId player id
     */
    private void loadDecoder(int decoderId) {
        this.decoderId = decoderId;
        decoderCC = DecoderLoader.loadPlayer(this.decoderId);
        if (decoderCC == null) {
            throw new RuntimeException("build decoder failed,please check you config,decoderId:" + this.decoderId + " class not found");
        }
        DecoderWrapper decoder = PlayerCC_Config.getDecoder(this.decoderId);
        PrimLog.d(TAG, "load decoder :" + this.decoderId);
        PrimLog.d(TAG, "decoder class:" + decoder.getPlayerClass().getSimpleName());
        PrimLog.d(TAG, "decoder desc:" + decoder.getPlayerDec());
    }

    /**
     * 动态选择解码器组件
     *
     * @param decoderId player ID
     */
    public boolean switchDecoder(int decoderId) {
        if (this.decoderId == decoderId) {
            PrimLog.d(TAG, "current decoderId == switch decoderId");
            return false;
        }
        //检查播放器组件ID 是否存在
        if (PlayerCC_Config.checkDecoderId(decoderId)) {
            destroy();//将之前的解码器销毁掉，防止出现内存泄漏出现其他问题等
            PlayerCC_Config.setUseDecoderId(decoderId);//更换用户使用的解码器组件ID
            loadDecoder(decoderId);//加载播放器组件
            return true;
        } else {
            throw new RuntimeException("build decoder failed,please check you config,decoderId: " + decoderId + " not found");
        }
    }

    private boolean isDecoderInit() {
        return decoderCC != null;
    }

    @Override
    public void setDataSource(PlayerSource source) {
        this.source = source;
        if (isDecoderInit()) {
            _initListener();
            decoderCC.setDataSource(source);
        }
    }

    private void _initListener() {
        mTimerUpdateHelper.setOnTimerUpdateHandleListener(updateHandleListener);
        decoderCC.setPlayerEventListener(mOnPlayerEventListener);
        decoderCC.setOnErrorEventListener(mOnErrorEventListener);
        decoderCC.setBufferingUpdateListener(mOnBufferUpdateListener);
    }

    private void _resetListener() {
        if (isDecoderInit()) {
            mTimerUpdateHelper.setOnTimerUpdateHandleListener(null);
            decoderCC.setPlayerEventListener(null);
            decoderCC.setBufferingUpdateListener(null);
            decoderCC.setOnErrorEventListener(null);
        }
    }

    private TimerUpdateHelper.OnTimerUpdateHandleListener updateHandleListener = new TimerUpdateHelper.OnTimerUpdateHandleListener() {
        @Override
        public void onUpdate() {
            long currentPosition = getCurrentPosition();
            long duration = getDuration();
            if (currentPosition <= 0 || duration <= 0) {
                return;
            }
            mOnTimerUpdateListener.onUpdate(getCurrentPosition(), getDuration(), getBufferPercentage());
        }
    };

    @Override
    public void start() {
        if (isDecoderInit())
            decoderCC.start();
    }

    @Override
    public void start(long location) {
        if (isDecoderInit()) {
            decoderCC.start(location);
        }
    }

    @Override
    public void pause() {
        if (isDecoderInit()) {
            decoderCC.pause();
        }
    }

    @Override
    public void resume() {
        if (isDecoderInit()) {
            decoderCC.resume();
        }
    }

    @Override
    public void reset() {
        if (isDecoderInit()) {
            decoderCC.reset();
        }
    }

    @Override
    public void stop() {
        if (isDecoderInit()) {
            decoderCC.stop();
        }
    }

    @Override
    public void destroy() {
        _resetListener();
        if (isDecoderInit()) {
            decoderCC.destroy();
        }
    }

    @Override
    public void seek(int position) {
        if (isDecoderInit()) {
            decoderCC.seek(position);
        }
    }

    /**
     * 设置是否循环播放
     *
     * @param loop true 循环播放 false 不循环播放
     */
    @Override
    public void setLooping(boolean loop) {
        if (isDecoderInit()) {
            decoderCC.setLooping(loop);
        }
    }

    @Override
    public boolean isLooping() {
        return isDecoderInit() && decoderCC.isLooping();
    }

    @Override
    public int getState() {
        if (isDecoderInit()) {
            return decoderCC.getState();
        }
        return Status.STATE_IDEL;
    }

    @Override
    public boolean isPlaying() {
        if (isDecoderInit()) {
            return decoderCC.isPlaying();
        }
        return false;
    }

    @Override
    public long getCurrentPosition() {
        if (isDecoderInit()) {
            return decoderCC.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getDuration() {
        if (isDecoderInit()) {
            return decoderCC.getDuration();
        }
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        if (isDecoderInit()) {
            return decoderCC.getBufferPercentage();
        }
        return 0;
    }

    @Override
    public void setVolume(float left, float right) {
        if (isDecoderInit()) {
            decoderCC.setVolume(left, right);
        }
    }

    @Override
    public void setSpeed(float m) {
        if (isDecoderInit()) {
            decoderCC.setSpeed(m);
        }
    }


    @Override
    public void setPlayerEventListener(OnPlayerEventListener onPlayingListener) {
        this.weakOnPlayerEventListener = new WeakReference<>(onPlayingListener);
    }

    @Override
    public void setOnErrorEventListener(OnErrorEventListener onErrorListener) {
        this.weakOnErrorListener = new WeakReference<>(onErrorListener);
    }

    @Override
    public void setBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.weakOnBufferingUpdateListener = new WeakReference<>(onBufferingUpdateListener);
    }

    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        weakOnTimerUpdateListener = new WeakReference<>(onTimerUpdateListener);
    }

    /**
     * 将解码器传递过来的数据 传递给视图组
     */

    private OnPlayerEventListener mOnPlayerEventListener = new OnPlayerEventListener() {
        @Override
        public void onPlayerEvent(int eventCode, Bundle bundle) {
            if (mTimerUpdateHelper != null) {
                mTimerUpdateHelper.setUpdatePlayEvent(eventCode, bundle);
            }
            if (weakOnPlayerEventListener != null && weakOnPlayerEventListener.get() != null) {
                weakOnPlayerEventListener.get().onPlayerEvent(eventCode, bundle);
            }
        }
    };

    private OnErrorEventListener mOnErrorEventListener = new OnErrorEventListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            if (mTimerUpdateHelper != null) {
                mTimerUpdateHelper.setPlayEventError();
            }
            if (weakOnErrorListener != null && weakOnErrorListener.get() != null) {
                weakOnErrorListener.get().onError(bundle, errorCode);
            }
            return true;
        }
    };

    private OnBufferingUpdateListener mOnBufferUpdateListener = new OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(Bundle bundle, int buffer) {
            if (weakOnBufferingUpdateListener != null && weakOnBufferingUpdateListener.get() != null) {
                weakOnBufferingUpdateListener.get().onBufferingUpdate(bundle, buffer);
            }
        }
    };

    private OnTimerUpdateListener mOnTimerUpdateListener = new OnTimerUpdateListener() {
        @Override
        public void onUpdate(long current, long duration, int bufferPercentage) {
            if (weakOnTimerUpdateListener != null && weakOnTimerUpdateListener.get() != null) {
                weakOnTimerUpdateListener.get().onUpdate(current, duration, bufferPercentage);
            }
        }
    };

    @Override
    public void setSurface(Surface surface) {
        if (isDecoderInit()) {
            decoderCC.setSurface(surface);
        }
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (isDecoderInit()) {
            decoderCC.setDisplay(surfaceHolder);
        }
    }

    @Override
    public void bindVideoView(View videoView) {
        if (isDecoderInit()) {
            decoderCC.bindVideoView(videoView);
        }
    }

    @Override
    public View getRenderView() {
        if (isDecoderInit()) {
            return decoderCC.getRenderView();
        }
        return null;
    }

    @Override
    public int getVideoWidth() {
        if (isDecoderInit()) {
            return decoderCC.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (isDecoderInit()) {
            return decoderCC.getVideoHeight();
        }
        return 0;
    }
}
