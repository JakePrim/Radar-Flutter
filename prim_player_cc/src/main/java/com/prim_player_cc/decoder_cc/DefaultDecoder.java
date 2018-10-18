package com.prim_player_cc.decoder_cc;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.ApplicationAttach;
import com.prim_player_cc.decoder_cc.event_code.ErrorCode;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.status.Status;

import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc default player 系统自带播放器组件，只处理播放相关的逻辑
 * @time 2018/7/24 - 下午4:06
 */
public class DefaultDecoder extends BaseDecoderCC {

    private MediaPlayer mediaPlayer;//播放器

    private PlayerSource playerSource;//播放器的播放资源

    private Uri mUri;

    private long jumpStartPosition;

    private static final String TAG = "DefaultDecoder";

    public DefaultDecoder() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void setDataSource(PlayerSource source) {
        this.playerSource = source;
        openVideo();
    }

    @Override
    public void openVideo() {
        if (playerSource == null) {
            return;
        }
        try {
            if (playerSource.isPlayerSource()) {
                release(false);
                mediaPlayer = new MediaPlayer();
                initListener();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    mediaPlayer.setDataSource(ApplicationAttach.getApplicationContext(), playerSource.getVideoUri(), playerSource.getHeaders());
                } else {
                    mediaPlayer.setDataSource(ApplicationAttach.getApplicationContext(),playerSource.getVideoUri());
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(EventCodeKey.PLAYER_DATA_SOURCE, playerSource);
                triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE, bundle);

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setScreenOnWhilePlaying(true);
                prepareAsync();
                updateState(Status.STATE_PREPARING);
            }
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
            updateState(Status.STATE_ERROR);
        }
    }


    private void release(boolean clear) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            updateState(Status.STATE_IDEL);
        }
    }

    private void initListener() {
        if (mediaPlayer == null) return;
        mediaPlayer.setOnPreparedListener(onPreparedListener);
        mediaPlayer.setOnInfoListener(onInfoListener);
        mediaPlayer.setOnErrorListener(onErrorListener);
        mediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    private void resetListener() {
        if (mediaPlayer == null) return;
        mediaPlayer.setOnPreparedListener(null);
        mediaPlayer.setOnInfoListener(null);
        mediaPlayer.setOnErrorListener(null);
        mediaPlayer.setOnBufferingUpdateListener(null);
        mediaPlayer.setOnCompletionListener(null);
    }

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            PrimLog.d(TAG, "onPrepared");
            updateState(Status.STATE_PREPARED);
            mp.start();
            //TODO 下发准备完毕的监听事件
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED, null);
        }
    };

    private MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            PrimLog.d(TAG, "onInfo:" + what);
            //TODO 下发信息事件监听
//            triggerPlayerEvent(PlayerEventCode.P);
            return false;
        }
    };

    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            PrimLog.d(TAG, "onError:" + framework_err);
            updateState(Status.STATE_ERROR);
            Bundle bundle = new Bundle();
            bundle.putInt("framework_err", framework_err);
            bundle.putInt("extra", impl_err);
            //TODO 下发错误事件
            triggerErrorEvent(bundle, ErrorCode.PLAYER_EVENT_ERROR_UNKNOWN);
            return false;
        }
    };

    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            PrimLog.d(TAG, "onBufferingUpdate:" + percent);
            //TODO 下发缓存更新事件
            triggerBufferUpdate(null, percent);
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            PrimLog.d(TAG, "onCompletion");
            //TODO 下发播放完成事件
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION, null);
        }
    };

    @Override
    public PlayerSource getDataSource() {
        return null;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        if (mediaPlayer != null) {
            mediaPlayer.prepareAsync();
        }
    }

    @Override
    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_START, null);
        }
    }

    @Override
    public void start(long location) {
        this.jumpStartPosition = location;
        start();
    }

    @Override
    public void pause() {
        if (mediaPlayer != null) {
            updateState(Status.STATE_PAUSE);
            mediaPlayer.pause();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PAUSE, null);
        }
    }

    @Override
    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_RESUME, null);
        }
    }

    @Override
    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_RESET, null);
        }
    }

    @Override
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            updateState(Status.STATE_IDEL);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_STOP, null);
        }
    }

    @Override
    public void setLooping(boolean loop) {
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(loop);
        }
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer != null && mediaPlayer.isLooping();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return -1;
    }

    @Override
    public void setVolume(float left, float right) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(left, right);
        }
    }

    @Override
    public void setSpeed(float m) {
        PrimLog.e(TAG, "System MediaPlayer not support speed");
    }

    @Override
    public void setSurface(Surface surface) {
        if (surface != null && mediaPlayer != null) {
            mediaPlayer.setSurface(surface);
        }
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null && mediaPlayer != null) {
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
    public IRenderView getRenderView() {
        return null;
    }

    @Override
    public int getVideoWidth() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoWidth();
        }

        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        return 0;
    }

    @Override
    public void setWakeMode(Context context, int mode) {

    }

    @Override
    public void setLogEnabled(boolean enabled) {

    }


    @Override
    public void destroy() {
        super.destroy();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        resetListener();
        updateState(Status.STATE_END);
        triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY, null);
    }

    @Override
    public void seek(long msec) throws IllegalStateException {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo((int) msec);
        }
    }
}
