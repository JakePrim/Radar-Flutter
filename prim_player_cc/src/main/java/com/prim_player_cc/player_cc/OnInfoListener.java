package com.prim_player_cc.player_cc;

import android.os.Bundle;

/**
 * @author prim
 * @version 1.0.0
 * @desc Mainly used to monitor whether is first frame of the video picture is displayed
 * on the screen ,and whether the buffer ,video angle changes,etc.
 * @time 2018/7/24 - 下午3:22
 */
public interface OnInfoListener {
    boolean onInfo(Bundle bundle, int var1, int var2);
}
