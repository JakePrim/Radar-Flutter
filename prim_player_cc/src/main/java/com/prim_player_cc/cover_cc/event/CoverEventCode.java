package com.prim_player_cc.cover_cc.event;

/**
 * @author prim
 * @version 1.0.0
 * @desc Cover 视图发送的事件码 通过该事件码 和解码器进行桥接
 * @time 2018/7/26 - 下午3:42
 */
public class CoverEventCode {
    public static final int COVER_EVENT_PAUSE = 800;

    public static final int COVER_EVENT_RESUME = 682;

    public static final int COVER_EVENT_RESET = 780;

    public static final int COVER_EVENT_SEEK = 819;

    public static final int COVER_EVENT_STOP = 674;

    public static final int COVER_EVENT_START = 267;
}
