package com.prim_player_cc.decoder_cc.event_code;

/**
 * @author prim
 * @version 1.0.0
 * @desc 自定义错误事件码
 * 如需要其他的错误码 可自行添加
 * @time 2018/9/19 - 上午10:17
 */
public class ErrorCode {
    /**
     * 本地文件或网络相关错误
     */
    public static final int PLAYER_EVENT_ERROR_IO = -10004;

    /**
     * 播放超时错误
     */
    public static final int PLAYER_EVENT_ERROR_TIMED_OUT = -10005;

    /**
     * 未知错误
     */
    public static final int PLAYER_EVENT_ERROR_UNKNOWN = -10006;


    /**
     * 比特流不符合相关的编码标准
     */
    public static final int PLAYER_EVENT_ERROR_MALFORMED = -10007;


}
