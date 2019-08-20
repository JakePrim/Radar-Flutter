package com.prim_player_cc.decoder_cc;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.config.PrimPlayerConfig;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.decoder_cc.helper.TimerUpdateHelper;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.IDataProvider;
import com.prim_player_cc.source_cc.IPoolOperate;
import com.prim_player_cc.source_cc.NodeData;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.loader.DecoderLoader;
import com.prim_player_cc.status.Status;

import java.lang.ref.WeakReference;

import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_AUTO_NEXT;
import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_DATA_SOURCE;

/**
 * @author prim
 * @version 1.0.0
 * @desc 本类使用代理模式
 * ProxyDecoderCC 也实现 IDecoder 接口，同时代理了{@link AbsDecoderCC}的具体实现者, 方便切换播放器组件和添加其他逻辑
 * 同时资源池的操作
 * @time 2018/7/24 - 上午11:26
 */
public class ProxyDecoderCC implements IDecoder {

    private static final String TAG = "ProxyDecoderCC";

    private AbsDecoderCC decoderCC;

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
     * {@link PrimPlayerConfig#usedDecoderId}
     */
    public ProxyDecoderCC() {
        mTimerUpdateHelper = new TimerUpdateHelper(1000);
        loadDecoder(PrimPlayerConfig.getUseDecoderId());
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
        DecoderWrapper decoder = PrimPlayerConfig.getDecoder(this.decoderId);
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
            PrimLog.d(TAG, "current decoderId == switch decoderId:" + decoderId);
            return false;
        }
        //检查播放器组件ID 是否存在
        if (PrimPlayerConfig.checkDecoderId(decoderId)) {
            destroy();//将之前的解码器销毁掉，防止出现内存泄漏出现其他问题等
            PrimPlayerConfig.setUseDecoderId(decoderId);//更换用户使用的解码器组件ID
            loadDecoder(decoderId);//加载播放器组件
            return true;
        } else {
            PrimLog.d(TAG, "build decoder failed,please check you config,decoderId: " + decoderId + " not found");
            return false;
//            throw new RuntimeException("build decoder failed,please check you config,decoderId: " + decoderId + " not found");
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
        _resetListener();
        if (isDecoderInit()) {
            PrimLog.e(TAG, "decoder not null");
            _initListener();
            decoderCC.setDataSource(source);
        } else {
            PrimLog.e(TAG, "decoder is null");
            loadDecoder(PrimPlayerConfig.getUseDecoderId());
            _initListener();
            decoderCC.setDataSource(source);
        }
        openVideo();
    }

    private AbsDataProvider provider;

    private IDataProvider.OnDataProviderListener onDataProviderListener;

    /**
     * 设置数据提供者
     *
     * @param provider 具体实现的数据提供者
     */
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

    /**
     * 是否存在下一个视频
     *
     * @return true 有；false 没有
     */
    public boolean hasNext() {
        return isUseProvider() && provider.isNext();
    }

    /**
     * 是否存在上一个视频
     *
     * @return true 有；false 没有
     */
    public boolean hasForward() {
        return isUseProvider() && provider.isForward();
    }

    /**
     * 手动播放下一个
     */
    public void playerNext() {
        if (isUseProvider()) {
            NodeData nodeData = provider.manualGetNextPlaySource();
            if (nodeData != null && nodeData.source != null) {
                initDataSource(nodeData.source);
            }
        }
    }

    /**
     * 手动播放上一个
     */
    public void playerForward() {
        if (isUseProvider()) {
            NodeData nodeData = provider.manualGetForwardPlaySource();
            if (nodeData != null && nodeData.source != null) {
                initDataSource(nodeData.source);
            }
        }
    }

    /**
     * 选择某一个进行播放
     *
     * @param position 位置
     */
    public void selectDataPlay(int position) {
        if (isUseProvider()) {
            if (position < 0 || position > provider.size()) {
                return;
            }
            PlayerSource source = provider.playIndexData(position);
            if (source != null) {
                initDataSource(source);
            }
        }
    }

    public void setLoopMode(int mode) {
        if (isUseProvider()) {
            provider.setLoopMode(mode);
            if (mode == IPoolOperate.LOOP_MODE_SINGLE) {
                setLooping(true);
            } else {
                setLooping(false);
            }
        }
    }

    public int getLoopMode() {
        if (isUseProvider()) {
            return provider.getLoopMode();
        }
        return -1;
    }

    public int currentPlayIndex() {
        if (isUseProvider()) {
            return provider.currentPlayIndex();
        }
        return -1;
    }

    public void setDataProviderListener(IDataProvider.OnDataProviderListener onDataProviderListener) {
        this.onDataProviderListener = onDataProviderListener;
    }

    private IDataProvider.OnDataProviderListener mOnDataProviderListener = new IDataProvider.OnDataProviderListener() {
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
                PrimLog.e(TAG, "绑定数据完成：" + currentSourceData.getUrl());
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
        mHandler.removeCallbacksAndMessages(null);
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
        try {
            if (isDecoderInit() && (getState() == Status.STATE_PREPARED
                    || getState() == Status.STATE_PAUSE
                    || getState() == Status.STATE_COMPLETE)) {
                decoderCC.start();
                updateState(Status.STATE_START);
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        if (null != e) {
            e.printStackTrace();
        }
        reset();
    }

    @Override
    public void start(long location) {
        try {
            if (isDecoderInit() && (getState() == Status.STATE_PREPARED
                    || getState() == Status.STATE_PAUSE
                    || getState() == Status.STATE_COMPLETE)) {
                decoderCC.start(location);
                updateState(Status.STATE_START);
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public void pause() {
        try {
            if (isDecoderInit()) {
                if (decoderCC.isPlaying()) {
                    updateState(Status.STATE_PAUSE);
                    decoderCC.pause();
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public void resume() {
        try {
            if (isDecoderInit() && getState() == Status.STATE_PAUSE) {
                updateState(Status.STATE_PAUSE);
                decoderCC.resume();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public void reset() {
        try {
            if (isDecoderInit()) {
                decoderCC.reset();
                updateState(Status.STATE_IDEL);
            }
            if (isUseProvider()) {
                provider.startBindSourceData();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public void release() {
        try {
            if (isDecoderInit()) {
                decoderCC.release();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public void stop() {
        try {
            if (isDecoderInit() && getState() != Status.STATE_STOP) {
                decoderCC.stop();
                updateState(Status.STATE_STOP);
                abandonAudioFocus();
            }
        } catch (Exception e) {
            handleException(e);
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
        updateState(Status.STATE_END);
        if (isDecoderInit()) {
            decoderCC.destroy();
            decoderCC = null;
        }
        if (isUseProvider()) {
            provider.destory();
            provider = null;
        }
        _resetListener();
    }

    @Override
    public void seek(long msec) throws IllegalStateException {
        try {
            if (isDecoderInit() && (getState() == Status.STATE_START
                    || getState() == Status.STATE_PAUSE
                    || getState() == Status.STATE_COMPLETE
                    || getState() == Status.STATE_PREPARED)) {
                decoderCC.seek(msec);
            }
        } catch (Exception e) {
            handleException(e);
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
            if (isUseProvider()) {
                provider.setLoopMode(IPoolOperate.LOOP_MODE_SINGLE);
            }
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
        if (isDecoderInit() && getState() != Status.STATE_ERROR) {
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
            //如果静音 设置不获取音频焦点
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
            PrimLog.e(TAG, "mOnPlayerEventListener:" + eventCode);
            switch (eventCode) {
                case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                    //更新状态
                    updateState(Status.STATE_COMPLETE);
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean(PLAYER_AUTO_NEXT, (autoNext && hasNext()));
                    triggerPlayerEvent(eventCode, bundle1);
                    completion();
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
                    updateState(Status.STATE_PREPARED);
                    triggerPlayerEvent(eventCode, bundle);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY:
                    updateState(Status.STATE_END);
                    triggerPlayerEvent(eventCode, bundle);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PAUSE:
                    updateState(Status.STATE_PAUSE);
                    triggerPlayerEvent(eventCode, bundle);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_STOP:
                    PrimLog.e(TAG, "FrontCover ：停止播放");
                    updateState(Status.STATE_IDEL);
                    triggerPlayerEvent(eventCode, bundle);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARING:
                    updateState(Status.STATE_PREPARING);
                    triggerPlayerEvent(eventCode, bundle);
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE:
                    updateState(Status.STATE_INIT);
                    triggerPlayerEvent(eventCode, bundle);
                    break;
                default:
                    triggerPlayerEvent(eventCode, bundle);
                    break;
            }
            if (mTimerUpdateHelper != null) {
                mTimerUpdateHelper.setUpdatePlayEvent(eventCode, bundle);
            }
        }
    };

    private void triggerPlayerEvent(int eventCode, Bundle bundle) {
        if (weakOnPlayerEventListener != null && weakOnPlayerEventListener.get() != null) {
            PrimLog.e("PRIM!!", "ProxyDecoder -> " + eventCode);
            weakOnPlayerEventListener.get().onPlayerEvent(eventCode, bundle);
        }
    }

    private int waitTime = 2000;

    private static final int WHAT_AUTO_PLAY_NEXT_ACTION = 8888;

    private H mHandler = new H(this);

    public void autoNextPlayWaitTime(int time) {
        this.waitTime = time;
    }

    /**
     * 播放完成后的处理
     */
    private void completion() {
        //根据数据提供者和设置来判断是否自动播放下一个
        if (autoNext) {
            if (getLoopMode() == IPoolOperate.LOOP_MODE_FINISH_PAUSE) {
                pause();
            } else {
                mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY_NEXT_ACTION, waitTime);
            }
        } else {
            PrimLog.e(TAG, "播放完毕");
        }
    }

    public void removePlayNext() {
        mHandler.removeMessages(WHAT_AUTO_PLAY_NEXT_ACTION);
    }

    private void handlePlayNext() {
        if (isUseProvider()) {
            NodeData nodeData = provider.autoGetNextPlaySource();
            if (nodeData != null) {
                PlayerSource source = nodeData.source;
                if (source != null) {
                    //将已经自动播放下一个的事件分发出去
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(PLAYER_DATA_SOURCE, source);
                    triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT, bundle);
                    initDataSource(source);
                }
            }
        }
    }

    private static class H extends Handler {
        private WeakReference<ProxyDecoderCC> decoderCC;

        private H(ProxyDecoderCC decoderCC) {
            this.decoderCC = new WeakReference<>(decoderCC);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_AUTO_PLAY_NEXT_ACTION:
                    if (decoderCC != null && decoderCC.get() != null) {
                        decoderCC.get().handlePlayNext();
                    }
                    break;
            }
        }

    }

    private OnErrorEventListener mOnErrorEventListener = new OnErrorEventListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            PrimLog.e(TAG, "mOnErrorEventListener:" + errorCode);
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
