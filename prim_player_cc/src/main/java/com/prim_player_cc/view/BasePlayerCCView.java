package com.prim_player_cc.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim_player_cc.config.PlayerCC_Config;
import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefaultControlCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCompleteCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCoverKey;
import com.prim_player_cc.cover_cc.defualt.DefaultErrorCover;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.render_cc.RenderSurfaceView;
import com.prim_player_cc.render_cc.RenderTextureView;
import com.prim_player_cc.status.Status;

/**
 * @author prim
 * @version 1.0.0
 * @desc playerCC base view 播放器组件的基础view
 * 主要承载了:
 * 1、加载覆盖视图组件
 * 2、加载呈现视频视图组件
 * 3、同时还有播放操作
 * 4、如有其他逻辑可在继承此类基础上扩展
 * @time 2018/7/24 - 下午5:08
 */
public abstract class BasePlayerCCView extends FrameLayout implements IPlayerCCView {

    private static final String TAG = "BasePlayerCCView";

    protected ProxyDecoderCC proxyDecoderCC;

    protected BusPlayerView busPlayerView;

    protected IRenderView mRenderView;

    protected View renderView;

    protected ICoverGroup coverGroup;

    protected IRenderView.ISurfaceHolder mSurfaceHolder;

    protected int mSurfaceWidth, mSurfaceHeight;

