package com.prim_player_cc.decoder_cc.listener;

import android.os.Bundle;

import com.prim_player_cc.decoder_cc.event_code.EventCode;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;

/**
 * @author prim
 * @version 1.0.0
 * @desc video player event listener
 * 自定义播放事件码 {@link PlayerEventCode}
 * @time 2018/7/24 - 下午3:16
 */
public interface OnPlayerEventListener {
    void onPlayerEvent(@EventCode int eventCode, Bundle bundle);
}
