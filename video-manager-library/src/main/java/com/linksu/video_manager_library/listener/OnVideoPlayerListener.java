package com.linksu.video_manager_library.listener;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/24 0024
 * 描    述：视频播放状态监听
 * 修订历史：
 * ================================================
 */
public interface OnVideoPlayerListener {
    void onVideoPrepared();

    void onVideoCompletion();

    void onVideoError(int i,String error);

    void onBufferingUpdate();

    void onVideoStopped();

    void onVideoPause();

    void onVideoThumbPause();

    void onVideoThumbStart();

    void onVideoPlayingPro(long currentPosition, long mDuration, int mPlayStatus);
}
