package com.prim_player_cc.expand;

import android.os.Bundle;

import com.prim_player_cc.cover_cc.ICover;
import com.prim_player_cc.cover_cc.ICoverGroup;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/11/27 - 12:34 PM
 */
public interface DelegateEventSender {
    void sendEvent(int eventCode, Bundle bundle, ICoverGroup.OnCoverFilter filter);
}
