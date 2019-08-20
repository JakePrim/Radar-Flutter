package com.prim_player_cc.cover_cc.event;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;

import com.prim_player_cc.cover_cc.ICoverGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图事件分发接口
 * @time 2018/9/15 - 下午5:09
 */
public interface IEventDispatcher {

    void dispatchPlayEvent(int eventCode, Bundle bundle);//分发播放事件

    void dispatchErrorEvent(int eventCode, Bundle bundle);//分发错误事件

    void dispatchCoverNativeEvent(int eventCode, Bundle bundle);//视图之间的桥接事件

    void dispatchExpandEvent(int eventCode, Bundle bundle, ICoverGroup.OnCoverFilter filter);//分发扩展事件

    //------------------------ 分发手势事件 ------------------------//

    boolean dispatchOnSingleTapUp(MotionEvent event);//分发点击事件

    boolean dispatchOnDoubleTap(MotionEvent event);//分发双击事件

    boolean dispatchOnDown(MotionEvent event);//分发按下事件

    boolean dispatchOnScroll(MotionEvent e1, MotionEvent e2, float dX, float dY);//分发滚动事件

    void dispatchOnTouchCancle();

}
