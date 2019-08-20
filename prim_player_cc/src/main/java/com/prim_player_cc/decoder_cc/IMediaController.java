package com.prim_player_cc.decoder_cc;

import android.view.View;

import com.prim_player_cc.source_cc.AbsDataProvider;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.status.PlayerStatus;

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

        void rePlay();

        boolean hasNext();

        boolean hasForward();

        void playerNext();

        void playerForward();

        void selectDataPlay(int position);

        int currentPlayIndex();

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

        AbsDataProvider getDataProvider();

        PlayerSource getPlayerSource();

        void releaseSurface();

        void setVideoRotation(int degree);

        void setDiaplayAspectRatio(int ratio);

        @PlayerStatus
        int getState();

        void setDataSource(PlayerSource dataSource);
    }
}
