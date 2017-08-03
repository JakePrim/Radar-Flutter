package com.linksu.video_manager_library.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.linksu.video_manager_library.R;
import com.linksu.video_manager_library.listener.ONShowHidListenner;
import com.linksu.video_manager_library.listener.OnDragToEndListener;
import com.linksu.video_manager_library.listener.OnVideoPlayBtnClickListener;
import com.pili.pldroid.player.IMediaController;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/24 0024
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LMediaController extends FrameLayout implements IMediaController {

    private Context context;
    // handler-控制当前Controller隐藏
    private static final int SET_CONTROLLER_VISIBLE_GONE = 5;
    // handler-播放进度更新
    private static final int SET_PROGRESS_UPDATE_UI = 2;
    private static final int SET_VIDEO_PLAYING_CALLBACK = 7;
    // 控制器
    private LinearLayout llTopContainer;
    private TextView tvTitle;
    private LinearLayout llMoreContainer;
    private ImageView imgVideoPlay;
    private ImageView imgVideoFullScreen;
    private TextView tvCurrentTime;
    private TextView tvAllTime;
    private SeekBar skVideoSeek;
    private FrameLayout fraParentLayout;
    /**
     * 视频实际控制对象
     */
    private MediaPlayerControl mController;
    /**
     * 控制器是否可见
     */
    private boolean isControllerShow;
    /**
     * 当前是否处于拖动状态下
     */
    private boolean isDragging = false;
    /**
     * 是否允许拖动seekBar的同时实时更新播放进度
     */
    private boolean mInstantSeeking = true;
    /**
     * 对外暴露的视频播放器显示控制开关,默认显示
     * true:显示控制器
     * false：不显示控制器
     */
    private boolean mControllerSwitch = true;
    private boolean isAdvert = false;
    private boolean isVideoPlaying = true;
    /**
     * 视频总时长
     */
    private long mDuration;
    private AudioManager mAudioManager;
    private Runnable mLastSeekBarRunnable;

    public FrameLayout fl_voide_download;
    public NewCircleTextProgressbar pg_voide_download;
    public ImageView iv_video_download;

    private boolean isNewsDetailV = false;

    public void setOnShowHidListenner(ONShowHidListenner onShowHidListenner) {
        this.onShowHidListenner = onShowHidListenner;
    }

    private ONShowHidListenner onShowHidListenner;

    private OnDragToEndListener mOnDragToEndListener;

    private OnVideoPlayBtnClickListener onVideoPlayBtnClickListener;

    private OnVideoDownloadClickListener onVideoDownloadClickListener;

    public interface OnVideoDownloadClickListener {
        void onClick();
    }

    /**
     * 播放进度实时回调
     */
    public static interface OnVideoPlayingCallBack {
        void onVideoPlaying(long currentPosition, long mDuration, int mPlayStatus);
    }

    private OnVideoPlayingCallBack onVideoPlayingCallBack;

    public LMediaController(@NonNull Context context) {
        this(context, false);
    }

    public LMediaController(@NonNull Context context, boolean isAdvert) {
        super(context);
        this.context = context;
        this.isAdvert = isAdvert;
        init();
    }

    public LMediaController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LMediaController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    protected void init() {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_video_controller, this);
        llTopContainer = (LinearLayout) rootView.findViewById(R.id.ll_layout_media_controller_top_container);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_layout_media_controller_title);
        llMoreContainer = (LinearLayout) rootView.findViewById(R.id.ll_layout_media_controller_more_container);
        imgVideoPlay = (ImageView) rootView.findViewById(R.id.list_video_btn_play);
        imgVideoFullScreen = (ImageView) rootView.findViewById(R.id.list_video_btn_all);
        tvCurrentTime = (TextView) rootView.findViewById(R.id.list_video_tv_current_time);
        tvAllTime = (TextView) rootView.findViewById(R.id.list_video_tv_all_time);
        skVideoSeek = (SeekBar) rootView.findViewById(R.id.list_video_seek);
        fl_voide_download = (FrameLayout) rootView.findViewById(R.id.fl_voide_download);

        pg_voide_download = (NewCircleTextProgressbar) rootView.findViewById(R.id.pg_voide_download);
        iv_video_download = (ImageView) rootView.findViewById(R.id.iv_video_download);
        skVideoSeek.setThumbOffset(1);
        skVideoSeek.setMax(1000);
        bindListener();
        judgeAdvertVisible();
    }

    /**
     * 广告
     */
    private void judgeAdvertVisible() {
        fl_voide_download.setVisibility(isAdvert ? GONE : VISIBLE);
        imgVideoFullScreen.setVisibility(isAdvert ? GONE : VISIBLE);
        llTopContainer.setVisibility(isAdvert ? GONE : VISIBLE);
    }

    /**
     * 动态设置顶部的隐藏
     */
    public void goneTopContainer() {
        isNewsDetailV = true;
        llTopContainer.setEnabled(false);
        llTopContainer.setVisibility(INVISIBLE);
    }

    /**
     * 动态设置下载隐藏
     */
    public void goneDownload() {
        fl_voide_download.setEnabled(false);
        fl_voide_download.setVisibility(GONE);
    }

    /**
     * 动态设置下载显示
     */
    public void visableDownload() {
        fl_voide_download.setEnabled(true);
        fl_voide_download.setVisibility(VISIBLE);
    }

    @Override
    public void setVisibility(int visibility) {
        if (mControllerSwitch) {
            super.setVisibility(visibility);
        } else {
            super.setVisibility(GONE);
        }
    }

    /**
     * 绑定监听器
     */
    private void bindListener() {
        imgVideoPlay.setOnClickListener(mVideoPlayClick);
        imgVideoFullScreen.setOnClickListener(mFullScreenClick);
        skVideoSeek.setOnSeekBarChangeListener(mSeekListener);
    }

    private Boolean isNetChangeDialogNeedShow;
    private OnClickListener mVideoPlayClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mController == null) {
                return;
            }
            isNetChangeDialogNeedShow = true;
            // 播放按钮的控制
            if (mController.isPlaying()) {
                mController.pause();
                isVideoPlaying = false;
                if (onVideoPlayBtnClickListener != null) {
                    onVideoPlayBtnClickListener.setOnVideoPlayBtnClick(false);
                }
                // TODO: 2017/1/17 手动暂停
                if (onShowHidListenner != null) {
                    onShowHidListenner.onThumbPause();
                }
            } else {
                isNetChangeDialogNeedShow = false;
                mController.start();
                isVideoPlaying = true;
                if (onVideoPlayBtnClickListener != null) {
                    onVideoPlayBtnClickListener.setOnVideoPlayBtnClick(true);
                }
//                NetChangeManager.getInstance().adjustNetBeforDownlaod(new NetChangeManager.OnNetAdjustCallBack() {
//                    @Override
//                    public void onGranted() {// wifi条件下自动回调
//                        isNetChangeDialogNeedShow = false;
//                        mController.start();
//                        isVideoPlaying = true;
//                        if (onVideoPlayBtnClickListener != null) {
//                            onVideoPlayBtnClickListener.setOnVideoPlayBtnClick(true);
//                        }
//                    }
//
//                    @Override
//                    public void onErro(String msg) {    // 无网络情况下回调
//
//                    }
//
//                    @Override
//                    public boolean onStartNeedShow() {  // 是否需要弹出流量显示框
//                        return isNetChangeDialogNeedShow;
//                    }
//
//                    @Override
//                    public boolean onMidNeedShow() {    // wifi切回为流量情况下 暂停播放 弹框逻辑以封装之网络处理内部
//                        mController.pause();
//                        isVideoPlaying = false;
//                        if (onVideoPlayBtnClickListener != null) {
//                            onVideoPlayBtnClickListener.setOnVideoPlayBtnClick(false);
//                        }
//                        return isNetChangeDialogNeedShow;
//                    }
//
//                    @Override
//                    public void onCancel() {            // 取消
//                        isNetChangeDialogNeedShow = false;
//                    }
//                });
            }
            updatePlayBtnUI();
        }
    };

    private OnClickListener mFullScreenClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("linksu LMediaController",
                    "onClick(LMediaController.java:290) mFullScreenClick");
            if (mController == null) {
                return;
            }
            // 控制方向切换
            int screenOrientation = ((Activity) context).getRequestedOrientation();
            switch (screenOrientation) {
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
            }
            // 图片变换
            updateFullScreenBtnUI();
        }
    };

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 防止调用seekBar.setProgress时回调
            if (!fromUser) {
                return;
            }
            long arg = Long.parseLong(String.valueOf(progress));

            long newPosition = (mDuration * arg) / 1000L;

            final long pos = newPosition;

            String time = generateTime(pos);
            if (mInstantSeeking) {
                mHandler.removeCallbacks(mLastSeekBarRunnable);
                mLastSeekBarRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mController.seekTo(pos);
                    }
                };
                mHandler.postDelayed(mLastSeekBarRunnable, 200);
            }
            if (tvCurrentTime != null) {
                tvCurrentTime.setText(time);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isDragging = true;
            show(3600000);
            mHandler.removeMessages(SET_PROGRESS_UPDATE_UI);
            if (mInstantSeeking) {
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            long all = mDuration * seekBar.getProgress();
            long position = all / 1000L;

            isDragging = false;
            if (!mInstantSeeking) {
                mController.seekTo(position);
            }

            if (position >= mDuration) {
                if (mOnDragToEndListener != null) {
                    mOnDragToEndListener.onDragToEnd(position);
                    mHandler.removeCallbacks(mLastSeekBarRunnable);
                    hide();
                }
            } else {
                show(3000);
                mHandler.removeMessages(SET_PROGRESS_UPDATE_UI);
                mHandler.sendEmptyMessageDelayed(SET_PROGRESS_UPDATE_UI, 1000);
            }

            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        }
    };

    @Override
    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.mController = mediaPlayerControl;
    }

    @Override
    public void show() {
        show(5000);
        if (onShowHidListenner != null) {
            onShowHidListenner.onShow();
        }
    }

    @Override
    public void show(int timeout) {
// 设置控件显示
        // 发送消息给handler开始循环发送消息实现播放进度更新
        if (!isControllerShow) {
            if (fraParentLayout == null) {
                return;
            }
            setVisibility(VISIBLE);
            if (imgVideoPlay != null) {
                imgVideoPlay.requestFocus();
            }
            disableUnsupportedButtons();
            isControllerShow = true;
        }
        updatePlayBtnUI();
        updateFullScreenBtnUI();
        mHandler.sendEmptyMessage(SET_PROGRESS_UPDATE_UI);
        if (timeout != 0) {
            mHandler.removeMessages(SET_CONTROLLER_VISIBLE_GONE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(SET_CONTROLLER_VISIBLE_GONE), timeout);
        }
    }

    @Override
    public void hide() {
        if (isControllerShow) {
            mHandler.removeMessages(SET_PROGRESS_UPDATE_UI);
            setVisibility(View.INVISIBLE);
            isControllerShow = false;
            if (onShowHidListenner != null) {
                onShowHidListenner.onHid();
            }
        }
    }

    @Override
    public boolean isShowing() {
        return isControllerShow;
    }

    /**
     * 设置控制器显示的锚点
     *
     * @param view
     */
    @Override
    public void setAnchorView(View view) {

    }

    /**
     * 设置控制器的父布局
     *
     * @param parentLayout
     */
    public void setControllerParent(FrameLayout parentLayout) {
        setControllerParent(parentLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置控制器的父布局
     *
     * @param parentLayout
     */
    public void setControllerParent(FrameLayout parentLayout, ViewGroup.LayoutParams layoutParams) {
        if (parentLayout == null) {
            return;
        }
        this.fraParentLayout = parentLayout;
        fraParentLayout.addView(this, layoutParams);
    }

    public void setLayoutParamsToControllerParent(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return;
        }
        setLayoutParams(layoutParams);
    }

    public void setOnDragToEndListener(OnDragToEndListener listener) {
        this.mOnDragToEndListener = listener;
    }

    /**
     * 设置是否显示控制器
     * true:显示控制器
     * false：不显示控制器
     */
    public void setControllerSwitch(boolean isShow) {
        this.mControllerSwitch = isShow;
    }

    private int mPlayingCallbackSpace = 1000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long position;
            int code = msg.what;
            switch (code) {
                case SET_CONTROLLER_VISIBLE_GONE:
                    hide();
                    break;
                case SET_PROGRESS_UPDATE_UI:
                    position = setProgressToUI();
                    // 关联了seekBar的与当前播放时间的关键点
                    // 不停的发送handler实现实时查询
                    if (!isDragging && isControllerShow) {
                        msg = obtainMessage(SET_PROGRESS_UPDATE_UI);
                        sendMessageDelayed(msg, 1000 - (position % 1000));
                        updatePlayBtnUI();
                    }
                    break;
                case SET_VIDEO_PLAYING_CALLBACK:
                    if (onVideoPlayingCallBack == null) {
                        return;
                    }
                    if (mController != null) {
                        long currentPos = mController.getCurrentPosition();
                        long mDuration = mController.getDuration();
                        int playingStatus = mController.isPlaying() ? 1 : 0;
                        if (mController.isPlaying()) {
                            onVideoPlayingCallBack.onVideoPlaying(currentPos, mDuration, playingStatus);
                        } else {
                            onVideoPlayingCallBack.onVideoPlaying(currentPos, mDuration, playingStatus);
                        }
                        sendEmptyMessageDelayed(SET_VIDEO_PLAYING_CALLBACK, mPlayingCallbackSpace);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void disableUnsupportedButtons() {
        try {
            if (null != mController && imgVideoPlay != null && !mController.canPause()) {
                imgVideoPlay.setEnabled(false);
            }
        } catch (IncompatibleClassChangeError ex) {
        }
    }

    /**
     * 根据播放状态更新播放按钮的图标
     */
    private void updatePlayBtnUI() {
        if (mController == null) {
            return;
        }
        if (mController.isPlaying()) {
            imgVideoPlay.setImageResource(R.drawable.icon_live_controller_pause);
            if (onShowHidListenner != null) {
                onShowHidListenner.onStart();//播放状态
            }
        } else {
            imgVideoPlay.setImageResource(R.drawable.icon_live_controller_play);
        }

    }

    /**
     * 根据播放状态更新播放按钮的图标
     */
    public void updateFullScreenBtnUI() {
        if (isAdvert) {
            return;
        }
        // 控制方向切换
        int screenOrientation = ((Activity) context).getRequestedOrientation();
        switch (screenOrientation) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                llMoreContainer.setVisibility(GONE);
                fl_voide_download.setVisibility(GONE);
                imgVideoFullScreen.setImageResource(R.drawable.video_list_small_screen);
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                llMoreContainer.setVisibility(VISIBLE);
                if (isNewsDetailV) { //带视频的新闻
                    fl_voide_download.setVisibility(GONE);
                } else {
                    fl_voide_download.setVisibility(VISIBLE);
                }
                imgVideoFullScreen.setImageResource(R.drawable.video_list_full_screen);
                break;
        }
    }

    /**
     * 监听播放按钮的点击事件
     *
     * @param listener
     */
    public void setOnVideoPlayBtnClickListener(OnVideoPlayBtnClickListener listener) {
        if (listener == null) {
            return;
        }
        this.onVideoPlayBtnClickListener = listener;
    }

    public void startVideoPlayingCallback(OnVideoPlayingCallBack listener, int callbackSpace) {
        if (listener == null) {
            return;
        }
        int space = callbackSpace < 300 ? 300 : callbackSpace;
        this.mPlayingCallbackSpace = space;
        this.onVideoPlayingCallBack = listener;
        if (mHandler != null) {
            mHandler.sendEmptyMessage(SET_VIDEO_PLAYING_CALLBACK);
        }
    }

    public void stopVideoPlayingCallback() {
        if (mHandler != null && mVideoPlayClick != null) {
            mHandler.removeMessages(SET_VIDEO_PLAYING_CALLBACK);
        }
        this.mPlayingCallbackSpace = 1000;
        this.onVideoPlayingCallBack = null;
        this.mVideoPlayClick = null;
    }
    public void topContainer() {
        llTopContainer.setEnabled(false);
        llTopContainer.setVisibility(INVISIBLE);
    }

    /**
     * 跳转到指定的位置
     *
     * @param position
     */
    public void controllerSeekTo(long position) {
        if (mController == null) {
            return;
        }
        mController.seekTo(position);
        float percent = (float) ((double) position / (double) mDuration);
        DecimalFormat fnum = new DecimalFormat("##0.0");
        float c_percent = 0;
        c_percent = Float.parseFloat(fnum.format(percent));
        skVideoSeek.setProgress((int) (c_percent * 100));
    }

    public boolean isVideoPlaying() {
        return isVideoPlaying;
    }

    /**
     * 返回 缓冲进度
     *
     * @return
     */
    public int getBufferPro() {
        if (mController == null) {
            return 0;
        }
        return mController.getBufferPercentage();
    }

    /**
     * 设置播放进度给时间显示textView与seekBar
     *
     * @return
     */
    private long setProgressToUI() {
        if (mController == null || isDragging) {
            return 0;
        }
        long position = mController.getCurrentPosition();
        long duration = mController.getDuration();
        if (skVideoSeek != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                skVideoSeek.setProgress((int) pos);
            }
            int percent = mController.getBufferPercentage();
            skVideoSeek.setSecondaryProgress(percent * 10);
        }
        mDuration = duration;
        if (mDuration < position) {
            mDuration = position;
        }

        if (tvAllTime != null) {
            tvAllTime.setText(generateTime(mDuration));
        }
        if (tvCurrentTime != null) {
            tvCurrentTime.setText(generateTime(position));
        }
        return position;
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }

        Log.e("LMediaController", "setTitle: " + title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void goneTitle() {
        tvTitle.setVisibility(INVISIBLE);
    }

    public void setMoreFunctionBtnClickListener(OnClickListener listener) {
        if (listener == null) {
            llMoreContainer.setVisibility(GONE);
            return;
        }
        llMoreContainer.setOnClickListener(listener);
    }

    private static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }
}
