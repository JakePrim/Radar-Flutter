package com.prim_player_cc.decoder_cc;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.decoder_cc.helper.TimerUpdateHelper;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.render_cc.AVOptions;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.IDataProvider;
import com.prim_player_cc.source_cc.NodeData;
import com.prim_player_cc.source_cc.PlayerSource;
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

    private int mTargetState = Status.STATE_IDEL;

    private boolean autoNext = false;

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
        updateState(Status.STATE_IDEL);
        DecoderWrapper decoder = PlayerCC_Config.getDecoder(this.decoderId);
        PrimLog.d(TAG, "load decoder :" + this.decoderId);
        PrimLog.d(TAG, "decoder class:" + decoder.getPlayerClass().getSimpleName());
        PrimLog.d(TAG, "decoder desc:" + decoder.getPlayerDec());
    }

    /**
     * 是否自动播放下一个
     *
     * @param autoNext true 如果存在下一个自动播放下一个；false 不管存不存在不自动播放下一个
     */
    public void autoPlayNext(boolean autoNext) {
        this.autoNext = autoNext;
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

    private boolean isUseProvider() {
        return provider != null;
    }

    @Override
    public void setDataSource(PlayerSource source) {
        this.source = source;
        if (!isUseProvider()) {
            initDataSource(source);
        }
    }

    private void initDataSource(PlayerSource source) {
        this.source = source;
        if (isDecoderInit()) {
            _initListener();
            decoderCC.setDataSource(source);
        } else {
            loadDecoder(PlayerCC_Config.getUseDecoderId());
            _initListener();
            decoderCC.setDataSource(source);
        }
        openVideo();
    }

    private AbsDataProvider provider;

    private IDataProvider.OnDataProviderListener onDataProviderListener;

    public void setDataProvider(AbsDataProvider provider) {
        if (provider == null) {
            return;
        }
        if (this.provider != null) {
            provider.destory();
            this.provider = null;
        }
        this.provider = provider;
        //为了防止监听不到 这里代理监听
        provider.setOnDateProviderListener(mOnDataProviderListener);
        //开始将数据加入到数据池中
        provider.startBindSourceData();
    }

    public void setDataProviderListener(IDataProvider.OnDataProviderListener onDataProviderListener) {
        this.onDataProviderListener = onDataProviderListener;
    }

    IDataProvider.OnDataProviderListener mOnDataProviderListener = new IDataProvider.OnDataProviderListener() {
        @Override
        public void onBindDataStart() {
            if (onDataProviderListener != null) {
                onDataProviderListener.onBindDataStart();
            }
        }

        @Override
        public void onBindDataCancle() {
            if (onDataProviderListener != null) {
                onDataProviderListener.onBindDataCancle();
            }
        }

        @Override
        public void onBindDataSuccess(PlayerSource source) {
            if (onDataProviderListener != null) {
                onDataProviderListener.onBindDataSuccess(source);
            }
        }

        @Override
        public void onBindDataError(int code) {
            if (onDataProviderListener != null) {
                onDataProviderListener.onBindDataError(code);
            }
        }

        @Override
        public void onBindDataFinish() {
            if (onDataProviderListener != null) {
                onDataProviderListener.onBindDataFinish();
            }
            //绑定数据完毕后设置当前的播放资源
            if (provider != null) {
                PlayerSource currentSourceData = provider.getCurrentSourceData();
                initDataSource(currentSourceData);
            }
        }
    };

    /**
     * 请求音频焦点
     */
    private void requestAudioFocus() {
        AudioManager am = (AudioManager) ApplicationAttach.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (am != null) {
            am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    private void _initListener() {
        mTimerUpdateHelper.setOnTimerUpdateHandleListener(updateHandleListener);
        decoderCC.setPlayerEventListener(mOnPlayerEventListener);
        decoderCC.setOnErrorEventListener(mOnErrorEventListener);
        decoderCC.setBufferingUpdateListener(mOnBufferUpdateListener);
    }

    private void _resetListener() {
        mTimerUpdateHelper.setOnTimerUpdateHandleListener(null);
        mTimerUpdateHelper.cancleH();
        if (isDecoderInit()) {
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
    public PlayerSource getDataSource() {
        return source;
    }

    @Override
    public void setAVOptions(AVOptions avOptions) {
        if (isDecoderInit()) {
            decoderCC.setAVOptions(avOptions);
        }
    }

    public void openVideo() {
        if (isDecoderInit()) {
            requestAudioFocus();
            updateState(Status.STATE_PREPARING);
        }
    }

    @Override
    public void updateState(int state) {
        mTargetState = state;
        if (isDecoderInit()) {
            decoderCC.updateState(state);
        }
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        if (isDecoderInit()) {
            decoderCC.prepareAsync();
        }
    }

    @Override
    public void start() {
        if (isDecoderInit()) {
            decoderCC.start();
            updateState(Status.STATE_PREPARING);
        }
    }

    @Override
    public void start(long location) {
        if (isDecoderInit()) {
            decoderCC.start(location);
            updateState(Status.STATE_PREPARING);
        }
    }

    @Override
    public void pause() {
        if (isDecoderInit()) {
            if (decoderCC.isPlaying()) {
                updateState(Status.STATE_PAUSE);
                decoderCC.pause();
            }
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
        if (isUseProvider()) {
            provider.startBindSourceData();
        }
    }

    @Override
    public void release() {
        if (isDecoderInit()) {
            decoderCC.release();
        }
    }

    @Override
    public void stop() {
        _resetListener();
        if (isDecoderInit()) {
            updateState(Status.STATE_IDEL);
            decoderCC.stop();
            abandonAudioFocus();
        }
    }

    private void abandonAudioFocus() {
        AudioManager am = (AudioManager) ApplicationAttach.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (am != null) {
            am.abandonAudioFocus(null);
        }
    }

    @Override
    public void destroy() {
        _resetListener();
        updateState(Status.STATE_END);
        if (isDecoderInit()) {
            decoderCC.destroy();
            decoderCC = null;
        }
        if (isUseProvider()) {
            provider.destory();
            provider = null;
        }
    }

    @Override
    public void seek(long msec) throws IllegalStateException {
        if (isDecoderInit()) {
            decoderCC.seek(msec);
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
        return -1;
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

    private OnPlayerEventListener mOnPlayerEventListener = new OnPlayerEventListener() {
        @Override
        public void onPlayerEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                    completion();
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
                    updateState(Status.STATE_PREPARED);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY:
                    updateState(Status.STATE_END);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PAUSE:
                    updateState(Status.STATE_PAUSE);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_STOP:
                    updateState(Status.STATE_IDEL);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARING:
                    updateState(Status.STATE_PREPARING);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE:
                    updateState(Status.STATE_INIT);
                    break;
                default:
                    break;
            }
            if (mTimerUpdateHelper != null) {
                mTimerUpdateHelper.setUpdatePlayEvent(eventCode, bundle);
            }
            if (weakOnPlayerEventListener != null && weakOnPlayerEventListener.get() != null) {
                weakOnPlayerEventListener.get().onPlayerEvent(eventCode, bundle);
            }
        }
    };

    private void completion() {
        //更新状态
        updateState(Status.STATE_COMPLETE);
        //根据数据提供者和设置来判断是否自动播放下一个
        if (autoNext) {
            if (isUseProvider()) {
                NodeData nodeData = provider.autoGetNextPlaySource();
                if (nodeData != null) {
                    PlayerSource source = nodeData.source;
                    if (source != null) {
                        initDataSource(source);
                        start();
                    }
                }
            }
        }
    }

    private OnErrorEventListener mOnErrorEventListener = new OnErrorEventListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            updateState(Status.STATE_ERROR);
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
    public IRenderView getRenderView() {
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

    @Override
    public int getVideoSarNum() {
        if (isDecoderInit()) {
            return decoderCC.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (isDecoderInit()) {
            return decoderCC.getVideoSarDen();
        }
        return 0;
    }

    @Override
    public void setWakeMode(Context context, int mode) {
        if (isDecoderInit()) {
            decoderCC.setWakeMode(context, mode);
        }
    }

    @Override
    public void setLogEnabled(boolean enabled) {
        if (isDecoderInit()) {
            decoderCC.setLogEnabled(enabled);
        }
    }
}
