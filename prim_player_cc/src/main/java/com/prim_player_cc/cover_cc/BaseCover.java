package com.prim_player_cc.cover_cc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.view.View;

import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.cover_cc.listener.OnCoverEventListener;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图的基类，所有到视图都继承此类
 * @time 2018/7/26 - 下午2:34
 */
public abstract class BaseCover implements ICover, ICoverOperate {
    private static final String TAG = "BaseCover";

    private View mCoverView;

    protected WeakReference<Context> weakContext;

    private WeakReference<OnCoverEventListener> onCoverEventListener;

    private ICoverGroup coverGroup;

    private int level = LEVEL_LOW;

    private String key;

    public BaseCover(Context context) {
        weakContext = new WeakReference<>(context);
        mCoverView = createCoverView(context);
    }

    protected abstract View createCoverView(Context context);

    @Override
    public void setCoverKey(String key) {
        this.key = key;
    }

    protected String getCoverKey() {
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
        if (mCoverView.getVisibility() != visibility) {
            mCoverView.setVisibility(visibility);
        }
    }

    @Override
    public int getCoverLevel() {
        return level;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return (T) mCoverView.findViewById(id);
    }

    //-------------------------------------- cover event ----------------------------------//

    @Override
    public void coverRequestPause() {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder 暂停视频");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_PAUSE, null);
    }

    @Override
    public void coverRequestReset() {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder reset视频");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_RESET, null);
    }

    @Override
    public void coverRequestResume() {
        PrimLog.d(TAG, "coverRequestPause 视图请求 decoder 继续播放视频");
        nativeCoverEvent(CoverEventCode.COVER_EVENT_RESUME, null);
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

    /**
     * 将视图组件的事件与播放器进行桥接
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

    /**
     * 设置低优先级 Cover
     *
     * @param p 0 - 30
     * @return 优先级
     */
    protected int setCoverLevelLow(@IntRange(from = 0, to = 30) int p) {
        level = LEVEL_LOW + p;
        return level;
    }

    /**
     * 设置中优先级 Cover
     *
     * @param p 31 - 61
     * @return 优先级
     */
    protected int setCoverLevelMiddle(@IntRange(from = 0, to = 30) int p) {
        level = LEVEL_MIDDLE + p;
        return level;
    }

    /**
     * 设置高优先级 Cover
     *
     * @param p 61 - 91
     * @return 优先级
     */
    protected int setCoverLevelHeight(@IntRange(from = 0, to = 30) int p) {
        level = LEVEL_HEIGHT + p;
        return level;
    }

    /**
     * {@link com.prim_player_cc.view.BasePlayerCCView} 将播放事件传递给视图组
     * 由视图组分发给各个视图，视图根据{@link PlayerEventCode} 播放事件码
     * 自行处理逻辑
     *
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {

    }
}
