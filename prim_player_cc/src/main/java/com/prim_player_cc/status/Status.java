package com.prim_player_cc.status;

/**
 * @author prim
 * @version 1.0.0
 * @desc 播放状态
 * @time 2018/7/24 - 下午2:35
 */
public class Status {

    public static final int STATE_PREPARED = 0;

    public static final int STATE_PAUSE = 2;

    public static final int STATE_COMPLETE = 4;

    public static final int STATE_PREPARING = 5;

    public static final int STATE_ERROR = -1;

    public static final int STATE_IDEL = -2;

    public static final int STATE_END = -3;

    public static final int STATE_INIT = -4;

    public static final int STATE_START = -5;

    public static final int STATE_STOP = -6;
}
