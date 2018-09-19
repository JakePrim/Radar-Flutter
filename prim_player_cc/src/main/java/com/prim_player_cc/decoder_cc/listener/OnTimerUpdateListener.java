package com.prim_player_cc.decoder_cc.listener;

/**
 * @author prim
 * @version 1.0.0
 * @desc 播放进度监听
 * @time 2018/9/18 - 上午11:30
 */
public interface OnTimerUpdateListener {
    void onUpdate(long current, long duration, int bufferPercentage);
}
