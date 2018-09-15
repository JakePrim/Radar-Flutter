package com.prim_player_cc.decoder_cc.listener;

import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc video buffer update listener
 * @time 2018/7/24 - 下午3:32
 */
public interface OnBufferingUpdateListener {
    void onBufferingUpdate(Bundle bundle, int buffer);
}
