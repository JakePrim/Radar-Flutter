package com.prim_player_cc.decoder_cc.listener;

import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc video player event listener
 * 自定义播放事件码 {@link com.prim_player_cc.decoder_cc.PlayerEventCode}
 * @time 2018/7/24 - 下午3:16
 */
public interface OnPlayerEventListener {

    void onPlayerEvent(int eventCode, Bundle bundle);
}
