package com.prim_player_cc.decoder_cc.listener;

import com.prim_player_cc.state.PlayerState;

/**
 * @author prim
 * @version 1.0.0
 * @desc video playing listener
 * @time 2018/7/24 - 下午3:16
 */
public interface OnPlayingListener {
    void onPlaying(long current, long duration, @PlayerState int state);
}
