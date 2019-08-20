//package com.prim_player_cc.assist;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.prim_player_cc.config.ApplicationAttach;
//import com.prim_player_cc.cover_cc.ICoverGroup;
//import com.prim_player_cc.decoder_cc.listener.OnErrorEventListener;
//import com.prim_player_cc.decoder_cc.listener.OnPlayerEventListener;
//import com.prim_player_cc.source_cc.PlayerSource;
//import com.prim_player_cc.view.OnCoverNativePlayerListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author prim
// * @version 1.0.0
// * @desc 视频播放助手 动态添加播放器view {@link AssistPlayer 也是播放器助手->动态切换RenderView}
// * @time 2018/11/6 - 9:50 AM
// */
//public class VideoAssistPlayer {
//
//    private static VideoAssistPlayer assistPlayer;
//
//    private VideoResumeAssist mResumeAssist;
//
//    private Context mContext;
//
//    private PlayerSource source;
//
//    private List<OnPlayerEventListener> onPlayerEventListeners;
//
//    private List<OnErrorEventListener> onErrorEventListeners;
//
//    private List<OnCoverNativePlayerListener> onCoverNativePlayerListeners;
//
//
//    public static VideoAssistPlayer get() {
//        if (null == assistPlayer) {
//            synchronized (VideoAssistPlayer.class) {
//                if (null == assistPlayer) {
//                    assistPlayer = new VideoAssistPlayer();
//                }
//            }
//        }
//        return assistPlayer;
//    }
//
//    private VideoAssistPlayer() {
//        onPlayerEventListeners = new ArrayList<>();
//        onErrorEventListeners = new ArrayList<>();
//        onCoverNativePlayerListeners = new ArrayList<>();
//        this.mContext = ApplicationAttach.getApplicationContext();
//        if (mResumeAssist == null) {
//            mResumeAssist = new VideoResumeAssist(mContext);
//            mResumeAssist.getBusPlayerView().setBackgroundColor(Color.BLACK);
//        }
//    }
//
//    public VideoAssistPlayer with(Context context) {
//        this.mContext = context;
//        return this;
//    }
//
//    public VideoAssistPlayer setCoverGroup(ICoverGroup coverGroup) {
//        if (mResumeAssist != null) {
//            mResumeAssist.setReceiverGroup(coverGroup);
//        }
//        return this;
//    }
//
//    private ICoverGroup getCoverGroup() {
//        return mResumeAssist.getCoverGroup();
//    }
//
//    private PlayerSource getPlayerSource() {
//        return mPlayerSource;
//    }
//
//    private PlayerSource mPlayerSource;
//
//    public void play(ViewGroup container, PlayerSource source) {
//        this.mPlayerSource = source;
//        mResumeAssist.attachContainer(container, source == null);
//        if (source != null) {
//            mResumeAssist.setPlaySource(source);
//            mResumeAssist.start(true);
//            setVoice(isVoice);
//            mResumeAssist.updateRenderSize();
//        }
//    }
//
//    public View getVideoView() {
//        return mResumeAssist.getBusPlayerView();
//    }
//
//    public void setVoice(boolean isVoice) {
//        this.isVoice = isVoice;
//        if (isVoice) {
//            mResumeAssist.setVolume(1, 1);
//        } else {
//            mResumeAssist.setVolume(0, 0);
//        }
//    }
//
//    private boolean isVoice = false;
//
//    public boolean isVoice() {
//        return isVoice;
//    }
//
//    public boolean isPlaying() {
//        if (mResumeAssist != null) {
//            return mResumeAssist.isPlaying();
//        }
//        return false;
//    }
//
//    public int getState() {
//        return mResumeAssist.getState();
//    }
//
//    public void pause() {
//        if (mResumeAssist != null) {
//            mResumeAssist.pause();
//        }
//    }
//
//    public void resume() {
//        if (mResumeAssist != null) {
//            mResumeAssist.resume();
//        }
//    }
//
//    public void stop() {
//        if (mResumeAssist != null) {
//            mResumeAssist.stop();
//        }
//    }
//
//    public void reset() {
//        if (mResumeAssist != null) {
//            mResumeAssist.reset();
//        }
//    }
//
//    public void destroy() {
//        onPlayerEventListeners.clear();
//        onErrorEventListeners.clear();
//        onCoverNativePlayerListeners.clear();
//        mResumeAssist.destory();
//        assistPlayer = null;
//    }
//}
