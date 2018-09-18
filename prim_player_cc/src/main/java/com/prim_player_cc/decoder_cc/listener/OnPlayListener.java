package com.prim_player_cc.decoder_cc.listener;

import android.os.Bundle;

import com.prim_player_cc.state.PlayerState;

/**
 * @author prim
 * @version 1.0.0
 * @desc video play listener
 * 其他事件的监听
 * @time 2018/7/24 - 下午3:16
 */
public interface OnPlayListener {
    void onPlaying(int eventCode, Bundle bundle);
}
