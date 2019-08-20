//package com.prim_player_cc.assist;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.ViewGroup;
//
//import com.prim_player_cc.cover_cc.ICoverGroup;
//import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
//import com.prim_player_cc.decoder_cc.event_code.EventCode;
//import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
//import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
//import com.prim_player_cc.decoder_cc.listener.OnBufferingUpdateListener;
//import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
//import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
//import com.prim_player_cc.decoder_cc.listener.OnTimerUpdateListener;
//import com.prim_player_cc.expand.producer.NetworkEventProducer;
//import com.prim_player_cc.log.PrimLog;
//import com.prim_player_cc.render_cc.IRenderView;
//import com.prim_player_cc.render_cc.RenderTextureView;
//import com.prim_player_cc.source_cc.AbsDataProvider;
//import com.prim_player_cc.source_cc.IDataProvider;
//import com.prim_player_cc.source_cc.PlayerSource;
//import com.prim_player_cc.view.BusPlayerView;
//import com.prim_player_cc.view.OnCoverNativePlayerListener;
//
///**
// * @author prim
// * @version 1.0.0
// * @desc 动态添加video的辅助类
// * @time 2018/11/29 - 4:07 PM
// */
//public class ResumeHelper implements IAssistPlay {
//
//    private static final String TAG = "ResumeHelper";
//
//    private Context mContent;
//
//    private ProxyDecoderCC mPlayer;
//
//    private BusPlayerView mBusPlayerView;
//
//    private ICoverGroup mCoverGroup;
//
//    private int mRenderType = IRenderView.TEXTURE_VIEW;
//
//    private boolean mRenderChange;
//
//    private IRenderView mRenderView;
//
//    private int mVideoWidth;
//
//    private int mVideoHeight;
//
//    private int mVideoSarNum;
//
//    private int mVideoSarDen;
//
//    private int mVideoRotation;
//
//    private PlayerSource mSource;
//
//    private IRenderView.ISurfaceHolder mHolder;
//
//    private OnPlayerEventListener mOnPlayerEventListener;
//
//    private OnErrorEventListener mOnErrorEventListener;
//
//    private OnCoverNativePlayerListener mOnCoverNativePlayerListener;
//
//    private OnTimerUpdateListener mOnTimerUpdateListener;
//
//    private OnBufferingUpdateListener mOnBufferingUpdateListener;
//
//    public ResumeHelper(Context mContent) {
//        this(mContent, null);
//    }
//
//    public ResumeHelper(Context mContent, BusPlayerView busPlayerView) {
//        this.mContent = mContent;
//        this.mPlayer = new ProxyDecoderCC();
//        if (busPlayerView == null) {
//            busPlayerView = new BusPlayerView(mContent);
//        }
//        busPlayerView.addEventProducer(new NetworkEventProducer());
//        this.mBusPlayerView = busPlayerView;
//    }
//
//    public BusPlayerView getBusPlayerView() {
//        return mBusPlayerView;
//    }
//
//    private void attachPlayerListener() {
//        mPlayer.setPlayerEventListener(null);
//        mPlayer.setOnErrorEventListener(null);
//        mPlayer.setOnTimerUpdateListener(null);
//        mPlayer.setBufferingUpdateListener(null);
//        mBusPlayerView.setOnCoverNativePlayerListener(null);
//    }
//
//    private void detachPlayerListener() {
//        mPlayer.setPlayerEventListener(null);
//        mPlayer.setOnErrorEventListener(null);
//        mPlayer.setOnTimerUpdateListener(null);
//        mPlayer.setBufferingUpdateListener(null);
//        mBusPlayerView.setOnCoverNativePlayerListener(null);
//    }
//
//    private OnPlayerEventListener mInternalPlayerEventListener = new OnPlayerEventListener() {
//        @Override
//        public void onPlayerEvent(int eventCode, Bundle bundle) {
//            onHandlePlayerEvent(eventCode, bundle);
//            if (mOnPlayerEventListener != null) {
//                mOnPlayerEventListener.onPlayerEvent(eventCode, bundle);
//            }
//            mBusPlayerView.dispatchPlayEvent(eventCode, bundle);
//        }
//    };
//
//    private void onHandlePlayerEvent(int eventCode, Bundle bundle) {
//        switch (eventCode) {
//            case PlayerEventCode
//                    .PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE:
//                if (bundle != null) {
//                    mVideoWidth = bundle.getInt(EventCodeKey.PLAYER_VIDEO_WIDTH);
//                    mVideoHeight = bundle.getInt(EventCodeKey.PLAYER_VIDEO_HEIGHT);
//                    mVideoSarDen = bundle.getInt(EventCodeKey.PLAYER_VIDEO_SAR_DEN);
//                    mVideoSarNum = bundle.getInt(EventCodeKey.PLAYER_VIDEO_SAR_NUM);
//                    if (mRenderView != null) {
//                        mRenderView.updateRenderSize(mVideoWidth, mVideoHeight);
//                        mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
//                    }
//                }
//                break;
//            case PlayerEventCode.PRIM_PLAYER_EVENT_ROTATION_CHANGED:
//                if (bundle != null) {
//                    int anInt = bundle.getInt(EventCodeKey.PLAYER_INFO_EXTRA);
//                    mVideoRotation = anInt;
//                    if (mRenderView != null) {
//                        mRenderView.setVideoRotation(anInt);
//                    }
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
//    @Override
//    public void start() {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void stop() {
//
//    }
//
//    @Override
//    public void reset() {
//
//    }
//
//    @Override
//    public void destory() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void seekTo(int msc) {
//
//    }
//
//    @Override
//    public boolean isPlaying() {
//        return false;
//    }
//
//    @Override
//    public int getState() {
//        return 0;
//    }
//
//    @Override
//    public long getDuration() {
//        return 0;
//    }
//
//    @Override
//    public int getCurrentPosition() {
//        return 0;
//    }
//
//    @Override
//    public void setPlaySource(PlayerSource source) {
//
//    }
//
//    @Override
//    public boolean switchDecoder(int decoderId) {
//        return false;
//    }
//
//    @Override
//    public void setVolume(float left, float right) {
//
//    }
//
//    @Override
//    public void setSpeed(float speed) {
//
//    }
//
//    @Override
//    public void setReceiverGroup(ICoverGroup receiverGroup) {
//
//    }
//
//    @Override
//    public void attachContainer(ViewGroup userContainer) {
//
//    }
//
//    @Override
//    public void attachTextureView(RenderTextureView renderView) {
//
//    }
//
//    @Override
//    public void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
//
//    }
//
//    @Override
//    public void setOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
//
//    }
//
//    @Override
//    public void setOnReceiverEventListener(OnCoverNativePlayerListener onReceiverEventListener) {
//
//    }
//
//    @Override
//    public void setOnTimerUpdateListener(OnTimerUpdateListener onTimerUpdateListener) {
//
//    }
//
//    @Override
//    public void setOnProviderListener(IDataProvider.OnDataProviderListener onProviderListener) {
//
//    }
//
//    @Override
//    public void setDataProvider(AbsDataProvider dataProvider) {
//
//    }
//
//    @Override
//    public void rePlay(int msc) {
//
//    }
//}
