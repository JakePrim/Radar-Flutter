package com.linksu.video_manager_library.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linksu.video_manager_library.R;
import com.linksu.video_manager_library.listener.ONShowHidListenner;
import com.linksu.video_manager_library.listener.OnVideoPlayBtnClickListener;
import com.linksu.video_manager_library.listener.OnVideoPlayerListener;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import java.io.File;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/24 0024
 * 描    述：将播放器进行封装
 * 修订历史：
 * ================================================
 */
public class LVideoView extends FrameLayout implements View.OnClickListener, OnVideoPlayBtnClickListener, ONShowHidListenner {

    private static final String TAG = "LVideoView";
    /**
     * video view 控制器
     */
    private LMediaController mController;
    /**
     * 核心播放器
     */
    private PLVideoTextureView videoView;
    /**
     * 视频播放加载视图
     */
    private LinearLayout llLoadingView;
    /**
     * 播放异常时覆盖图
     */
    private LinearLayout llErrorCover;
    private TextView tvErrorCover;
    private ImageView erroIv;
    private FrameLayout fraVideoContainer;
    /**
     * 当前页面是否启动视频播放
     */
    private boolean isVideoPlay = false;
    private boolean isFinish = false;

    private boolean isVideoPlaying;

    private OnVideoPlayerListener videoPlayerListener;

    private ProgressBar progressBar;

    private Context context;

    private boolean isPause = false;// 是否暂停

    public NewCircleTextProgressbar pg_voide_download;//下载进度
    public ImageView iv_video_download;// 下载按钮
    public FrameLayout fl_voide_download;// 下载view
    //    private TabFragMainBeanItemBean paramBean;//进入此页面带入
    private String finalpath;


    public LVideoView(@NonNull Context context) {
        super(context);
        initVideo(context);
    }

    public LVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideo(context);
    }

    public LVideoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideo(context);
    }

    /**
     * 初始化控件
     *
     * @param context
     */
    protected void initVideo(Context context) {
        this.context = context;
        View.inflate(context, R.layout.layout_video_view, this);
        initView();
    }

    /**
     * 初始化view
     */
    public void initView() {
        fraVideoContainer = (FrameLayout) findViewById(R.id.fra_live_video_container);
        llErrorCover = (LinearLayout) findViewById(R.id.ll_live_video_error_cover);
        progressBar = (ProgressBar) findViewById(R.id.list_video_progress);
        erroIv = (ImageView) findViewById(R.id.erroIcon);
        tvErrorCover = (TextView) findViewById(R.id.tv_video_error_cover);
        llLoadingView = (LinearLayout) findViewById(R.id.ll_live_video_loading);
        progressBar.setVisibility(GONE);
        progressBar.setMax(1000);
        initVideoView();
    }

    /**
     * 初始化视频播放器
     */
    public void initVideoView() {
        videoView = new PLVideoTextureView(context);
        videoView.setBackgroundColor(Color.BLACK);
        AVOptions options = new AVOptions();
        // 解码方式，codec＝1，硬解; codec=0, 软解
//        options.setInteger(AVOptions.KEY_MEDIACODEC, 0);
        // 开启底层优化
//        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        // 不开启直播优化
//        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);
        // 开启延时优化---- service中实列化的videoview 添加后 播放几秒无声音
//        options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        // 准备超时时间10 * 1000
//        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 读取视频流超时时间10 * 1000
//        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        // 默认的缓存大小2000
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 2000);
        // 最大的缓存大小4000
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
        // 不启用自动播放
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
        videoView.setAVOptions(options);
        mController = new LMediaController(context);
        mController.topContainer();
        mController.visableDownload();
        pg_voide_download = mController.pg_voide_download;
        iv_video_download = mController.iv_video_download;
        fl_voide_download = mController.fl_voide_download;
        fl_voide_download.setOnClickListener(this);
        mController.setOnVideoPlayBtnClickListener(this);
        mController.setOnShowHidListenner(this);
        setVideoView();
        addVideoViewWithContainer();
    }

