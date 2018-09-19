package com.prim_player_cc.decoder_cc;

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
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.status.PlayerStatus;
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

    private long jumpStartPosition;

    private static final String TAG = "DefaultDecoder";

    public DefaultDecoder() {
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
            updateState(Status.STATE_INIT);

            setPlayerSource(source);

            initListener();

            //TODO 触发设置播放资源事件
            Bundle bundle = new Bundle();
            bundle.putParcelable(EventCodeKey.PLAYER_DATA_SOURCE, source);
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE, bundle);

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
            updateState(Status.STATE_ERROR);
        }
    }

    private void setPlayerSource(PlayerSource source) throws IOException {
        String url = source.getUrl();
        Uri uri = source.getUri();
        AssetFileDescriptor assetFileDescriptor = source.getAssetFileDescriptor();
        if (!TextUtils.isEmpty(url)) {
            PrimLog.d(TAG, "setPlayerSource url -->> " + url);
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
            triggerErrorEvent(bundle, ErrorEventCode.PLAYER_EVENT_ERROR_UNKNOWN);
            return false;
        }
    };

    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            PrimLog.d(TAG, "onBufferingUpdate:" + percent);
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
    public void start() {
        if (mediaPlayer != null) {
            updateState(Status.STATE_START);
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
            updateState(Status.STATE_START);
            mediaPlayer.start();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_RESUME, null);
        }
    }

    @Override
    public void reset() {
        if (mediaPlayer != null) {
            updateState(Status.STATE_IDEL);
            mediaPlayer.reset();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_RESET, null);
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            updateState(Status.STATE_STOP);
            mediaPlayer.stop();
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_STOP, null);
        }
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
    public View getRenderView() {
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
    public void destroy() {
        super.destroy();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        updateState(Status.STATE_END);
        triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY, null);
    }
}
