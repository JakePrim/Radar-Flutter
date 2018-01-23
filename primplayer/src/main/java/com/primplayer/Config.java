package com.primplayer;

import com.pili.pldroid.player.widget.PLVideoView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：1/23 0023
 * 描    述: prim player Config
 * 修订历史：
 * ================================================
 */
public class Config {
    public static int KEY_MEDIA_CODEC = 2; // 解码方式，codec＝1，硬解; codec=0, 软解 2 自动

    public static int KEY_PREPARE_TIMEOUT = 10 * 1000;// 准备超时时间10 * 1000

    public static int KEY_GET_AV_FRAME_TIMEOUT = 10 * 1000;// 读取视频流超时时间10 * 1000

    public static int KEY_CACHE_BUFFER_DURATION = 2000; // 默认的缓存大小2000

    public static int KEY_MAX_CACHE_BUFFER_DURATION = 4000;// 最大的缓存大小4000

    public static int KEY_START_ON_PREPARED = 0;// 0 不启用自动播放 1 启用自动播放

    public static boolean ISLOPPING = false;// 是否循环播放

    /**
     * 原始尺寸、适应屏幕、全屏铺满、16:9、4:3
     * mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
     * mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
     * mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
     * mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_16_9);
     * mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_4_3);
     */
    public static final int ASPECT_RATIO = PLVideoView.ASPECT_RATIO_FIT_PARENT;
}
