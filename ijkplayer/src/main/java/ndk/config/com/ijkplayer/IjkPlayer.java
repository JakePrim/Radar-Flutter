package ndk.config.com.ijkplayer;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.prim_player_cc.config.AVOptions;
import com.prim_player_cc.decoder_cc.AbsDecoderCC;
import com.prim_player_cc.decoder_cc.IDecoder;
import com.prim_player_cc.decoder_cc.event_code.ErrorCode;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.InfoCode;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.render_cc.IRenderView;
import com.prim_player_cc.render_cc.ISurfaceTextureHolder;
import com.prim_player_cc.render_cc.ISurfaceTextureHost;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.status.Status;

import java.io.FileDescriptor;
import java.util.HashMap;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_VIDEO_HEIGHT;
import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_VIDEO_SAR_DEN;
import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_VIDEO_SAR_NUM;
import static com.prim_player_cc.decoder_cc.event_code.EventCodeKey.PLAYER_VIDEO_WIDTH;

/**
 * @author prim
 * @version 1.0.0
 * @desc ijkplayer
 * @time 2018/11/7 - 10:53 AM
 */
public class IjkPlayer extends AbsDecoderCC {
    static {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private IjkMediaPlayer mediaPlayer;//播放器

    private PlayerSource playerSource;//播放器的播放资源

    private Uri mUri;

    private long jumpStartPosition;

    private static final String TAG = "DefaultDecoder";

    private AVOptions avOptions;

    public IjkPlayer() {
        mediaPlayer = new IjkMediaPlayer();
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
                    mediaPlayer = new IjkMediaPlayer();
                } else {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }

                //open mediacodec
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);

                //accurate seek
//                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);

//                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1L);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 10000000);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "reconnect", 1);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

                mediaPlayer.setOption(1, "analyzemaxduration", 100L);

                mediaPlayer.setOption(1, "probesize", 10240L);

                mediaPlayer.setOption(1, "flush_packets", 1L);

                mediaPlayer.setOption(4, "packet-buffering", 0L);

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
            mediaPlayer.reset();
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

    private IjkMediaPlayer.OnPreparedListener onPreparedListener = new IjkMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(final IMediaPlayer mp) {
            PrimLog.d(TAG, "onPrepared");
            Bundle bundle = new Bundle();
            bundle.putInt(PLAYER_VIDEO_WIDTH, mp.getVideoWidth());
            bundle.putInt(PLAYER_VIDEO_HEIGHT, mp.getVideoHeight());
            bundle.putInt(PLAYER_VIDEO_SAR_NUM, mp.getVideoSarNum());
            bundle.putInt(PLAYER_VIDEO_SAR_DEN, mp.getVideoSarDen());
            //TODO 下发准备完毕的监听事件
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED, bundle);
            start();

        }
    };

    private IjkMediaPlayer.OnInfoListener onInfoListener = new IjkMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
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

    private IjkMediaPlayer.OnErrorListener onErrorListener = new IjkMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
            PrimLog.d(TAG, "onError:" + framework_err);
            //TODO 下发错误事件
            Bundle bundle = new Bundle();
            bundle.putInt("framework_err", framework_err);
            bundle.putInt("extra", impl_err);
            triggerErrorEvent(bundle, ErrorCode.PLAYER_EVENT_ERROR_UNKNOWN);
            return false;
        }
    };

    private IjkMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new IjkMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            //TODO 下发缓存更新事件
            if (percent != 100) {
                PrimLog.d(TAG, "onBufferingUpdate:" + percent);
                triggerBufferUpdate(null, percent);
            }
        }
    };

    private IjkMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new IjkMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
            //TODO 下发视频大小改变事件
            Bundle bundle = new Bundle();
            bundle.putInt(PLAYER_VIDEO_WIDTH, i);
            bundle.putInt(EventCodeKey.PLAYER_VIDEO_HEIGHT, i1);
            bundle.putInt(EventCodeKey.PLAYER_VIDEO_SAR_NUM, i2);
            bundle.putInt(EventCodeKey.PLAYER_VIDEO_SAR_DEN, i3);
            triggerPlayerEvent(PlayerEventCode.PRIM_PLAYER_EVENT_VIDEO_SIZE_CHANGE, bundle);
        }
    };

    private IjkMediaPlayer.OnCompletionListener onCompletionListener = new IjkMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
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
//            IjkMediaPlayer.native_profileEnd();
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
        if (mediaPlayer != null) {
            mediaPlayer.setSpeed(m);
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
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoSarDen();
        }
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
        if (mediaPlayer != null) {
            mediaPlayer.setLogEnabled(enabled);
        }
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
