package com.prim_player_cc.state;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/24 - 下午2:35
 */
public class State {
    public static final int STATE_PREPARED = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_STOP = 3;
    public static final int STATE_COMPLETE = 4;

    public static final int STATE_ERROR = -1;
    public static final int STATE_IDEL = -2;
    public static final int STATE_END = -3;
    public static final int STATE_INIT = -4;
}
