package com.prim_player_cc.cover_cc.listener;

import android.view.MotionEvent;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图的手势监听，那个视图需要手势监听就实现此类
 * @time 2018/9/18 - 下午4:25
 */
public interface OnCoverGestureListener {
    boolean onSingleTapUp(MotionEvent event);//分发点击事件

    boolean onDoubleTap(MotionEvent event);//分发双击事件

    boolean onDown(MotionEvent event);//分发按下事件

    boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY);//分发滚动事件

    boolean onTouchCancle();//手指抬起 或取消
}