//    public void setItemBean(TabFragMainBeanItemBean paramBean) {
//        this.paramBean = paramBean;
//    }


    /**
     * 跳转到指定位置
     *
     * @param position
     */
    public void setSeekTo(long position) {
        mController.controllerSeekTo(position);
    }

    /**
     * 销毁播放器
     */
    public void recycleVideoView() {
        if (videoView != null) {
            videoView.setBufferingIndicator(null);
            videoView.setMediaController(null);
            videoView.setOnPreparedListener(null);
            videoView.setOnInfoListener(null);
            videoView.setOnCompletionListener(null);
            videoView.setOnErrorListener(null);
            videoView = null;
        }
    }

    /**
     * 返回 控制器
     *
     * @return
     */
    public LMediaController getController() {
        return mController;
    }

    /**
     * 设置播放器
     */
    private void setVideoView() {
        videoView.setBufferingIndicator(llLoadingView);
        videoView.setMediaController(mController);
        videoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        videoView.setOnPreparedListener(mVideoPreparedListener);
        videoView.setOnInfoListener(mVideoInfoListener);
        videoView.setOnCompletionListener(mVideoCompletionListener);
        videoView.setOnErrorListener(mVideoErrorListener);
        videoView.setLooping(false);
    }

    /**
     * 添加播放器和控制器
     */
    private void addVideoViewWithContainer() {
        if (videoView.getParent() != null) {
            ((ViewGroup) (videoView.getParent())).removeView(videoView);
        }
        fraVideoContainer.addView(videoView);
        mController.setControllerParent(fraVideoContainer);
    }

    /**
     * 开始播放
     *
     * @param path
     */
    public void startLive(String path) {
        if (videoView != null) {
            if (!isVideoPlay) {
                videoView.stopPlayback();
                this.finalpath = path;
                videoView.setVideoPath(finalpath);
            }
            if (progressBar != null) {
                progressBar.setVisibility(GONE);
            }
            if (isPause) { // 暂停之后再播放，走这一段代码
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mController.show(5000);
                    }
                }, 300);
            }
            videoView.start();
            isPause = false;
            isVideoPlay = true;
        }
    }

    /**
     * 继续播放
     */
    public void currentPlayer() {
        if (videoView != null) {
            videoView.start();
        }
    }

    public boolean isPlayer() {
        return isVideoPlay;
    }

    /**
     * 更新视频下载状态
     *
     * @param url
     */
