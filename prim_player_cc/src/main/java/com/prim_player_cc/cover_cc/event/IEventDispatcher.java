package com.prim_player_cc.cover_cc.event;

import android.os.Build;
import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/9/15 - 下午5:09
 */
public interface IEventDispatcher {

    boolean dispatchPlayEvent(int eventCode, Bundle bundle);//播放事件

    boolean dispatchErrorEvent(int eventCode, Bundle bundle);//错误事件

}
