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
     * when decoder set data source{@link com.prim_player_cc.source_cc.PlayerSource}
     */
    public static final int PRIM_PLAYER_EVENT_DATA_SOURCE = -1010;

    /**
     * when decoder preparing
     */
    public static final int PRIM_PLAYER_EVENT_PREPARING = -2010;

    /**
     * when decoder prepared listener
     */
    public static final int PRIM_PLAYER_EVENT_PREPARED = -1011;

    /**
     * when infoListener buffering end
     */
    public static final int PRIM_PLAYER_EVENT_BUFFERING_END = -3000;

    /**
     * when infoListener buffering start
     */
    public static final int PRIM_PLAYER_EVENT_BUFFERING_START = -3001;

    /**
     * when infoListener rotation changed
     */
    public static final int PRIM_PLAYER_EVENT_ROTATION_CHANGED = -3002;

    /**
     * when infoListener pandering start
     */
    public static final int PRIM_PLAYER_EVENT_PANDERING_START = -3003;

    /**
     * when decoder videoSizeChanged listener
     */
    public static final int PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE = -1021;

    /**
     * when decoder info listenerf
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
     * when you call {@link IDecoder#destroy()}
     */
    public static final int PRIM_PLAYER_EVENT_DESTROY = -1017;

    /**
     * on player timer progress update 进度更新
     * {@link com.prim_player_cc.decoder_cc.helper.TimerUpdateHelper}
     */
    public static final int PRIM_PLAYER_EVENT_TIMER_UPDATE = -1018;

    /**
     * when player status change {@link com.prim_player_cc.status.Status}
     */
    public static final int PRIM_PLAYER_EVENT_STATUS_CHANGE = -1020;

    /**
     * when player advert prompt event 播放的视频为广告
     */
    public static final int PRIM_PLAYER_EVENT_ADVERT_PROMPT = -2000;

    /**
     * when net/wifi --> net player prompt event wifi切换到数据流量
     */
    public static final int PRIM_PLAYER_EVENT_NET_DATA_PROMPT = -2001;

    /**
     * when net --> wifi player prompt event 数据流量切换到Wi-Fi
     */
    public static final int PRIM_PLAYER_EVENT_WIFI_PROMPT = -2004;

    /**
     * when player completion auto player next，自动播放下一个
     */
    public static final int PRIM_PLAYER_EVENT_AUTO_PLAY_NEXT = -2002;

    public static final int PRIM_PLAYER_EVENT_CANCEL_AUTO_PLAY_NEXT = -0x2012;

    /**
     * 告知视图即将播放下一条视频 该提示用户了
     */
    public static final int PRIM_PLAYER_EVENT_FUTURE_PLAY_NEXT = 0x697;
    public static final int PRIM_PLAYER_EVENT_CANCLE_FUTURE_PLAY_NEXT = 0x698;

    /**
     * when player buffering update listener
     */
    public static final int PRIM_PLAYER_EVENT_BUFFER_UPDATE = -2003;

    /**
     * when {@link com.prim_player_cc.view.BasePlayerCCView} full --> vertical 横屏切换到竖屏
     */
    public static final int PRIM_PLAYER_EVENT_FULL_VERTICAL = -2007;

    /**
     * when {@link com.prim_player_cc.view.BasePlayerCCView} vertical --> full 竖屏切换到横屏
     */
    public static final int PRIM_PLAYER_EVENT_VERTICAL_FULL = -2005;

    /**
     * when {@link IDecoder#seek(long)}
     */
    public static final int PRIM_PLAYER_EVENT_ON_SEEK_TO = -2006;

    public static final int PRIM_PLAYER_EVENT_ON_SURFACE_HOLDER_UPDATE = -2008;

    public static final int PRIM_PLAYER_EVENT_ON_SURFACE_UPDATE = -2009;

    public static final int PRIM_PLAYER_EVENT_NET_WORK_CHANGED = -0x2010;

    public static final int PRIM_PLAYER_EVENT_DISPOSABLE = -3010;

    public static final int PRIM_PLAYER_VIDEO_SEEK_RENDERING_START = 104;
}
