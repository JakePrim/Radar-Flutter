package com.prim_player_cc.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;

/**
 * @author prim
 * @version 1.0.0
 * @desc 手势触摸辅助类
 * @time 2018/9/19 - 下午2:50
 */
public class TouchGestureHelper {
    TouchGestureHandler touchGestureHandler;

    GestureDetector gestureDetector;

    public TouchGestureHelper(Context context, TouchGestureHandler touchGestureHandler) {
        this.touchGestureHandler = touchGestureHandler;
        gestureDetector = new GestureDetector(context, touchGestureHandler);
    }

    public boolean onTouch(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void setGesture(boolean gesture) {
        touchGestureHandler.setGesture(gesture);
    }

    public void setScrollGesture(boolean scrollGesture) {
        touchGestureHandler.setScrollGesture(scrollGesture);
    }
}
