package com.prim_player_cc.decoder_cc;

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
     * when decoder prepared
     */
    public static final int PRIM_PLAYER_EVENT_PREPARED = -1011;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#start()}
     */
    public static final int PRIM_PLAYER_EVENT_START = -1012;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#pause()}
     */
    public static final int PRIM_PLAYER_EVENT_PAUSE = -1013;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#resume()}
     */
    public static final int PRIM_PLAYER_EVENT_RESUME = -1014;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#stop()}
     */
    public static final int PRIM_PLAYER_EVENT_STOP = -1015;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#reset()}
     */
    public static final int PRIM_PLAYER_EVENT_RESET = -1016;

    /**
     * when you call {@link com.prim_player_cc.decoder_cc.IDecoder#destroy()}
     */
    public static final int PRIM_PLAYER_EVENT_DESTROY = -1017;

    /**
     * on player timer progress update
     */
    public static final int PRIM_PLAYER_EVENT_TIMER_UPDATE = -1018;

    /**
     * when decoder completion
     */
    public static final int PRIM_PLAYER_EVENT_COMPLETION = -1019;

    /**
     * when player status change {@link com.prim_player_cc.status.Status}
     */
    public static final int PRIM_PLAYER_EVENT_STATUS_CHANGE = -1020;
}
