package com.prim_player_cc.cover_cc;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.cover_cc.listener.OnCoverEventListener;
import com.prim_player_cc.decoder_cc.IMediaController;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.utils.Tools;

import java.lang.ref.WeakReference;

import static android.view.View.NO_ID;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图的基类，所有到视图都继承此类
 * 视图可以通过 {@link IMediaController} 控制mediaplayer 的播放暂停等功能
 * 也可以通过{@link #nativeCoverEvent(int, Bundle)} 桥接方法控制mediaplayer 等播放暂停等事件，还可以通知到其他视图做相应的处理
 * 更加灵活控制
 * @time 2018/7/26 - 下午2:34
 */
public abstract class BaseCover implements ICover, ICoverOperate, View.OnClickListener {
    private static final String TAG = "BaseCover";

    public static final int TIME_OUT = 5000;

    public static final int WHAT_CONTROL_VISIBLE_GONE = 717;

    protected View mCoverView;

    protected WeakReference<Context> weakContext;

    private WeakReference<OnCoverEventListener> onCoverEventListener;

    private ICoverGroup coverGroup;

    private int level = LEVEL_LOW;

    private String key;

    protected IMediaController.MediaPlayerControl mediaPlayerControl;

    public BaseCover(Context context) {
        weakContext = new WeakReference<>(context);
        if (context != null) {
            mCoverView = createCoverView(context);
        }
        int[] ints = setCoverLevel();
        if (ints != null && ints.length > 1) {
            if (ints[0] == LEVEL_LOW) {
                setCoverLevelLow(ints[1]);
            } else if (ints[0] == LEVEL_MIDDLE) {
                setCoverLevelMiddle(ints[1]);
            } else if (ints[0] == LEVEL_HEIGHT) {
                setCoverLevelHeight(ints[1]);
            }
        }
    }

    public void updateContext(Context context) {
        weakContext = new WeakReference<>(context);
    }

    protected abstract View createCoverView(Context context);

    @Override
    public void setCoverKey(String key) {
        this.key = key;
    }

    @Override
    public String getCoverKey() {
        return key;
    }

    @Override
    public void bindCoverEventListener(OnCoverEventListener onCoverEventListener) {
        this.onCoverEventListener = new WeakReference<>(onCoverEventListener);
    }

    @Override
    public void unBindCoverEventListener() {
        if (onCoverEventListener != null && onCoverEventListener.get() != null) {
            onCoverEventListener.clear();
            onCoverEventListener = null;
        }
    }

    @Override
    public void bindCoverGroup(ICoverGroup coverGroup) {
        this.coverGroup = coverGroup;
    }

    @Override
    public ICoverGroup getCoverGroup() {
        return coverGroup;
    }

    @Override
    public View getCoverView() {
        return mCoverView;
    }

    @Override
    public void coverVisibility(int visibility) {
        if (mCoverView != null && mCoverView.getVisibility() != visibility) {
            mCoverView.setVisibility(visibility);
        }
    }

    @Override
    public int getCoverLevel() {
        return level;
    }

    @Override
    @Nullable
    public <T extends View> T findViewById(@IdRes int id) {
        if (id == NO_ID) {
            return null;
        }
        if (mCoverView != null) {
            return (T) mCoverView.findViewById(id);
        }
        return null;
    }

    //-------------------------------------- cover event 也可以通过mediaPlayerControl控制解码器 ----------------------------------//

    @Override
    public void coverRequestPause() {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder 暂停视频");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_PAUSE, null);
    }

    @Override
    public void coverRequestSeek(Bundle bundle) {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder 跳转到指定位置");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_SEEK, bundle);
    }

    @Override
    public void coverRequestStart() {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder 视频开始播放");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_START, null);
    }

    @Override
    public void coverRequestStop() {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder 停止播放");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_STOP, null);
    }

    //通知我要变成全屏了
    @Override
    public void coverRequestFullScreen() {
        nativeCoverEvent(CoverEventCode.COVER_EVENT_FULL_SCREEN, null);
    }

    //通知我要变成竖屏全屏
    @Override
    public void coverRequestVerticalFullScreen() {
        nativeCoverEvent(CoverEventCode.COVER_EVENT_VERTICAL_FULL_SCREEN, null);
    }

    //通知我要变成竖屏了
    @Override
    public void coverRequestVerticalScreen() {
        nativeCoverEvent(CoverEventCode.COVER_EVENT_VERTICAL_SCREEN, null);
    }

    /**
     * 视图发送事件
     * 可用于所有视图接收此事件，可通知播放器 方便更灵活处理
     *
     * @param code
     * @param bundle
     */
    public void nativeCoverEvent(int code, Bundle bundle) {
        if (onCoverEventListener != null && onCoverEventListener.get() != null) {
            onCoverEventListener.get().onEvent(code, bundle);
        }
    }

    //设置视图组件的优先级
    protected abstract int[] setCoverLevel();

    /**
     * 设置低优先级 Cover
     *
     * @param p 0 - 30
     * @return 优先级
     */
    private int setCoverLevelLow(@IntRange(from = 0, to = 30) int p) {
        level = LEVEL_LOW + p;
        return level;
    }

    /**
     * 设置中优先级 Cover
     *
     * @param p 31 - 61
     * @return 优先级
     */
    private int setCoverLevelMiddle(@IntRange(from = 0, to = 30) int p) {
        level = LEVEL_MIDDLE + p;
        return level;
    }

    /**
     * 设置高优先级 Cover
     *
     * @param p 61 - 91
     * @return 优先级
     */
    private int setCoverLevelHeight(@IntRange(from = 0, to = 30) int p) {
        level = LEVEL_HEIGHT + p;
        return level;
    }

    /**
     * {@link com.prim_player_cc.view.BasePlayerCCView} 将播放事件传递给视图组
     * 由视图组分发给各个视图{@link com.prim_player_cc.view.BusPlayerView#dispatchPlayEvent(int, Bundle)}，
     * 视图根据{@link PlayerEventCode} 播放事件码
     * 自行处理逻辑
     *
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {

    }

    /**
     * {@link com.prim_player_cc.view.BasePlayerCCView} 将播放事件传递给视图组
     * 由视图组分发给各个视图{@link com.prim_player_cc.view.BusPlayerView#dispatchErrorEvent(int, Bundle)}，
     * 视图根据错误码
     * 自行处理逻辑
     *
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    /**
     * {@link com.prim_player_cc.view.BasePlayerCCView} 将播放事件传递给视图组
     * 由视图组分发给各个视图{@link com.prim_player_cc.view.BusPlayerView#dispatchCoverNativeEvent(int, Bundle)} (int, Bundle)}，
     * 视图根据视图码
     * 自行处理逻辑
     *
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onCoverNativeEvent(int eventCode, Bundle bundle) {

    }

    /**
     * 自定义的扩展事件，允许用户自定义扩展事件(比如：监听网络变化等)
     *
     * @param eventCode 自定义事件码
     * @param bundle    传递的值
     */
    @Override
    public void onExpandEvent(int eventCode, Bundle bundle) {

    }

    /**
     * 表示视图被添加到视图组的回调方法
     */
    @Override
    public void onCoverBind() {

    }

    /**
     * 表示视图被视图组移除的回调方法
     */
    @Override
    public void onCoverUnBind() {

    }

    @Override
    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.mediaPlayerControl = mediaPlayerControl;
    }

    @Override
    public void show() {
        show(TIME_OUT);
    }

    @Override
    public void show(int timeout) {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mCoverView != null)
            mCoverView.setEnabled(enabled);
    }

    /**
     * 设置很随描点的view
     *
     * @param view 描点view
     */
    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void onClick(View v) {

    }

    protected void pauseOrResume() {
        if (mediaPlayerControl != null) {
            if (mediaPlayerControl.isPlaying()) {
                mediaPlayerControl.pause();
            } else {
                mediaPlayerControl.start();
            }
        }
    }

    protected boolean isFullScreen() {
        int requestedOrientation = Tools.scanForActivity(weakContext.get()).getRequestedOrientation();
        return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    }
}
