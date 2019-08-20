package com.prim_player_cc.decoder_cc;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.decoder_cc.event_code.ErrorCode;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.InfoCode;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.status.Status;

import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_VIDEO_HEIGHT;
import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_VIDEO_WIDTH;
import static com.prim_player_cc.config.AVOptions.KEY_START_ON_PREPARED;

/**
 * @author prim
 * @version 1.0.0
 * @desc default player 系统自带播放器组件，只处理播放相关的逻辑
 * @time 2018/7/24 - 下午4:06
 */
public class DefaultDecoder extends AbsDecoderCC {

    private MediaPlayer mediaPlayer;//播放器

    private PlayerSource playerSource;//播放器的播放资源

    private Uri mUri;

    private long jumpStartPosition;

    private static final String TAG = "DefaultDecoder";

    private AVOptions avOptions;

    public DefaultDecoder() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void setDataSource(PlayerSource source) {
        this.playerSource = source;
        openVideo();
    }

    private void openVideo() {
        if (playerSource == null) {
            return;
        }
        PrimLog.e(TAG, "初始化视频");
        try {
            if (playerSource.isPlayerSource()) {
                resetListener();

                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }

                initListener();

                PrimLog.e(TAG, "播放地址:" + playerSource.getUrl());

                mediaPlayer.setDataSource(playerSource.getUrl());

                Bundle bundle = new Bundle();

                bundle.putParcelable(EventCodeKey.PLAYER_DATA_SOURCE, playerSource);

                //TODO 下发播放资源事件
                triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE, bundle);

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.setScreenOnWhilePlaying(true);

                prepareAsync();
            }
        } catch (Exception e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
            PrimLog.e(TAG, "出现异常");
            triggerErrorEvent(null, ErrorCode.PLAYER_EVENT_ERROR_UNKNOWN);
        }
    }


    private void release(boolean clear) {
        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initListener() {
        if (mediaPlayer == null) return;
        mediaPlayer.setOnPreparedListener(onPreparedListener);
        mediaPlayer.setOnInfoListener(onInfoListener);
        mediaPlayer.setOnErrorListener(onErrorListener);
        mediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    private void resetListener() {
        if (mediaPlayer == null) return;
        mediaPlayer.setOnPreparedListener(null);
        mediaPlayer.setOnInfoListener(null);
        mediaPlayer.setOnErrorListener(null);
        mediaPlayer.setOnBufferingUpdateListener(null);
        mediaPlayer.setOnCompletionListener(null);
        mediaPlayer.setOnVideoSizeChangedListener(null);
    }

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(final MediaPlayer mp) {
            PrimLog.d(TAG, "onPrepared");
            Bundle bundle = new Bundle();
            bundle.putInt(PLAYER_VIDEO_WIDTH, mp.getVideoWidth());
            bundle.putInt(PLAYER_VIDEO_HEIGHT, mp.getVideoHeight());
            //TODO 下发准备完毕的监听事件
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED, bundle);
            if (avOptions != null) {
                if (avOptions.containsKey(KEY_START_ON_PREPARED)) {
                    //自动播放
                    if (avOptions.getInteger(KEY_START_ON_PREPARED) == 1) {
                        start();
                    } else {
                        PrimLog.e(TAG, "不允许自动播放");
                    }
                } else {
                    start();
                }
            } else {
                start();
            }

        }
    };

    private MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            PrimLog.e(TAG, "onInfo:" + what);
            Bundle bundle = new Bundle();
            bundle.putInt(EventCodeKey.PLAYER_INFO_WHAT, what);
            bundle.putInt(EventCodeKey.PLAYER_INFO_EXTRA, extra);
            switch (what) {
                case InfoCode.MEDIA_INFO_BUFFERING_START:
                    triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_BUFFERING_START, bundle);
                    break;
                case InfoCode.MEDIA_INFO_BUFFERING_END:
                    triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_BUFFERING_END, bundle);
                    break;
                case InfoCode.MEDIA_INFO_VIDEO_RENDERING_START:
                    triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PANDERING_START, bundle);
                    break;
                case InfoCode.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_ROTATION_CHANGED, bundle);
                    break;
            }
            //TODO 下发信息事件监听 交给视图组件去处理
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_INFO, bundle);
            return false;
        }
    };

    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            PrimLog.d(TAG, "onError:" + framework_err);
            //TODO 下发错误事件
            Bundle bundle = new Bundle();
            bundle.putInt("framework_err", framework_err);
            bundle.putInt("extra", impl_err);
            triggerErrorEvent(bundle, ErrorCode.PLAYER_EVENT_ERROR_UNKNOWN);
            return false;
        }
    };

    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            //TODO 下发缓存更新事件
            if (percent != 100) {
                PrimLog.d(TAG, "onBufferingUpdate:" + percent);
                triggerBufferUpdate(null, percent);
            }
        }
    };

    private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            //TODO 下发视频大小改变事件
            Bundle bundle = new Bundle();
            bundle.putInt(PLAYER_VIDEO_WIDTH, width);
            bundle.putInt(EventCodeKey.PLAYER_VIDEO_HEIGHT, height);
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE, bundle);
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
        return playerSource;
    }

    @Override
    public void setAVOptions(AVOptions avOptions) {
        this.avOptions = avOptions;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        if (mediaPlayer != null) {
            mediaPlayer.prepareAsync();
            //TODO 下发准备中事件
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PREPARING, null);
        }
    }

    @Override
    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            //TODO 下发开始播放事件
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
            mediaPlayer.pause();
            //TODO 下发暂停播放事件
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PAUSE, null);
        }
    }

    @Override
    public void resume() {
        if (mediaPlayer != null && getState() == Status.STATE_PAUSE) {
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
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_RELEASE, null);
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            //TODO 下发停止播放事件
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
        if (mediaPlayer != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PlaybackParams playbackParams = mediaPlayer.getPlaybackParams();
            playbackParams.setSpeed(m);
            mediaPlayer.setPlaybackParams(playbackParams);
        } else {
            PrimLog.e(TAG, "System MediaPlayer not support speed");
        }
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
        if (mediaPlayer != null) {
            mediaPlayer.setWakeMode(context, mode);
        }
    }

    @Override
    public void setLogEnabled(boolean enabled) {

    }


    @Override
    public void destroy() {
        super.destroy();
        release(true);
        resetListener();
        //TODO 下发销毁播放器事件
        triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY, null);
    }

    @Override
    public void seek(long msec) throws IllegalStateException {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo((int) msec);
        }
    }
}
