package com.prim_player_cc.expand;

import android.os.Bundle;

import com.prim_player_cc.cover_cc.ICoverGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc 事件发送者，将扩展的事件发送给视图
 * @time 2018/11/27 - 11:42 AM
 */
public interface CoverEventSender {
    void sendEvent(int eventCode, Bundle bundle);

    void sendEvent(int eventCode, Bundle bundle, ICoverGroup.OnCoverFilter filter);
}
