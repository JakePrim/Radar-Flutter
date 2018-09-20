package com.prim_player_cc.status;

import com.prim_player_cc.decoder_cc.IDecoder;

/**
 * @author prim
 * @version 1.0.0
 * @desc 播放状态
 * @time 2018/7/24 - 下午2:35
 */
public class Status {
    /**
     * play prepared state
     */
    public static final int STATE_PREPARED = 0;

    /**
     * start play state {@link IDecoder#start()}
     */
    public static final int STATE_START = 1;

    /**
     * pause play state {@link IDecoder#pause()}
     */
    public static final int STATE_PAUSE = 2;

    /**
     * stop play state {@link IDecoder#stop()}
     */
    public static final int STATE_STOP = 3;

    /**
     * complete play state
     */
    public static final int STATE_COMPLETE = 4;

    /**
     * play error state
     */
    public static final int STATE_ERROR = -1;

    /**
     * idel player state
     */
    public static final int STATE_IDEL = -2;

    /**
     * {@link IDecoder#destroy()}
     */
    public static final int STATE_END = -3;

    /**
     * play init state
     */
    public static final int STATE_INIT = -4;
}
