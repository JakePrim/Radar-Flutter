package com.prim_player_cc.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.prim_player_cc.PrimPlayerCC;
import com.prim_player_cc.R;
import com.prim_player_cc.config.PrimPlayerConfig;
import com.prim_player_cc.cover_cc.CoverCCManager;
import com.prim_player_cc.cover_cc.CoverGroup;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.decoder_cc.AbsDecoderCC;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.decoder_cc.IMediaController;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
import com.prim_player_cc.cover_cc.ICoverGroup;
import com.prim_player_cc.cover_cc.defualt.DefaultControlCover;
import com.prim_player_cc.cover_cc.defualt.DefaultCompleteCover;
import com.prim_player_cc.cover_cc.event.CoverEventKey;
import com.prim_player_cc.cover_cc.defualt.DefaultErrorCover;
import com.prim_player_cc.cover_cc.defualt.DefaultLoadCover;
import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.expand.AbsEventProducer;
import com.prim_player_cc.render_cc.AssistTextureView;
import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.IDataProvider;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.render_cc.RenderSurfaceView;
import com.prim_player_cc.render_cc.RenderTextureView;
import com.prim_player_cc.status.Status;
import com.prim_player_cc.utils.NetworkTools;
import com.prim_player_cc.utils.Tools;

import static com.prim_player_cc.render_cc.IRenderView.AR_ASPECT_FIT_PARENT;

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
public abstract class BasePlayerCCView extends FrameLayout implements IPlayerCCView, IMediaController.MediaPlayerControl {

    private static final String TAG = "BasePlayerCCView";

    protected ProxyDecoderCC proxyDecoderCC;

    protected BusPlayerView busPlayerView;

    protected IRenderView mRenderView;

    protected ICoverGroup coverGroup;

    protected IRenderView.ISurfaceHolder mSurfaceHolder;

    protected int mSurfaceWidth, mSurfaceHeight;

    private int mVideoRotationDegree;

    private int mVideoWidth, mVideoHeight;

    private int mVideoSarNum, mVideoSarDen;

    protected int mRenderType = IRenderView.TEXTURE_VIEW;

    private AbsDataProvider dataProvider;

    private PlayerSource playerSource;

    public BasePlayerCCView(@NonNull Context context) {
        this(context, null);
    }

    public BasePlayerCCView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
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

    protected void _init(Context context, AttributeSet attrs, int defStyleAttr) {
        PrimLog.d(TAG, "build player cc View");
        //创建视图组
        createCoverGroup();
        //创建总线view
        createBusView(context);
        //获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestLayout();
        //add BusPlayerView
        addBusPlayerView();
        initView();
        //初始化MediaPlayer
        proxyDecoderCC = createMediaPlayer();
        //初始化监听
        _initListener();
        //初始化render view
        setRenderView(mRenderType);
    }

    /**
     * 获取视频当前帧截图
     *
     * @return Bitmap
     */
    @Override
    public Bitmap getShortcut() {
        if (mRenderView != null) {
            return mRenderView.getShortcut();
        }
        return null;
    }

    protected void addBusPlayerView() {
        //将视图组件总线view 添加到 视频组件基类view中 在最底层
        addView(busPlayerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private ProxyDecoderCC createMediaPlayer() {
        return new ProxyDecoderCC();
    }


    public ProxyDecoderCC getProxyDecoderCC() {
        return proxyDecoderCC;
    }

    protected void createBusView(Context context) {
        //初始化视图组件总线的view
        busPlayerView = new BusPlayerView(context);
        //将MediaPlayerControl 传递给视图
        busPlayerView.setMediaPlayerControl(this);
        //视图与播放器的桥接监听
        busPlayerView.setOnCoverNativePlayerListener(onCoverNativePlayerListener);
    }

    protected void createCoverGroup() {
        coverGroup = new CoverGroup();
        CoverCCManager.getInstance().setCoverGroup(coverGroup);
    }

    protected void _initListener() {
        if (proxyDecoderCC != null) {
            PrimLog.d(TAG, "set decoder listener");
            proxyDecoderCC.setPlayerEventListener(playerEventListener);
            proxyDecoderCC.setOnErrorEventListener(errorListener);
            proxyDecoderCC.setBufferingUpdateListener(bufferingUpdateListener);
            proxyDecoderCC.setOnTimerUpdateListener(onTimerUpdateListener);
        } else {
            PrimLog.d(TAG, "DecoderCC is null,please check code");
        }
    }

    protected void _resetListener() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setPlayerEventListener(null);
            proxyDecoderCC.setOnErrorEventListener(null);
            proxyDecoderCC.setBufferingUpdateListener(null);
            proxyDecoderCC.setOnTimerUpdateListener(null);
        }
    }

