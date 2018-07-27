package com.prim_player_cc.player_cc;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.data.PlayerSource;
import com.prim_player_cc.state.State;

import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc default player 系统自带播放器组件，只处理播放相关的逻辑
 * @time 2018/7/24 - 下午4:06
 */
public class DefaultPlayer extends BasePlayerCC {

    private MediaPlayer mediaPlayer;//播放器

    private PlayerSource playerSource;//播放器的播放资源

    private long jumpStartPosition;

    public DefaultPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void setDataSource(PlayerSource source) {
        this.playerSource = source;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            stop();
            reset();
            resetListener();
        }
        try {
            updateState(State.STATE_INIT);
            initListener();
            String url = source.getUrl();
            Uri uri = source.getUri();
            AssetFileDescriptor assetFileDescriptor = source.getAssetFileDescriptor();
            if (!TextUtils.isEmpty(url)) {
                mediaPlayer.setDataSource(url);
            } else if (uri != null) {
                if (source.getHeaders().isEmpty()) {
                    mediaPlayer.setDataSource(ApplicationAttach.getApplicationContext(), uri);
                } else {
                    mediaPlayer.setDataSource(ApplicationAttach.getApplicationContext(), uri, source.getHeaders());
                }
            } else if (assetFileDescriptor != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mediaPlayer.setDataSource(assetFileDescriptor);
                }
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            updateState(State.STATE_ERROR);
        }
    }

    private void initListener() {
//        mediaPlayer.setOnPreparedListener();
//        mediaPlayer.setOnInfoListener();
//        mediaPlayer.setOnErrorListener();
//        mediaPlayer.setOnBufferingUpdateListener();
//        mediaPlayer.setOnCompletionListener();
//        mediaPlayer.setOnTimedTextListener();
    }

    private void resetListener() {

    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void start(long location) {
        this.jumpStartPosition = location;
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void resume() {
        mediaPlayer.release();
    }

    @Override
    public void reset() {
        mediaPlayer.reset();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    @Override
    public void seek(int position) {
        mediaPlayer.seekTo(position);
    }

    @Override
    public void setLooping(boolean loop) {
        mediaPlayer.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void setVolume(float left, float right) {

    }

    @Override
    public void setSpeed(float m) {

    }

    @Override
    public void setSurface(Surface surface) {
        if (surface != null) {
            mediaPlayer.setSurface(surface);
        }
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null) {
            mediaPlayer.setDisplay(surfaceHolder);
        }
    }

    @Override
    public void bindVideoView(View videoView) {
//        if (view instanceof SurfaceView || view instanceof TextureView) {
//            this.videoView = view;
//            //注意这里需要将view 绑定到最底端
//            addView(view, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        } else {
//            throw new RuntimeException("video view must SurfaceView or TextureView,please check bindVideoView()");
//        }
    }

    @Override
    public void destroy() {
        super.destroy();
        mediaPlayer.release();
    }
}
