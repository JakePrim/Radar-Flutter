package com.prim_player_cc.cover_cc.event;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图事件分发接口
 * @time 2018/9/15 - 下午5:09
 */
public interface IEventDispatcher {

    void dispatchPlayEvent(int eventCode, Bundle bundle);//分发播放事件

    void dispatchErrorEvent(int eventCode, Bundle bundle);//分发错误事件



    //------------------------ 分发手势事件 ------------------------//

    boolean dispatchOnSingleTapUp(MotionEvent event);//分发点击事件

    boolean dispatchOnDoubleTap(MotionEvent event);//分发双击事件

    boolean dispatchOnDown(MotionEvent event);//分发按下事件

    boolean dispatchOnScroll(MotionEvent e1,MotionEvent e2,float dX,float dY);//分发滚动事件

}