//    public void updateVideoDownLoadSate(String url) {
//        FileEntity fileEntity = (FileEntity) DbHelper.getInstance().searchAsCulom(FileEntity.class, "url", url);
//        if (fileEntity != null) {
//            videoInit();
//        }
//    }

    /**
     * 手动暂停
     */
    public void onThumePause() {
        isPause = true;
        if (videoView != null && isVideoPlay) {
            isVideoPlaying = videoView.isPlaying();
            videoView.pause();
            videoView.setVolume(0f, 0f);
        }
    }

    /**
     * 手动暂停，从后台回来之后调用
     */
    public void startThumb() {
        if (videoView != null && isVideoPlay) {
            if (!isVideoPlaying) {
                videoView.setVolume(0, 0);
                videoView.start();
            } else {
                videoView.start();
                videoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (videoView != null)
                            videoView.setVolume(1, 1);
                    }
                }, 300);
            }
            if (progressBar != null) {
                progressBar.setVisibility(GONE);
            }
            videoView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("linksu",
                            "run(LVideoView.java:349)");
                    videoView.setVolume(1, 1);
                    videoView.pause();
                }
            }, 200);
        }
    }

    /**
     * 大小屏切换播放器的处理
     *
     * @param newConfig
     */
    public void onConfigurationChanged(Configuration newConfig) {
        ViewGroup.LayoutParams layoutParams = fraVideoContainer.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) { //竖屏
            layoutParams.height = (int) getResources().getDimension(R.dimen.live_video_height);
            fraVideoContainer.setLayoutParams(layoutParams);
        } else {// 横屏
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            fraVideoContainer.setLayoutParams(layoutParams);
        }
        if (videoView != null && isVideoPlay) {
            isVideoPlaying = videoView.isPlaying();
            if (!isVideoPlaying) {
                startThumb();
            }
        }
    }

    /**
     * 停止播放
     */
    public void stopVideoPlay() {
        // videoView 不等于 null 且正在播放的时候 停止
        if (videoView != null && isVideoPlay) {
            videoView.stopPlayback();
            isVideoPlay = false;
            if (progressBar != null) {
                progressBar.setVisibility(GONE);
            }
            if (videoPlayerListener != null) {
                videoPlayerListener.onVideoStopped();
            }
            mController.stopVideoPlayingCallback();
        }
    }

    /**
     * 暂停播放
     */
    public void onPause() {
        if (videoView != null && isVideoPlay) {
            isVideoPlaying = videoView.isPlaying();
            videoView.pause();
            isPause = true;
            if (progressBar != null) {
                progressBar.setVisibility(GONE);
            }
            if (videoPlayerListener != null) {
                videoPlayerListener.onVideoPause();
            }
        }
    }

    /**
     * 播放状态监听
     *
     * @param videoPlayerListener
     */
    public void setOnVideoPlayerListener(OnVideoPlayerListener videoPlayerListener) {
        this.videoPlayerListener = videoPlayerListener;
    }

    /**
     * 注意需要 注销监听
     */
    public void unOnVideoPlayerListener() {
        if (videoPlayerListener != null) {
            videoPlayerListener = null;
        }
    }

    // 播放完毕时回调
    private PLMediaPlayer.OnCompletionListener mVideoCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            if (videoView != null) {
                if (videoPlayerListener != null)
                    videoPlayerListener.onVideoCompletion();
                setComplet();
            }
        }
    };

    // 播放发生异常时回调
    private PLMediaPlayer.OnErrorListener mVideoErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
            String toast;
            switch (i) {
                case -5:
                    toast = "网络异常";
                    break;
                case -11:
                    toast = "与服务器连接断开";
                    break;
                case -110:
                    toast = "连接超时";
                    break;
                default:
                    toast = "播放状态异常";
                    break;
            }
            llErrorCover.setVisibility(VISIBLE);
            tvErrorCover.setText(toast);
            erroIv.setImageResource(R.drawable.icon_live_video_notice_thumbnail);
            if (videoPlayerListener != null)
                videoPlayerListener.onVideoError(i, toast);
            return true;
        }
    };

    /**
     * 完成播放
     */
    public void setComplet() {
        if (progressBar != null) {
            progressBar.setVisibility(GONE);
        }
        videoView.stopPlayback();
        isFinish = true;
        isVideoPlay = false;
        llErrorCover.setVisibility(VISIBLE);
        erroIv.setVisibility(VISIBLE);
        erroIv.setImageResource(R.drawable.play_again);
        tvErrorCover.setText("重播");
    }

    // 准备完毕后回调
    private PLMediaPlayer.OnPreparedListener mVideoPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(final PLMediaPlayer plMediaPlayer) {
            Log.e(TAG, "onPrepared: 准备完毕播放");
            if (videoPlayerListener != null) {
                videoPlayerListener.onVideoPrepared();
            }
            isVideoPlaying = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mController.show(5000);
                }
            }, 500);
            start();
            if (mController != null) { // 自动播放时 拿到这个播放进度
                mController.startVideoPlayingCallback(new LMediaController.OnVideoPlayingCallBack() {
                    @Override
                    public void onVideoPlaying(long currentPosition, long mDuration, int mPlayStatus) {
                        setProgressToUI(currentPosition, mDuration);
                        if (videoPlayerListener != null) {
                            videoPlayerListener.onVideoPlayingPro(currentPosition, mDuration, mPlayStatus);
                        }

                    }
                }, 1000);
            }
        }
    };

    // 播放状态发生变化回调
    private PLMediaPlayer.OnInfoListener mVideoInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
            switch (i) {
                case 701:
                    // 开始缓冲
                    break;
                case 702:
                    // 停止缓冲
                    break;
            }
            if (videoPlayerListener != null)
                videoPlayerListener.onBufferingUpdate();
            return false;
        }
    };

    private void start() {
        llErrorCover.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void setOnVideoPlayBtnClick(boolean isPlay) {
        if (isPlay) { //开始播放 监听进度w
            if (mController != null) {
                mController.startVideoPlayingCallback(new LMediaController.OnVideoPlayingCallBack() {
                    @Override
                    public void onVideoPlaying(long currentPosition, long mDuration, int mPlayStatus) {
                        setProgressToUI(currentPosition, mDuration);
                        if (videoPlayerListener != null) {
                            videoPlayerListener.onVideoPlayingPro(currentPosition, mDuration, mPlayStatus);
                        }
                    }
                }, 1000);
            }
        } else { //暂停播放

        }
    }

    /**
     * 更新进度条
     */
    private void setProgressToUI(long currentPosition, long mDuration) {
        if (mController == null) {
            return;
        }
        if (progressBar != null) {
            if (currentPosition > 0) {
                long pos = 1000L * currentPosition / mDuration;
                progressBar.setProgress((int) pos);
            }
            int percent = mController.getBufferPro();
            progressBar.setSecondaryProgress(percent * 10);
        }
    }


    @Override
    public void onShow() {
        if (mController == null) {
            return;
        }
        if (progressBar != null) {
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onHid() {
        if (mController == null) {
            return;
        }
        if (progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onThumbPause() {
        if (videoPlayerListener != null) {
            videoPlayerListener.onVideoThumbPause();
        }
    }

    @Override
    public void onStart() {
        if (videoPlayerListener != null) {
            videoPlayerListener.onVideoThumbStart();
        }
    }
}
