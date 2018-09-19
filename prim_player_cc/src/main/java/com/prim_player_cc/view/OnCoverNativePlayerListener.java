package com.prim_player_cc.view;

import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc 视图和解码器桥接的监听
 * @time 2018/9/19 - 下午3:30
 */
public interface OnCoverNativePlayerListener {
    void onEvent(int eventCode, Bundle bundle);
}
