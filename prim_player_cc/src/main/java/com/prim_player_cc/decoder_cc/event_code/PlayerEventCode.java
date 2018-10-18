package com.prim_player_cc.decoder_cc.event_code;

import com.prim_player_cc.decoder_cc.IDecoder;

/**
 * @author prim
 * @version 1.0.0
 * @desc 自定义播放事件码
 * @time 2018/9/19 - 上午10:13
 */
public class PlayerEventCode {
    /**
     * when decoder set data source{@link com.prim_player_cc.source.PlayerSource}
     */
    public static final int PRIM_PLAYER_EVENT_DATA_SOURCE = -1010;

    /**
     * when decoder prepared listener
     */
    public static final int PRIM_PLAYER_EVENT_PREPARED = -1011;

    /**
     * when decoder videoSizeChanged listener
     */
    public static final int PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE = -1021;

    /**
     * when decoder info listener
     */
    public static final int PRIM_PLAYER_EVENT_INFO = -1022;

    /**
     * when decoder info seek complete listener
     */
    public static final int PRIM_PLAYER_SEEK_COMPLETE = -1023;

    /**
     * when decoder timed text listener
     */
    public static final int PRIM_PLAYER_TIMED_TEXT = -1024;

    /**
     * when decoder completion listener
     */
    public static final int PRIM_PLAYER_EVENT_COMPLETION = -1019;

    /**
     * when you call {@link IDecoder#start()}
     */
    public static final int PRIM_PLAYER_EVENT_START = -1012;

    /**
     * when you call {@link IDecoder#pause()}
     */
    public static final int PRIM_PLAYER_EVENT_PAUSE = -1013;

    /**
     * when you call {@link IDecoder#resume()}
     */
    public static final int PRIM_PLAYER_EVENT_RESUME = -1014;

    /**
     * when you call {@link IDecoder#stop()}
     */
    public static final int PRIM_PLAYER_EVENT_STOP = -1015;

    /**
     * when you call {@link IDecoder#reset()}
     */
    public static final int PRIM_PLAYER_EVENT_RESET = -1016;

    /**
     * when you call {@link IDecoder#release()}
     */
    public static final int PRIM_PLAYER_EVENT_RELEASE = -1025;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#destroy()}
     */
    public static final int PRIM_PLAYER_EVENT_DESTROY = -1017;

    /**
     * on player timer progress update
     * {@link com.prim_player_cc.decoder_cc.helper.TimerUpdateHelper}
     */
    public static final int PRIM_PLAYER_EVENT_TIMER_UPDATE = -1018;

    /**
     * when player status change {@link com.prim_player_cc.status.Status}
     */
    public static final int PRIM_PLAYER_EVENT_STATUS_CHANGE = -1020;
}
