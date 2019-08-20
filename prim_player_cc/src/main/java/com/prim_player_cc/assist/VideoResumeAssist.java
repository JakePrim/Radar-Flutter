//package com.prim_player_cc.assist;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//
//import com.prim_player_cc.R;
//import com.prim_player_cc.cover_cc.ICoverGroup;
//import com.prim_player_cc.cover_cc.event.CoverEventCode;
//import com.prim_player_cc.cover_cc.event.CoverEventKey;
//import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
//import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
//import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
//import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
//import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
//import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
//import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
//import com.prim_player_cc.log.PrimLog;
//import com.prim_player_cc.render_cc.IRenderView;
//import com.prim_player_cc.render_cc.RenderSurfaceView;
//import com.prim_player_cc.render_cc.RenderTextureView;
//import com.prim_player_cc.source_cc.AbsDataProvider;
//import com.prim_player_cc.source_cc.IDataProvider;
//import com.prim_player_cc.source_cc.PlayerSource;
//import com.prim_player_cc.status.Status;
//import com.prim_player_cc.utils.Tools;
//import com.prim_player_cc.view.BusPlayerView;
//import com.prim_player_cc.view.OnCoverNativePlayerListener;
//
//import static com.prim_player_cc.render_cc.IRenderView.AR_ASPECT_FIT_PARENT;
//
///**
// * @author prim
// * @version 1.0.0
// * @desc 视频无缝续播助手
// * @time 2018/11/5 - 7:17 PM
// */
//public class VideoResumeAssist implements IAssistPlay {
//    private Context context;
//    private ProxyDecoderCC proxyDecoderCC;
//    private BusPlayerView busPlayerView;
//
//    private ICoverGroup coverGroup;
//
//    private int mRenderType = IRenderView.TEXTURE_VIEW;
//
//    private boolean mRenderChange;
//
//    private IRenderView mRenderView;
//
//    private PlayerSource mPlayerSource;
//    private int mVideoWidth;
//    private int mVideoHeight;
//    private int mVideoSarNum;
//    private int mVideoSarDen;
//
//    private int mVideoRotation;
//
//    private OnPlayerEventListener mOnPlayerEventListener;
//
//    private OnErrorEventListener mOnErrorEventListener;
//
//    private OnCoverNativePlayerListener mOnCoverNativePlayerListener;
//
//    private OnTimerUpdateListener mOnTimerUpdateListener;
//
//    private IRenderView.ISurfaceHolder surfaceHolder;
//
//    public VideoResumeAssist(Context context) {
//        this(context, null);
//    }
//
//    public VideoResumeAssist(Context context, BusPlayerView busPlayerView) {
//        this.context = context;
//        if (busPlayerView == null) {
//            busPlayerView = new BusPlayerView(context);
//        }
//        this.busPlayerView = busPlayerView;
//        this.proxyDecoderCC = new ProxyDecoderCC();
//    }
//
//    public BusPlayerView getBusPlayerView() {
//        return busPlayerView;
//    }
//
//    private void attachPlayListener() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.setPlayerEventListener(mAssistOnPlayerEventListener);
//            proxyDecoderCC.setOnErrorEventListener(mAssistOnErrorEventListener);
//            proxyDecoderCC.setOnTimerUpdateListener(onTimerUpdateListener);
//            proxyDecoderCC.setBufferingUpdateListener(bufferingUpdateListener);
//        }
//        busPlayerView.setOnCoverNativePlayerListener(mAssistOnCoverNativeListener);
//
//    }
//
//    private void detachPlayListener() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.setPlayerEventListener(null);
//            proxyDecoderCC.setOnErrorEventListener(null);
//            proxyDecoderCC.setOnTimerUpdateListener(null);
//            proxyDecoderCC.setBufferingUpdateListener(null);
//        }
//        busPlayerView.setOnCoverNativePlayerListener(null);
//    }
//
//    private static final String TAG = "VideoResumeAssist";
//
//    OnPlayerEventListener mAssistOnPlayerEventListener = new OnPlayerEventListener() {
//        @Override
//        public void onPlayerEvent(int eventCode, Bundle bundle) {
//            if (mOnPlayerEventListener != null) {
//                mOnPlayerEventListener.onPlayerEvent(eventCode, bundle);
//            }
//            if (busPlayerView != null) {
//                busPlayerView.dispatchPlayEvent(eventCode, bundle);
//            }
//            handlePlayerEvent(eventCode, bundle);
//        }
//    };
//
//    private void handlePlayerEvent(int eventCode, Bundle bundle) {
//        switch (eventCode) {
//            case PlayerEventCode.PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE:
//                mVideoWidth = bundle.getInt(EventCodeKey.PLAYER_VIDEO_WIDTH);
//                mVideoHeight = bundle.getInt(EventCodeKey.PLAYER_VIDEO_HEIGHT);
//                mVideoSarDen = bundle.getInt(EventCodeKey.PLAYER_VIDEO_SAR_DEN);
//                mVideoSarNum = bundle.getInt(EventCodeKey.PLAYER_VIDEO_SAR_NUM);
//                if (mVideoWidth != 0 && mVideoHeight != 0) {
//                    if (mRenderView != null) {
//                        PrimLog.e(TAG, "视频大小改变:mVideoWidth --> " + mVideoWidth + " mVideoHeight--> " + mVideoHeight);
//                        mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
//                        mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
//                    }
//                }
//                break;
//            case PlayerEventCode.PRIM_PLAYER_EVENT_ROTATION_CHANGED:
//                int anInt = bundle.getInt(EventCodeKey.PLAYER_INFO_EXTRA);
//                mVideoRotation = anInt;
//                if (mRenderView != null) {
//                    mRenderView.setVideoRotation(anInt);
//                }
//                break;
//            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
//                PrimLog.e("PRIM!!", "视频准备完毕");
//                if (bundle != null && mRenderView != null) {
//                    mVideoWidth = bundle.getInt(EventCodeKey.PLAYER_VIDEO_WIDTH);
//                    mVideoHeight = bundle.getInt(EventCodeKey.PLAYER_VIDEO_HEIGHT);
//                    mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    OnErrorEventListener mAssistOnErrorEventListener = new OnErrorEventListener() {
//        @Override
//        public boolean onError(Bundle bundle, int errorCode) {
//            if (mOnErrorEventListener != null) {
//                mOnErrorEventListener.onError(bundle, errorCode);
//            }
//            if (busPlayerView != null) {
//                busPlayerView.dispatchErrorEvent(errorCode, bundle);
//            }
//            return true;
//        }
//    };
//
//    OnTimerUpdateListener onTimerUpdateListener = new OnTimerUpdateListener() {
//        @Override
//        public void onUpdate(long current, long duration, int bufferPercentage) {
//            if (busPlayerView != null) {
//                Bundle bundle = new Bundle();
//                bundle.putLong(EventCodeKey.PLAYER_CURRENT, current);
//                bundle.putLong(EventCodeKey.PLAYER_DURATION, duration);
//                bundle.putLong(EventCodeKey.PLAYER_BUFFER, bufferPercentage);
//                busPlayerView.dispatchPlayEvent(PlayerEventCode.PRIM_PLAYER_EVENT_TIMER_UPDATE, bundle);
//            }
//            if (mOnTimerUpdateListener != null) {
//                mOnTimerUpdateListener.onUpdate(current, duration, bufferPercentage);
//            }
//        }
//    };
//
//    OnBufferingUpdateListener bufferingUpdateListener = new OnBufferingUpdateListener() {
//        @Override
//        public void onBufferingUpdate(Bundle bundle, int buffer) {
//            if (busPlayerView != null) {
//                Bundle bundle1 = new Bundle();
//                bundle1.putInt(EventCodeKey.PLAYER_BUFFER, buffer);
//                busPlayerView.dispatchPlayEvent(PlayerEventCode.PRIM_PLAYER_EVENT_BUFFER_UPDATE, bundle1);
//            }
//        }
//    };
//
//    OnCoverNativePlayerListener mAssistOnCoverNativeListener = new OnCoverNativePlayerListener() {
//        @Override
//        public void onEvent(int eventCode, Bundle bundle) {
//            switch (eventCode) {
//                case CoverEventCode.COVER_EVENT_PAUSE:
//                    pause();
//                    break;
//                case CoverEventCode.COVER_EVENT_START:
//                    start();
//                    break;
//                case CoverEventCode.COVER_EVENT_STOP:
//                    stop();
//                    break;
//                case CoverEventCode.COVER_EVENT_SEEK:
//                    if (bundle != null) {
//                        long aLong = bundle.getLong(CoverEventKey.DEFAULT_SEEK);
//                        seekTo((int) aLong);
//                    }
//                    break;
//                case CoverEventCode.COVER_EVENT_FULL_SCREEN:
//                    openFullScreen();
//                    break;
//                case CoverEventCode.COVER_EVENT_VERTICAL_SCREEN:
//                    openVerticalScreen();
//                    break;
//                case CoverEventCode.COVER_EVENT_CANCEL_AUTO_PLAY_NEXT:
//                    if (proxyDecoderCC != null) {
//                        proxyDecoderCC.removePlayNext();
//                    }
//                    break;
//                default:
//                    break;
//            }
//            if (mOnCoverNativePlayerListener != null) {
//                mOnCoverNativePlayerListener.onEvent(eventCode, bundle);
//            }
//            if (busPlayerView != null) {
//                busPlayerView.dispatchCoverNativeEvent(eventCode, bundle);
//            }
//        }
//    };
//
//    public void updateRenderSize() {
//        if (mRenderView != null) {
//            mRenderView.updateRenderSize(proxyDecoderCC.getVideoWidth(), proxyDecoderCC.getVideoHeight());
//        }
//    }
//
//    public boolean isFullScreen() {
//        int requestedOrientation = Tools.scanForActivity(context).getRequestedOrientation();
//        return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
//                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
//    }
//
//    private void openVerticalScreen() {
//        PrimLog.e(TAG, "openVerticalScreen");
//        Tools.setRequestedOrientation(context, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ViewGroup viewGroup = (ViewGroup) (Tools.scanForActivity(context)).findViewById(Window.ID_ANDROID_CONTENT);
//        View fullView = viewGroup.findViewById(R.id.video_fullscreen_id);
//        if (fullView != null) {//移除视频播放的view
//            viewGroup.removeView(fullView);
//        }
//        Window window = Tools.scanForActivity(context).getWindow();
//        if (window != null) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//        ViewParent parent = busPlayerView.getParent();
//        if (parent instanceof ViewGroup) {
//            ViewGroup viewgroup = (ViewGroup) parent;
//            viewgroup.removeView(busPlayerView);
//        }
//        if (userContainer != null) {
//            userContainer.addView(busPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//        }
//    }
//
//    private void openFullScreen() {
//
//        PrimLog.e(TAG, "openFullScreen");
//
//        Tools.setRequestedOrientation(context, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        //全屏处理 将视频放到decorview 中去
//        Activity activity = Tools.scanForActivity(context);
//
////        ViewGroup viewGroup = (ViewGroup) (Tools.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);
//        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
//
//        View fullView = viewGroup.findViewById(R.id.video_fullscreen_id);
//
//        if (fullView != null) {
//            viewGroup.removeView(fullView);
//        }
//        try {
//            ViewParent parent = busPlayerView.getParent();
//            if (parent instanceof ViewGroup) {
//                ViewGroup viewgroup = (ViewGroup) parent;
//                viewgroup.removeView(busPlayerView);
//            }
//            busPlayerView.setId(R.id.video_fullscreen_id);
//            Window window = Tools.scanForActivity(context).getWindow();
//            if (window != null) {
//                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            }
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT);
//            viewGroup.addView(busPlayerView, lp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isAttachPlay() {
//        return proxyDecoderCC != null;
//    }
//
//    @Override
//    public void start() {
//        start(false);
//    }
//
//    public void start(boolean updateRender) {
//        if (updateRender) {
//            releaseRender();
//            updateRenderView();
//        }
//        if (mPlayerSource != null) {
//            proxyDecoderCC.setDataSource(mPlayerSource);
//            proxyDecoderCC.start();
//        }
//    }
//
//    @Override
//    public void pause() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.pause();
//        }
//    }
//
//    @Override
//    public void stop() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.stop();
//        }
//    }
//
//    @Override
//    public void reset() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.reset();
//        }
//    }
//
//    @Override
//    public void destory() {
//        detachPlayListener();
//        if (isAttachPlay()) {
//            proxyDecoderCC.destroy();
//        }
//        busPlayerView.destroy();
//        surfaceHolder = null;
//        releaseRender();
//        detachBusView();
//        setReceiverGroup(null);
//    }
//
//    @Override
//    public void resume() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.resume();
//        }
//    }
//
//    @Override
//    public void seekTo(int msc) {
//        if (isAttachPlay()) {
//            proxyDecoderCC.seek(msc);
//        }
//    }
//
//    @Override
//    public boolean isPlaying() {
//        if (isAttachPlay()) {
//            return proxyDecoderCC.isPlaying();
//        }
//        return false;
//    }
//
//    @Override
//    public int getState() {
//        if (isAttachPlay()) {
//            return proxyDecoderCC.getState();
//        }
//        return Status.STATE_IDEL;
//    }
//
//    @Override
//    public long getDuration() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.getDuration();
//        }
//        return -1;
//    }
//
//    @Override
//    public int getCurrentPosition() {
//        if (isAttachPlay()) {
//            proxyDecoderCC.getCurrentPosition();
//        }
//        return 0;
//    }
//
//    @Override
//    public void setPlaySource(PlayerSource source) {
//        this.mPlayerSource = source;
//    }
//
//    @Override
//    public boolean switchDecoder(int decoderId) {
//        boolean switchDecoder = proxyDecoderCC.switchDecoder(decoderId);
//        if (switchDecoder) {
//            releaseRender();
//        }
//        return switchDecoder;
//    }
//
//    private void releaseRender() {
//        if (mRenderView != null) {
//            mRenderType = IRenderView.TEXTURE_VIEW;
//            mRenderView.removeRenderCallback(mRCallback);
//            mRenderView.release();
//            mRenderView = null;
//        }
//    }
//
//    @Override
//    public void setVolume(float left, float right) {
//        if (isAttachPlay()) {
//            proxyDecoderCC.setVolume(left, right);
//        }
//    }
//
//    @Override
//    public void setSpeed(float speed) {
//        if (isAttachPlay()) {
//            proxyDecoderCC.setSpeed(speed);
//        }
//    }
//
//    @Override
//    public void setReceiverGroup(ICoverGroup coverGroup) {
//        this.coverGroup = coverGroup;
//    }
//
//    public ICoverGroup getCoverGroup() {
//        return coverGroup;
//    }
//
//    private ViewGroup userContainer;
//
//    @Override
//    public void attachContainer(ViewGroup userContainer) {
//        attachContainer(userContainer, false);
//    }
//
//    @Override
//    public void attachTextureView(RenderTextureView renderView) {
//        attachTextureView(renderView, false);
//    }
//
//    public void attachTextureView(RenderTextureView renderView, boolean b) {
//        //注册播放监听
//        attachPlayListener();
//        if (coverGroup != null) {
//            busPlayerView.setCoverGroup(coverGroup);
//        }
//        if (b) {
//            if (proxyDecoderCC != null) {
//                renderView.getSurfaceHolder().bindToMediaPlayer(proxyDecoderCC);
//                renderView.updateRenderSize(proxyDecoderCC.getVideoWidth(), proxyDecoderCC.getVideoHeight());
//                renderView.setVideoSampleAspectRatio(proxyDecoderCC.getVideoSarNum(), proxyDecoderCC.getVideoSarDen());
//                renderView.setAspectRatio(AR_ASPECT_FIT_PARENT);
//            }
//            addRenderView(renderView);
//        }
//    }
//
//    public void attachContainer(ViewGroup userContainer, boolean updateRender) {
//        attachPlayListener();
//        detachBusView();
//        if (coverGroup != null) {
//            busPlayerView.setCoverGroup(coverGroup);
//        }
//        if (updateRender || isNeedForceUpdateRender()) {
//            releaseRender();
//            updateRenderView();
//        }
//        if (userContainer != null) {
//            this.userContainer = userContainer;
//            userContainer.addView(busPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//        }
//    }
//
//    private void detachBusView() {
//        ViewParent parent = busPlayerView.getParent();
//        if (parent != null && parent instanceof ViewGroup) {
//            ((ViewGroup) parent).removeView(busPlayerView);
//        }
//    }
//
//
//    private boolean isNeedForceUpdateRender() {
//        return mRenderView == null || mRenderChange;
//    }
//
//    public void setRenderType(int type) {
//        mRenderChange = mRenderType != type;
//        this.mRenderType = type;
//        updateRenderView();
//    }
//
//    public IRenderView getRenderView() {
//        return mRenderView;
//    }
//
//    public int getRenderType() {
//        return mRenderType;
//    }
//
//    public void updateRenderView() {
//        PrimLog.e(TAG, "updateRenderView:" + mRenderType + "isNeedForceUpdateRender:" + isNeedForceUpdateRender());
//        if (isNeedForceUpdateRender()) {
//            mRenderChange = false;
//            releaseRender();
//            switch (mRenderType) {
//                case IRenderView.RENDER_NONE:
//                    addRenderView(null);
//                    break;
//                case IRenderView.SURFACE_VIEW:
//                    RenderSurfaceView renderSurfaceView = new RenderSurfaceView(context);
//                    addRenderView(renderSurfaceView);
//                    break;
//                case IRenderView.CUSTOM_VIEW:
//                    IRenderView renderView = proxyDecoderCC.getRenderView();
//                    addRenderView(renderView);
//                    break;
//                case IRenderView.TEXTURE_VIEW:
//                    RenderTextureView renderTextureView = new RenderTextureView(context);
//                    addRenderView(renderTextureView);
//                    break;
//                default:
//                    Log.e(TAG, "invalid render");
//                    break;
//            }
//        }
//    }
//
//    private void addRenderView(IRenderView renderView) {
//        if (renderView == null) {
//            return;
//        }
//        if (mRenderView != null) {
//            proxyDecoderCC.setDisplay(null);
//            proxyDecoderCC.setSurface(null);
//            mRenderView.removeRenderCallback(mRCallback);
//            mRenderView = null;
//        }
//        PrimLog.e(TAG, "addRenderView");
//        this.mRenderView = renderView;
//        if (mVideoWidth > 0 && mVideoHeight > 0) {
//            this.mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
//        }
//        if (mVideoSarNum > 0 && mVideoSarDen > 0) {
//            this.mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
//        }
//
//        this.mRenderView.setRenderCallback(mRCallback);
//
//        //设置rotation
//        this.mRenderView.setVideoRotation(mVideoRotation);
//        //添加到view中
//        this.busPlayerView.setRenderView(mRenderView);
//    }
//
//    private int mSurfaceHeight;
//    private int mSurfaceWidth;
//    IRenderView.IRenderCallback mRCallback = new IRenderView.IRenderCallback() {
//
//        @Override
//        public void surfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width, int height) {
//            if (holder.getRenderView() != mRenderView) {
//                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
//                return;
//            }
//            surfaceHolder = holder;
//            if (proxyDecoderCC != null) {
//                bindSurfaceHolder(proxyDecoderCC, holder);
//            }
//        }
//
//        @Override
//        public void surfaceChanged(@NonNull IRenderView.ISurfaceHolder holder, int format, int width, int height) {
//            if (holder.getRenderView() != mRenderView) {
//                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
//                return;
//            }
//            mSurfaceWidth = width;
//            mSurfaceHeight = height;
//        }
//
//        @Override
//        public void surfaceDestroyed(@NonNull IRenderView.ISurfaceHolder holder) {
//            if (holder.getRenderView() != mRenderView) {
//                PrimLog.e(TAG, "surfaceDestroyed :unmatched render callback");
//                return;
//            }
//            surfaceHolder = null;
//            proxyDecoderCC.setDisplay(null);
//        }
//    };
//
//    private void bindSurfaceHolder(ProxyDecoderCC proxyDecoderCC, IRenderView.ISurfaceHolder holder) {
//        if (proxyDecoderCC == null) {
//            return;
//        }
//        if (holder == null) {
//            proxyDecoderCC.setDisplay(null);
//            return;
//        }
//        PrimLog.e(TAG, "bindSurfaceHolder");
//        holder.bindToMediaPlayer(proxyDecoderCC);
//    }
//
//    @Override
//    public void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
//        this.mOnPlayerEventListener = onPlayerEventListener;
//
//    }
//
//    @Override
//    public void setOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
//        this.mOnErrorEventListener = onErrorEventListener;
//
//    }
//
//    @Override
//    public void setOnReceiverEventListener(OnCoverNativePlayerListener onCoverNativePlayerListener) {
//        this.mOnCoverNativePlayerListener = onCoverNativePlayerListener;
//    }
//
//    @Override
//    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
//        this.mOnTimerUpdateListener = onTimerUpdateListener;
//    }
//
//    @Override
//    public void setOnProviderListener(IDataProvider.OnDataProviderListener onProviderListener) {
//        proxyDecoderCC.setDataProviderListener(onProviderListener);
//    }
//
//    @Override
//    public void setDataProvider(AbsDataProvider dataProvider) {
//        proxyDecoderCC.setDataProvider(dataProvider);
//    }
//
//    @Override
//    public void rePlay(int msc) {
//        if (mPlayerSource != null) {
//            if (isAttachPlay()) {
//                proxyDecoderCC.setDataSource(mPlayerSource);
//                proxyDecoderCC.start(msc);
//            }
//        }
//    }
//}
