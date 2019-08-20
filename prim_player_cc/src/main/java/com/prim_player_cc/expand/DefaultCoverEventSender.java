package com.prim_player_cc.expand;

import android.os.Bundle;

import com.prim_player_cc.cover_cc.ICoverGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认实现的事件发送者
 * @time 2018/11/27 - 11:46 AM
 */
public class DefaultCoverEventSender implements CoverEventSender {

    private DelegateEventSender delegateEventSender;

    public DefaultCoverEventSender(DelegateEventSender delegateEventSender) {
        this.delegateEventSender = delegateEventSender;
    }

    @Override
    public void sendEvent(int eventCode, Bundle bundle) {
        sendEvent(eventCode, bundle, null);
    }

    @Override
    public void sendEvent(int eventCode, Bundle bundle, ICoverGroup.OnCoverFilter filter) {
        if (delegateEventSender != null) {
            delegateEventSender.sendEvent(eventCode, bundle, filter);
        }
    }
}
