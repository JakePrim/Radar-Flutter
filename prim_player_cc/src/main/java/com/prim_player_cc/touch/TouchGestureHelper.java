package com.prim_player_cc.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;

/**
 * @author prim
 * @version 1.0.0
 * @desc 手势触摸辅助类
 * @time 2018/9/19 - 下午2:50
 */
public class TouchGestureHelper {
    private TouchGestureHandler touchGestureHandler;

    private GestureDetector gestureDetector;

    public TouchGestureHelper(Context context, OnCoverGestureListener onCoverGestureListener, View view) {
        this.touchGestureHandler = new TouchGestureHandler(context, onCoverGestureListener, view);
        this.gestureDetector = new GestureDetector(context, touchGestureHandler);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (touchGestureHandler != null) {
                    touchGestureHandler.onTouchCancle();
                }
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 是否允许手势
     *
     * @param gesture
     */
    public void setGesture(boolean gesture) {
        touchGestureHandler.setGesture(gesture);
    }

    /**
     * 是否允许滑动手势
     *
     * @param scrollGesture
     */
    public void setScrollGesture(boolean scrollGesture) {
        touchGestureHandler.setScrollGesture(scrollGesture);
    }

    void onDestory() {
        if (touchGestureHandler != null) {
            touchGestureHandler.onDestory();
            touchGestureHandler = null;
        }
    }
}
