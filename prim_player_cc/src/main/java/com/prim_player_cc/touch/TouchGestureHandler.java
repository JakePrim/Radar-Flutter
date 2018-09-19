package com.prim_player_cc.touch;

import android.accessibilityservice.GestureDescription;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;

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

    public TouchGestureHandler(OnCoverGestureListener onCoverGestureListener) {
        this.onCoverGestureListener = onCoverGestureListener;
    }

    public void setGesture(boolean gesture) {
        isGesture = gesture;
    }

    public void setScrollGesture(boolean scrollGesture) {
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
        if (onCoverGestureListener != null) {
            onCoverGestureListener.onDown(e);
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
        if (onCoverGestureListener != null) {
            onCoverGestureListener.onScroll(e1, e2, distanceX, distanceY);
        }
        return isScrollGesture;
    }
}
