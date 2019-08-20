package com.prim_player_cc.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;
import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc 手势处理
 * @time 2018/9/19 - 下午2:28
 */
public class TouchGestureHandler extends GestureDetector.SimpleOnGestureListener {

    private OnCoverGestureListener onCoverGestureListener;

    private boolean isGesture = true;

    private boolean isScrollGesture = true;

    private boolean isVoiceGesture = false;

    private boolean isPositionGesture = false;

    private boolean isBrightnessGesture = false;

    private View atLocationView;

    public TouchGestureHandler(Context context, OnCoverGestureListener onCoverGestureListener, View view) {
        this.onCoverGestureListener = onCoverGestureListener;
        this.atLocationView = view;
    }

    void onDestory() {
        onCoverGestureListener = null;
        atLocationView = null;
        isGesture = true;
        isScrollGesture = true;
        isVoiceGesture = false;
        isPositionGesture = false;
        isBrightnessGesture = false;
    }

    public void setGesture(boolean gesture) {
        isGesture = gesture;
    }

    public void setScrollGesture(boolean scrollGesture) {
        PrimLog.e(TAG, "setScrollGesture:" + scrollGesture);
        isScrollGesture = scrollGesture;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (onCoverGestureListener != null) {
            onCoverGestureListener.onSingleTapUp(e);
        }
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (isGesture) {
            if (onCoverGestureListener != null) {
                onCoverGestureListener.onDown(e);
            }
        }
        return isGesture;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (onCoverGestureListener != null) {
            onCoverGestureListener.onDoubleTap(e);
        }
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        PrimLog.e(TAG, "onScroll" + isScrollGesture);
        if (isScrollGesture) {
            if (onCoverGestureListener != null) {
                onCoverGestureListener.onScroll(e1, e2, distanceX, distanceY);
            }
        }
        return isScrollGesture;
    }

    public void onTouchCancle() {
        if (onCoverGestureListener != null) {
            onCoverGestureListener.onTouchCancle();
        }
    }

    private static final String TAG = "TouchGestureHandler";
}
