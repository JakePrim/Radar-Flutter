package com.prim_player_cc.decoder_cc.event_code;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/18 - 下午2:11
 */
public class InfoCode {
    public static final int MEDIA_INFO_AUDIO_DECODED_START = 10003;
    public static final int MEDIA_INFO_VIDEO_DECODED_START = 10004;
    public static final int MEDIA_INFO_OPEN_INPUT = 10005;
    public static final int MEDIA_INFO_FIND_STREAM_INFO = 10006;
    public static final int MEDIA_INFO_COMPONENT_OPEN = 10007;
    public static final int MEDIA_INFO_VIDEO_SEEK_RENDERING_START = 10008;
    public static final int MEDIA_INFO_AUDIO_SEEK_RENDERING_START = 10009;
    public static final int MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE = 10100;

    public static final int MEDIA_INFO_UNKNOWN = 1;//未知信息
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;//播放下一条
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;//视频开始整备中，准备渲染
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;//视频日志跟踪
    public static final int MEDIA_INFO_BUFFERING_START = 701;//开始缓冲中 开始缓冲
    public static final int MEDIA_INFO_BUFFERING_END = 702;//缓冲结束
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;//网络带宽，网速方面
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;//
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;//不可设置播放位置，直播方面
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;//
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;//不支持字幕
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;//字幕超时
    public static final int MEDIA_INFO_VIDEO_INTERRUPT = -10000;//数据连接中断，一般是视频源有问题或者数据格式不支持，比如音频不是AAC之类的
    public static final int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;//视频方向改变，视频选择信息
    public static final int MEDIA_INFO_AUDIO_RENDERING_START = 10002;//音频开始整备中
}