    protected abstract void initView();

    public void addEventProducer(AbsEventProducer producer) {
        if (busPlayerView != null) {
            busPlayerView.addEventProducer(producer);
        }
    }

    public void removeEventProducer(AbsEventProducer producer) {
        if (busPlayerView != null) {
            busPlayerView.removeEventProducer(producer);
        }
    }

    @Override
    public void setAVOptions(AVOptions avOptions) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setAVOptions(avOptions);
        }
    }

    /**
     * 如果存在下一条数据 播放完成后，自动播放下一个
     *
     * @param autoNext
     */
    @Override
    public void autoPlayNext(boolean autoNext) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.autoPlayNext(autoNext);
        }
    }

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
     * 可以设置视图组 添加视图的拦截规则
     *
     * @param filter 拦截器
     */
    public void setCoverGroupFilter(ICoverGroup.OnCoverFilter filter) {
        if (busPlayerView != null) {
            busPlayerView.setCoverFilter(filter);
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

    private int decoderId;

    /**
     * 视频还没有开始播放时，选择解码器
     *
     * @param decoderId
     * @return
     */
    public boolean switchDecoder(int decoderId) {
        this.decoderId = decoderId;
        return false;
    }

    /**
     * 根据解码器ID，动态切换解码器组件，视频正在播放中时切换调用此方法
     * 如果当前的view，想要使用其他的解码器，可调用此方法进行切换.
     * 解码器必须实现此类 {@link AbsDecoderCC}
     * 同时要在Application 中初始化 {@link PrimPlayerConfig#configBuild()} 想要切换的解码器，否则会找不到相关的解码器
     *
     * @param decoderId 解码器组件ID
     */
    @Override
    public boolean dynamicChangedDecoder(int decoderId) {
        if (proxyDecoderCC != null) {
            PrimLog.e(TAG, "dynamicChangedDecoder --> decoderId:" + decoderId);
            boolean switchDecoder = proxyDecoderCC.switchDecoder(decoderId);
            if (switchDecoder) {
                releaseRender();
                setRenderView(mRenderType);
            }
            return switchDecoder;
        }
        PrimLog.e(TAG, "proxyDecoderCC == null dynamicChangedDecoder invalid");
        return false;
    }

    public IDecoder getDecoder() {
        return proxyDecoderCC;
    }

    /**
     * 设置数据提供者-一组播放数据
     *
     * @param provider
     */
    public void setDataProvider(AbsDataProvider provider) {
        this.dataProvider = provider;
        openVideo(provider);
    }

    /**
     * 设置播放资源数据
     *
     * @param dataSource 资源数据
     */
    @Override
    public void setDataSource(PlayerSource dataSource) {
        this.playerSource = dataSource;
        openVideo(dataSource);
    }

    public void updateSource(PlayerSource source) {
        this.playerSource = source;
    }

    protected void openVideo(AbsDataProvider provider) {
        proxyDecoderCC.setDataProvider(provider);
        bindSurfaceHolder(proxyDecoderCC, mSurfaceHolder);
    }

    protected void openVideo(PlayerSource dataSource) {
        proxyDecoderCC.setDataSource(dataSource);
        bindSurfaceHolder(proxyDecoderCC, mSurfaceHolder);
    }

    public void setDataProviderListener(IDataProvider.OnDataProviderListener onDataProviderListener) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setDataProviderListener(onDataProviderListener);
        }
    }

    public void setLoopMode(int mode) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setLoopMode(mode);
        }
    }

    protected void releaseRender() {
        if (mRenderView != null) {
            mRenderType = IRenderView.TEXTURE_VIEW;
            mRenderView.release();
            mRenderView = null;
        }
    }

    @Override
    public void releaseSurface() {
        if (mRenderView != null) {
            mRenderView.release();
        }
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
                .addCover(CoverEventKey.DEFAULT_LOAD_COVER, new DefaultLoadCover(getContext()))
                .addCover(CoverEventKey.DEFAULT_CONTROL_COVER, new DefaultControlCover(getContext()))
                .addCover(CoverEventKey.DEFAULT_ERROR_COVER, new DefaultErrorCover(getContext()))
                .addCover(CoverEventKey.DEFAULT_COMPLETE_COVER, new DefaultCompleteCover(getContext()));
        setCoverGroup(CoverCCManager.getInstance().getCoverGroup());
    }


    @Override
    public void setRenderView(int type) {
        this.mRenderType = type;
        PrimLog.e(TAG, "renderView:" + type);
        requestLayout();
        invalidate();
        releaseRender();
        initRender(type);
    }

    private void initRender(int type) {
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
                    renderTextureView.setVideoSampleAspectRatio(proxyDecoderCC.getVideoSarNum(), proxyDecoderCC.getVideoSarDen());
                    renderTextureView.setAspectRatio(AR_ASPECT_FIT_PARENT);
                }
                addRenderView(renderTextureView);
                break;
            case IRenderView.ASSIST_VIEW:
                AssistTextureView assistTextureView = new AssistTextureView(getContext());
                if (proxyDecoderCC != null) {
                    assistTextureView.getSurfaceHolder().bindToMediaPlayer(proxyDecoderCC);
                    assistTextureView.updateRenderSize(proxyDecoderCC.getVideoWidth(), proxyDecoderCC.getVideoHeight());
                    assistTextureView.setVideoSampleAspectRatio(proxyDecoderCC.getVideoSarNum(), proxyDecoderCC.getVideoSarDen());
                    assistTextureView.setAspectRatio(AR_ASPECT_FIT_PARENT);
                }
                addRenderView(assistTextureView);
                break;
            default:
                Log.e(TAG, "invalid render");
                break;
        }
    }

    private int mCurrentAspectRatio = AR_ASPECT_FIT_PARENT;

    public void updateRenderSize() {
        if (mRenderView != null) {
            mRenderView.updateRenderSize(proxyDecoderCC.getVideoWidth(), proxyDecoderCC.getVideoHeight());
            mRenderView.setVideoSampleAspectRatio(proxyDecoderCC.getVideoSarNum(), proxyDecoderCC.getVideoSarDen());
        }
    }


    private void addRenderView(IRenderView renderView) {
        if (renderView == null) {
            return;
        }
        if (mRenderView != null) {
            proxyDecoderCC.setDisplay(null);
            proxyDecoderCC.setSurface(null);
            mRenderView.removeRenderCallback(mRCallback);
            mRenderView = null;
        }
        this.mRenderView = renderView;
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            this.mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
        }
        if (mVideoSarNum > 0 && mVideoSarDen > 0) {
            this.mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
        }

        this.mRenderView.setRenderCallback(mRCallback);

        //设置rotation
        this.mRenderView.setVideoRotation(mVideoRotationDegree);
        //添加到view中
        this.busPlayerView.setRenderView(mRenderView);
    }

    IRenderView.IRenderCallback mRCallback = new IRenderView.IRenderCallback() {

        @Override
        public void surfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width, int height) {
            if (holder.getRenderView() != mRenderView) {
                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
                return;
            }
            mSurfaceHolder = holder;
            if (proxyDecoderCC != null) {
                bindSurfaceHolder(proxyDecoderCC, holder);
            }
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
            if (proxyDecoderCC != null) {
                proxyDecoderCC.setDisplay(null);
            }
        }
    };

    protected void bindSurfaceHolder(IDecoder decoder, IRenderView.ISurfaceHolder holder) {
        if (decoder == null) {
            PrimLog.e("PRIM!!", "decoder is null");
            return;
        }
        if (holder == null) {
            PrimLog.e("PRIM!!", "SurfaceHolder is null setRenderView");
//            decoder.setDisplay(null);
            setRenderView(mRenderType);
            return;
        }
        PrimLog.e("PRIM!!", "bind surface holder --> " + holder);
        holder.bindToMediaPlayer(decoder);
    }

    private OnPlayerEventListener mOnPlayerEventListener;

    private OnErrorEventListener mOnErrorEventListener;

    private OnTimerUpdateListener mOnTimerUpdateListener;

    private OnCoverNativePlayerListener mOnCoverNativePlayerListener;

    public void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
        this.mOnPlayerEventListener = onPlayerEventListener;
    }

    public void setOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
        this.mOnErrorEventListener = onErrorEventListener;
    }

    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
        this.mOnTimerUpdateListener = onTimerUpdateListener;
    }

    public void setOnCoverNativePlayerListener(OnCoverNativePlayerListener onCoverNativePlayerListener) {
        this.mOnCoverNativePlayerListener = onCoverNativePlayerListener;
    }

    //----------------- 播放监听相关 -------------------//

    OnPlayerEventListener playerEventListener = new OnPlayerEventListener() {
        @Override
        public void onPlayerEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
//                case PlayerEventCode.PRIM_PLAYER_EVENT_PANDERING_START:
//                    Log.e(TAG, "onPlayerEvent: 设置背景为黑色");
//                    busPlayerView.setBackgroundColor(Color.BLACK);
//                    break;
//                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARING:
//                case PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE:
//                case PlayerEventCode.PRIM_PLAYER_EVENT_STOP:
//                    PrimLog.e(TAG, "onPlayerEvent: 设置背景为透明");
//                    busPlayerView.setBackgroundColor(Color.TRANSPARENT);
//                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE:
                    mVideoWidth = bundle.getInt(EventCodeKey.PLAYER_VIDEO_WIDTH);
                    mVideoHeight = bundle.getInt(EventCodeKey.PLAYER_VIDEO_HEIGHT);
                    mVideoSarDen = bundle.getInt(EventCodeKey.PLAYER_VIDEO_SAR_DEN);
                    mVideoSarNum = bundle.getInt(EventCodeKey.PLAYER_VIDEO_SAR_NUM);
                    if (mVideoWidth != 0 && mVideoHeight != 0) {
                        if (mRenderView != null) {
                            PrimLog.e(TAG, "视频大小改变:mVideoWidth --> " + mVideoWidth + " mVideoHeight--> " + mVideoHeight);
                            mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
                            mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
                        }
                        requestLayout();
                    }
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_ROTATION_CHANGED:
                    int anInt = bundle.getInt(EventCodeKey.PLAYER_INFO_EXTRA);
                    mVideoRotationDegree = anInt;
                    PrimLog.e(TAG, "视频角度:mVideoRotationDegree --> " + mVideoRotationDegree);
                    if (mRenderView != null) {
                        mRenderView.setVideoRotation(anInt);
                    }
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
                    if (bundle != null) {
                        mVideoWidth = bundle.getInt(EventCodeKey.PLAYER_VIDEO_WIDTH);
                        mVideoHeight = bundle.getInt(EventCodeKey.PLAYER_VIDEO_HEIGHT);
                        if (mVideoHeight != 0 && mVideoWidth != 0) {
                            if (mRenderView != null) {
                                mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
                                mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
                            }
                        }
                    }
                    break;
                case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                    //视频播放完成清楚屏幕常亮设置
//                    clear_sreen_on();
//                    busPlayerView.setBackgroundColor(Color.TRANSPARENT);
                    break;
                default:
                    break;
            }
            PrimLog.e("PRIM!!", "playEventCode -> " + eventCode);
            if (busPlayerView != null) {
                busPlayerView.dispatchPlayEvent(eventCode, bundle);
            }

            if (mOnPlayerEventListener != null) {
                mOnPlayerEventListener.onPlayerEvent(eventCode, bundle);
            }
        }
    };

    OnErrorEventListener errorListener = new OnErrorEventListener() {
        @Override
        public boolean onError(Bundle bundle, int errorCode) {
            if (bundle != null) {
                //下发错误事件
                int framework_err = bundle.getInt("framework_err");
                int extra = bundle.getInt("extra");
                PrimLog.d(TAG, "onError: -> " + framework_err + " extra : " + extra);
            }
            if (busPlayerView != null) {
                busPlayerView.dispatchErrorEvent(errorCode, bundle);
            }
            if (mOnErrorEventListener != null) {
                mOnErrorEventListener.onError(bundle, errorCode);
            }
            return true;
        }
    };

    OnBufferingUpdateListener bufferingUpdateListener = new OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(Bundle bundle, int buffer) {
            if (busPlayerView != null) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt(EventCodeKey.PLAYER_BUFFER, buffer);
                busPlayerView.dispatchPlayEvent(PlayerEventCode.PRIM_PLAYER_EVENT_BUFFER_UPDATE, bundle1);
            }
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
            if (mOnTimerUpdateListener != null) {
                mOnTimerUpdateListener.onUpdate(current, duration, bufferPercentage);
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
                    start();
                    break;
                case CoverEventCode.COVER_EVENT_STOP:
                    stop();
                    break;
                case CoverEventCode.COVER_EVENT_FULL_SCREEN:
                    openFullScreen();
                    break;
                case CoverEventCode.COVER_EVENT_VERTICAL_SCREEN:
                    openVerticalScreen();
                    break;
                case CoverEventCode.COVER_EVENT_CANCEL_AUTO_PLAY_NEXT:
                    if (proxyDecoderCC != null) {
                        proxyDecoderCC.removePlayNext();
                    }
                    break;
                default:
                    break;
            }
            PrimLog.e(TAG, "OnCoverNativePlayerListener --> code:" + eventCode + " listener -> " + mOnCoverNativePlayerListener);
            if (mOnCoverNativePlayerListener != null) {
                mOnCoverNativePlayerListener.onEvent(eventCode, bundle);
            }
            if (busPlayerView != null) {
                busPlayerView.dispatchCoverNativeEvent(eventCode, bundle);
            }
        }
    };

    public boolean isFullScreen() {
        int requestedOrientation = Tools.scanForActivity(getContext()).getRequestedOrientation();
        return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    }

    protected void openVerticalScreen() {
        if (getContext() instanceof Application) {
            return;
        }
        PrimLog.e(TAG, "openVerticalScreen");
        Tools.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Activity activity = Tools.scanForActivity(getContext());
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        viewGroup.removeView(busPlayerView);
        Window window = Tools.scanForActivity(getContext()).getWindow();
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ViewParent parent = busPlayerView.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup viewgroup = (ViewGroup) parent;
            viewgroup.removeView(busPlayerView);
        }
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(busPlayerView, params);
    }

    protected void openFullScreen() {
        if (getContext() instanceof Application) {
            return;
        }
        PrimLog.e(TAG, "openFullScreen");
        Tools.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Window window = Tools.scanForActivity(getContext()).getWindow();
        if (window != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ViewGroup viewGroup = (ViewGroup) (Tools.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);
        View fullView = viewGroup.findViewById(R.id.video_fullscreen_id);
        if (fullView != null) {
            viewGroup.removeView(fullView);
        }
        try {
            ViewParent parent = busPlayerView.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewgroup = (ViewGroup) parent;
                viewgroup.removeView(busPlayerView);
            }
            busPlayerView.setId(R.id.video_fullscreen_id);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            viewGroup.addView(busPlayerView, lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isFullScreen() && keyCode == KeyEvent.KEYCODE_BACK) {
            if (busPlayerView != null) {
                busPlayerView.dispatchPlayEvent(PlayerEventCode.PRIM_PLAYER_EVENT_FULL_VERTICAL, null);
            }
            PrimLog.e(TAG, "onKeyDown is full screen");
            openVerticalScreen();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //------------------ 播放控制相关 -------------------//

    public void autoNextPlayWaitTime(int time) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.autoNextPlayWaitTime(time);
        }
    }

    /**
     * 开始播放
     */
    @Override
    public void start() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.start();
        }
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.pause();
        }
    }

    @Override
    public void rePlay() {
        if (null != playerSource) {
            setDataSource(playerSource);
        }
    }

    /**
     * 销毁播放器
     */
    @Override
    public void destroy() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.destroy();
        }
        if (busPlayerView != null) {
            busPlayerView.destroy();
        }
        _resetListener();
    }

    @Override
    public void stop() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.stop();
        }
    }

    /**
     * 获取播放状态
     *
     * @return {@link Status}
     */
    @Override
    public int getState() {
        if (proxyDecoderCC != null) {
            return proxyDecoderCC.getState();
        }
        return Status.STATE_IDEL;
    }

    /**
     * 是否正在播放中
     *
     * @return true 正在播放 false 没有播放
     */
    @Override
    public boolean isPlaying() {
        return proxyDecoderCC != null && proxyDecoderCC.isPlaying();
    }

    /**
     * 获取当前到播放进度
     *
     * @return long
     */
    @Override
    public long getCurrentPosition() {
        if (proxyDecoderCC != null) {
            return proxyDecoderCC.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(long msec) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.seek(msec);
        }
    }

    /**
     * 获取总到播放进度
     *
     * @return long
     */
    @Override
    public long getDuration() {
        if (proxyDecoderCC != null) {
            return proxyDecoderCC.getDuration();
        }
        return -1;
    }

    /**
     * 获取缓存进度
     *
     * @return long
     */
    @Override
    public int getBufferPercentage() {
        if (proxyDecoderCC != null) {
            return proxyDecoderCC.getBufferPercentage();
        }
        return 0;
    }

    private boolean mCanPause = true;

    private boolean mCanSeekBackward = true;

    private boolean mCanSeekForward = true;

    @Override
    public boolean canPause() {
        return mCanPause;
    }

    @Override
    public boolean canSeekBackward() {
        return mCanSeekBackward;
    }

    @Override
    public boolean canSeekForward() {
        return mCanSeekForward;
    }

    /**
     * 设置声音
     *
     * @param left
     * @param right
     */
    @Override
    public void setVolume(float left, float right) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setVolume(left, right);
        }
    }

    /**
     * 设置播放速度
     *
     * @param m
     */
    @Override
    public void setSpeed(float m) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.setSpeed(m);
        }
    }

    @Override
    public boolean hasForward() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.hasForward();
        }
        return false;
    }

    @Override
    public boolean hasNext() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.hasNext();
        }
        return false;
    }

    @Override
    public void playerForward() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.playerForward();
        }
    }

    @Override
    public void playerNext() {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.playerNext();
        }
    }

    @Override
    public AbsDataProvider getDataProvider() {
        return dataProvider;
    }

    @Override
    public PlayerSource getPlayerSource() {
        return playerSource;
    }

    @Override
    public void selectDataPlay(int position) {
        if (proxyDecoderCC != null) {
            proxyDecoderCC.selectDataPlay(position);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindSurfaceHolder(proxyDecoderCC, mSurfaceHolder);
                }
            }, 5000);
        }
    }

    @Override
    public int currentPlayIndex() {
        if (proxyDecoderCC != null) {
            return proxyDecoderCC.currentPlayIndex();
        }
        return -1;
    }

    @Override
    public void setVideoRotation(int degree) {
        if (mRenderView != null) {
            mRenderView.setVideoRotation(degree);
        }
    }

    @Override
    public void setDiaplayAspectRatio(int ratio) {
        this.mCurrentAspectRatio = ratio;
        if (mRenderView != null) {
            mRenderView.setAspectRatio(ratio);
        }
    }

    //------------------- 手势设置 -----------------------//

    /**
     * 设置是否开启手势
     *
     * @param gesture
     */
    public void setGesture(boolean gesture) {
        if (busPlayerView != null) {
            busPlayerView.setGesture(gesture);
        }
    }

    /**
     * 设置是否开启滑动手势
     *
     * @param scrollGesture
     */
    public void setScrollGesture(boolean scrollGesture) {
        if (busPlayerView != null) {
            busPlayerView.setScrollGesture(scrollGesture);
        }
    }
}
