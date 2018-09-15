package com.prim_player_cc.decoder_cc.listener;

import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc player prepared listener,Mainly used for some operations after
 * video playback preparation is completed
 * @time 2018/7/24 - 下午3:14
 */
public interface OnPreparedListener {
    void onPrepared(Bundle bundle, int i);
}
