package com.prim_player_cc.decoder_cc;

import android.view.View;

/**
 * @author prim
 * @version 1.0.0
 * @desc 控制器实现的接口
 * @time 2018/10/19 - 上午11:00
 */
public interface IMediaController {
    void setMediaPlayer(IMediaController.MediaPlayerControl mediaPlayerControl);

    void show();

    void show(int timeout);

    void hide();

    boolean isShowing();

    void setEnabled(boolean enabled);

    void setAnchorView(View view);

    public interface MediaPlayerControl {
        void start();

        void pause();

        void stop();

        long getDuration();

        long getCurrentPosition();

        void seekTo(long msec);

        boolean isPlaying();

        void setLooping(boolean loop);

        boolean isLooping();

        int getBufferPercentage();

        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        void setVolume(float left, float right);

        void setSpeed(float m);
    }
}