    public BasePlayerCCView(@NonNull Context context) {
        this(context, null);
    }

    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void _init(Context context, AttributeSet attrs, int defStyleAttr) {
        PrimLog.d(TAG, "build player cc View");
        proxyDecoderCC = new ProxyDecoderCC();
        //初始化视图组
        coverGroup = new CoverGroup();
        CoverCCManager.getInstance().setCoverGroup(coverGroup);
        //初始化视图组件总线的view
        busPlayerView = new BusPlayerView(context);
        //
        busPlayerView.setOnCoverNativePlayerListener(onCoverNativePlayerListener);
        //将视图组件总线view 添加到 视频组件基类view中 在最底层
        addView(busPlayerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initView();
        _initListener();
    }

    private void _initListener() {
        if (proxyDecoderCC != null) {
            PrimLog.d(TAG, "set decoder listener");
            proxyDecoderCC.setPlayerEventListener(playerEventListener);
            proxyDecoderCC.setOnErrorEventListener(errorListener);
            proxyDecoderCC.setOnTimerUpdateListener(onTimerUpdateListener);
        } else {
            PrimLog.d(TAG, "DecoderCC is null,please check code");
        }
    }

    protected abstract void initView();

    /**
     * 设置是否循环播放
     *
     * @param loop true 循环播放 false 不是循环播放
     */
    @Override
    public void setLooping(boolean loop) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setLooping(loop);
        }
    }


    /**
     * 获取是否为循环播放
     *
     * @return true 是 false 否
     */
    @Override
    public boolean isLooping() {
        return proxyDecoderCC != null && proxyDecoderCC.isLooping();
    }

    /**
     * 添加覆盖视图组，如果想要重新设置视图组，需要设置此方法
     *
     * @param coverGroup {@link ICoverGroup}
     */
    @Override
    public void setCoverGroup(ICoverGroup coverGroup) {
        if (busPlayerView != null) {
            busPlayerView.setCoverGroup(coverGroup);
        }
    }

    /**
     * 获取当前的覆盖视图组
     *
     * @return {@link ICoverGroup}
     */
    @Override
    public ICoverGroup getCoverGroup() {
        if (busPlayerView != null) {
            return busPlayerView.getCoverGroup();
        }
        return null;
    }

    /**
     * 根据解码器ID，切换解码器组件
     * 如果当前的view，想要使用其他的解码器，可调用此方法进行切换.
     * 解码器必须实现此类 {@link com.prim_player_cc.decoder_cc.BaseDecoderCC}
     * 同时要在Application 中初始化 {@link PlayerCC_Config#configBuild()} 想要切换的解码器，否则会找不到相关的解码器
     *
     * @param decoderId 解码器组件ID
     */
    @Override
    public void switchDecoder(int decoderId) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.switchDecoder(decoderId);
        }
    }

    public IDecoder getDecoder() {
        return proxyDecoderCC;
    }

    /**
     * 设置播放资源数据
     *
     * @param dataSource 资源数据
     */
    @Override
    public void setDataSource(PlayerSource dataSource) {
        proxyDecoderCC.setDataSource(dataSource);
        requestLayout();
        invalidate();
    }

    /**
     * 如果自己已经实现了
     * SurfaceView 或 TextureView，
     * 便不需要 本库中的view ，将此view绑定到组件播放器view
     */
    private void bindVideoView() {
        proxyDecoderCC.bindVideoView(this);
    }

    /**
     * 获取视图总线组件view
     *
     * @return {@link BusPlayerView}
     */
    @Override
    public BusPlayerView getBusPlayerView() {
        return busPlayerView;
    }

    /**
     * 使用默认的覆盖视图组件
     */
    @Override
    public void usedDefaultCoverGroup() {
        CoverCCManager.getInstance()
                .addCover(DefaultCoverKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(getContext()))
                .addCover(DefaultCoverKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(getContext()))
                .addCover(DefaultCoverKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(getContext()))
                .addCover(DefaultCoverKey.DEFAULT_COMPLETE_COVER, new DefaultCompleteCover(getContext()));
        setCoverGroup(CoverCCManager.getInstance().getCoverGroup());
    }


    @Override
    public void setRenderView(int type) {
        switch (type) {
            case IRenderView.RENDER_NONE:
                addRenderView(null);
                break;
            case IRenderView.SURFACE_VIEW:
                RenderSurfaceView renderSurfaceView = new RenderSurfaceView(getContext());
                addRenderView(renderSurfaceView);
                break;
            case IRenderView.CUSTOM_VIEW:
                IRenderView renderView = proxyDecoderCC.getRenderView();
                addRenderView(renderView);
                break;
            case IRenderView.TEXTURE_VIEW:
                RenderTextureView renderTextureView = new RenderTextureView(getContext());
                if (proxyDecoderCC != null) {
                    renderTextureView.getSurfaceHolder().bindToMediaPlayer(proxyDecoderCC);
                    renderTextureView.updateRenderSize(proxyDecoderCC.getVideoWidth(), proxyDecoderCC.getVideoHeight());
                }
                addRenderView(renderTextureView);
                break;
            default:
                Log.e(TAG, "invalid render");
                break;
        }
    }

    private void addRenderView(IRenderView renderView) {
        if (mRenderView != null) {
            proxyDecoderCC.setDisplay(null);
            proxyDecoderCC.setSurface(null);
            mRenderView.removeRenderCallback(mRCallback);
            mRenderView = null;
        }
        if (renderView == null) {
            return;
        }
        this.mRenderView = renderView;
        if (proxyDecoderCC.getVideoWidth() > 0 && proxyDecoderCC.getVideoHeight() > 0) {
            this.mRenderView.updateRenderSize(proxyDecoderCC.getVideoWidth(), proxyDecoderCC.getVideoHeight());
        }
        this.mRenderView.setRenderCallback(mRCallback);
        busPlayerView.setRenderView(mRenderView);
    }

    IRenderView.IRenderCallback mRCallback = new IRenderView.IRenderCallback() {

        @Override
        public void surfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width, int height) {
            if (holder.getRenderView() != mRenderView) {
                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
                return;
            }
            mSurfaceHolder = holder;
            if (holder == null) {
                proxyDecoderCC.setDisplay(null);
                return;
            }
            holder.bindToMediaPlayer(proxyDecoderCC);
        }

        @Override
        public void surfaceChanged(@NonNull IRenderView.ISurfaceHolder holder, int format, int width, int height) {
            if (holder.getRenderView() != mRenderView) {
                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
                return;
            }
            mSurfaceWidth = width;
            mSurfaceHeight = height;
        }

        @Override
        public void surfaceDestroyed(@NonNull IRenderView.ISurfaceHolder holder) {
            if (holder.getRenderView() != mRenderView) {
                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
                return;
            }
            mSurfaceHolder = null;
            proxyDecoderCC.setDisplay(null);
        }
    };

    //----------------- 播放监听相关 -------------------//
    OnPlayerEventListener playerEventListener = new OnPlayerEventListener() {
        @Override
        public void onPlayerEvent(int eventCode, Bundle bundle) {
            if (busPlayerView != null) {
                busPlayerView.dispatchPlayEvent(eventCode, bundle);
            }
        }
    };

    OnErrorEventListener errorListener = new OnErrorEventListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            if (busPlayerView != null) {
                busPlayerView.dispatchErrorEvent(errorCode, bundle);
            }
            return true;
        }
    };

    OnTimerUpdateListener onTimerUpdateListener = new OnTimerUpdateListener() {
        @Override
        public void onUpdate(long current, long duration, int bufferPercentage) {
            if (busPlayerView != null) {
                Bundle bundle = new Bundle();
                bundle.putLong(EventCodeKey.PLAYER_CURRENT, current);
                bundle.putLong(EventCodeKey.PLAYER_DURATION, duration);
                bundle.putLong(EventCodeKey.PLAYER_BUFFER, bufferPercentage);
                busPlayerView.dispatchPlayEvent(PlayerEventCode.PRIM_PLAYER_EVENT_TIMER_UPDATE, bundle);
            }
        }
    };

    /**
     * 视图和播放器的桥接事件监听,视图调用播放器的某些方法
     */
    OnCoverNativePlayerListener onCoverNativePlayerListener = new OnCoverNativePlayerListener() {
        @Override
        public void onEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case CoverEventCode.COVER_EVENT_PAUSE:
                    pause();
                    break;
                case CoverEventCode.COVER_EVENT_START:
                case CoverEventCode.COVER_EVENT_RESUME:
                    start();
                    break;
            }
        }
    };

    //------------------ 播放控制相关 -------------------//

    /**
     * 开始播放
     */
    @Override
    public void start() {
        proxyDecoderCC.start();
    }

    /**
     * 开始从某个位置播放
     *
     * @param location
     */
    @Override
    public void start(long location) {
        proxyDecoderCC.start(location);
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        proxyDecoderCC.pause();
    }

    /**
     * 继续播放
     */
    @Override
    public void resume() {
        proxyDecoderCC.resume();
    }

    /**
     * 重置播放
     */
    @Override
    public void reset() {
        proxyDecoderCC.reset();
    }

    /**
     * 停止播放
     */
    @Override
    public void stop() {
        proxyDecoderCC.stop();
    }

    /**
     * 销毁播放器
     */
    @Override
    public void destroy() {
        proxyDecoderCC.destroy();
        busPlayerView.destroy();
    }

    /**
     * 跳转到某个位置播放
     *
     * @param position
     */
    @Override
    public void seek(int position) {
        proxyDecoderCC.seek(position);
    }

    /**
     * 获取播放状态
     *
     * @return {@link Status}
     */
    @Override
    public int getState() {
        return proxyDecoderCC.getState();
    }

    /**
     * 是否正在播放中
     *
     * @return true 正在播放 false 没有播放
     */
    @Override
    public boolean isPlaying() {
        return proxyDecoderCC.isPlaying();
    }

    /**
     * 获取当前到播放进度
     *
     * @return long
     */
    @Override
    public long getCurrentPosition() {
        return proxyDecoderCC.getCurrentPosition();
    }

    /**
     * 获取总到播放进度
     *
     * @return long
     */
    @Override
    public long getDuration() {
        return proxyDecoderCC.getDuration();
    }

    /**
     * 获取缓存进度
     *
     * @return long
     */
    @Override
    public int getBufferPercentage() {
        return proxyDecoderCC.getBufferPercentage();
    }

    /**
     * 设置声音
     *
     * @param left
     * @param right
     */
    @Override
    public void setVolume(float left, float right) {
        proxyDecoderCC.setVolume(left, right);
    }

    /**
     * 设置播放速度
     *
     * @param m
     */
    @Override
    public void setSpeed(float m) {
        proxyDecoderCC.setSpeed(m);
    }
}
