package com.prim_player_cc.cover_cc.defualt;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.prim_player_cc.R;
import com.prim_player_cc.assist.AssistPlayer;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.event.CoverEventCode;
import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.source_cc.PlayerSource;
import com.prim_player_cc.utils.Tools;
import com.prim_player_cc.view.GestureControllerView;

import static com.prim_player_cc.decoder_cc.event_code.PlayerEventCode.PRIM_PLAYER_EVENT_VERTICAL_FULL;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认控制器视图
 * @time 2018/7/27 - 下午3:04
 */
public class DefaultControlCover extends BaseCover implements OnCoverGestureListener, View.OnClickListener {

    private static final String TAG = "DefaultControlCover";

    private ImageView mPlayStateBtn;

    private ProgressBar st_video_progress;

    private RelativeLayout rl_cover;

    private TextView mTitle, mCurrentTime, mAllTime;

    private SeekBar mSeekBar;

    private long mDuration;

    private boolean isMoveSeek = false;

    private ImageView mVideoBtnAll;

    private ImageView mControllerBack;

    private LinearLayout ll_layout_more_container, ll_layout_top_container;

    private GestureControllerView gestureControllerView;

    public DefaultControlCover(Context context, PlayerSource playerSource) {
        super(context);
        initView();
        initListener();
        updateSource(playerSource);//设置标题等
    }

    public DefaultControlCover(Context context) {
        super(context);
        initView();
        initListener();
    }

    private void initView() {
        ll_layout_top_container = findViewById(R.id.ll_layout_media_controller_top_container);
        mPlayStateBtn = findViewById(R.id.list_video_btn_play);
        st_video_progress = findViewById(R.id.st_video_progress);
        mTitle = findViewById(R.id.tv_layout_media_controller_title);
        mCurrentTime = findViewById(R.id.video_tv_current_time);
        mAllTime = findViewById(R.id.video_tv_all_time);
        mSeekBar = findViewById(R.id.video_seek);
        rl_cover = findViewById(R.id.rl_cover);
        mVideoBtnAll = findViewById(R.id.video_btn_all);
        mControllerBack = findViewById(R.id.iv_controller_back);
        ll_layout_more_container = findViewById(R.id.ll_layout_more_container);
        gestureControllerView = findViewById(R.id.gestureControllerView);
        st_video_progress.setMax(1000);
        mSeekBar.setMax(1000);
        mSeekBar.setThumbOffset(1);
        setProgress(0);
        rl_cover.setVisibility(View.GONE);
        st_video_progress.setVisibility(View.VISIBLE);
        gestureFullChange();
    }

    private void gestureFullChange() {
        if (!isFullScreen()) {
            if (gestureControllerView != null) {
                gestureControllerView.setchangePosition(true);
                gestureControllerView.setChangeVolume(false);
                gestureControllerView.setChangeBrightness(false);
            }
        } else {
            if (gestureControllerView != null) {
                gestureControllerView.setchangePosition(true);
                gestureControllerView.setChangeVolume(true);
                gestureControllerView.setChangeBrightness(true);
            }
        }
    }

    private void initListener() {
        mPlayStateBtn.setOnClickListener(this);
        mVideoBtnAll.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(getL());
        mControllerBack.setOnClickListener(this);
        ll_layout_more_container.setOnClickListener(this);
        gestureControllerView.setOnGestureControllerListener(new GestureControllerView.OnGestureControllerListener() {
            @Override
            public void seekTo(int position) {
                mediaPlayerControl.seekTo(position);
            }
        });
    }

    @NonNull
    private SeekBar.OnSeekBarChangeListener getL() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                long l = mDuration * progress / 1000L;
                mCurrentTime.setText(Tools.generateTime(l));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isMoveSeek = true;
                show(3600000);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long l = mDuration * seekBar.getProgress();
                long position = l / 1000L;
                if (mediaPlayerControl != null) {
                    mediaPlayerControl.seekTo(position);
                }

                if (position >= mDuration) {
                    hide();
                } else {
                    show(TIME_OUT);
                }
                isMoveSeek = false;
            }
        };
    }

    private void setProgress(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            st_video_progress.setProgress(progress, true);
            if (!isMoveSeek)
                mSeekBar.setProgress(progress, true);
        } else {
            st_video_progress.setProgress(progress);
            if (!isMoveSeek)
                mSeekBar.setProgress(progress);
        }
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_control_layout, null);
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_MIDDLE, 11};
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
//        PrimLog.e(TAG, "eventCode --> " + eventCode);
        switch (eventCode) {
            case PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE:
//                hide();
//                setProgress(0);
                //拿到播放资源 设置视频标题 下载信息等
                PlayerSource playerSource = bundle.getParcelable(EventCodeKey.PLAYER_DATA_SOURCE);
                updateSource(playerSource);//设置标题等
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
            case PlayerEventCode.PRIM_PLAYER_EVENT_STOP:
                //播放完成隐藏控制器
                setProgress(0);
                coverVisibility(View.GONE);
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARING:
                setEnabled(false);
                if (gestureControllerView != null) {
                    gestureControllerView.setchangePosition(false);
                    gestureControllerView.setChangeBrightness(false);
                    gestureControllerView.setChangeVolume(false);
                }
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
            case PlayerEventCode.PRIM_PLAYER_EVENT_START:
                setEnabled(true);
                //准备完毕或开始播放 显示控制器
                coverVisibility(View.VISIBLE);
                gestureFullChange();
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_STATUS_CHANGE:
                //更新播放状态更新
                if (mediaPlayerControl != null) {
                    setStateSelected(mediaPlayerControl.isPlaying());
                }
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_TIMER_UPDATE:
                updateTimer(bundle);
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_FULL_VERTICAL:
                fullToVertical();
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_VERTICAL_FULL:
                verticalToFull();
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_DESTROY:
                if (gestureControllerView != null) {
                    gestureControllerView.recoverBrightness();
                }
                break;
        }
    }

    private void fullToVertical() {
        if (gestureControllerView != null) {
            gestureControllerView.setchangePosition(true);
            gestureControllerView.setChangeVolume(false);
            gestureControllerView.setChangeBrightness(false);
        }
        ll_layout_top_container.setVisibility(View.GONE);
        mControllerBack.setVisibility(View.GONE);
        mVideoBtnAll.setImageResource(R.mipmap.icon_new_video_list_full);
        mControllerBack.setEnabled(false);
    }

    private void verticalToFull() {
        if (gestureControllerView != null) {
            gestureControllerView.setchangePosition(true);
            gestureControllerView.setChangeVolume(true);
            gestureControllerView.setChangeBrightness(true);
        }
        ll_layout_top_container.setVisibility(View.VISIBLE);
        mControllerBack.setVisibility(View.VISIBLE);
        mControllerBack.setEnabled(true);
        mVideoBtnAll.setImageResource(R.mipmap.icon_new_video_list_v);
    }

    private void updateTimer(Bundle bundle) {
        if (bundle != null) {
            long current = bundle.getLong(EventCodeKey.PLAYER_CURRENT);
            long duration = bundle.getLong(EventCodeKey.PLAYER_DURATION);
            long buffer = bundle.getLong(EventCodeKey.PLAYER_BUFFER);
            setProgress((int) (1000L * current / duration));
            mDuration = duration;
            mAllTime.setText(Tools.generateTime(duration));
            mCurrentTime.setText(Tools.generateTime(current));
            st_video_progress.setSecondaryProgress((int) (buffer * 10));
            mSeekBar.setSecondaryProgress((int) (buffer * 10));
            //更新播放状态更新
            if (mediaPlayerControl != null) {
                setStateSelected(mediaPlayerControl.isPlaying());
            }
        }
    }

    private PlayerSource source;

    private void updateSource(PlayerSource playerSource) {
        if (playerSource == null) return;
        this.source = playerSource;
        //更新标题
        if (playerSource.getTitle() != null) {
            mTitle.setText(playerSource.getTitle());
        }
        //更新下载信息
    }

    private void control() {
        if (rl_cover.getVisibility() == View.GONE) {
            show(TIME_OUT);
        } else {
            hide();
        }
    }

    @Override
    public void show(int timeout) {
        rl_cover.setVisibility(View.VISIBLE);
        st_video_progress.setVisibility(View.GONE);
        H.removeMessages(WHAT_CONTROL_VISIBLE_GONE);
        H.sendEmptyMessageDelayed(WHAT_CONTROL_VISIBLE_GONE, timeout);
        if (mediaPlayerControl != null) {
            setStateSelected(mediaPlayerControl.isPlaying());
        }
    }

    @Override
    public void hide() {
        rl_cover.setVisibility(View.GONE);
        st_video_progress.setVisibility(View.VISIBLE);
        H.removeCallbacksAndMessages(null);
    }

    private Handler H = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_CONTROL_VISIBLE_GONE:
                    control();
                    break;
            }
        }
    };

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        control();
        return true; //返回true
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        pauseOrResume();
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        if (gestureControllerView != null) {
            gestureControllerView.setCurrentPosition(mediaPlayerControl.getCurrentPosition());
            gestureControllerView.setDuration(mediaPlayerControl.getDuration());
            gestureControllerView.down();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY) {
        if (gestureControllerView != null) {
            gestureControllerView.onScroll(e1, e2, dX, dY);
        }
        return false;
    }

    @Override
    public boolean onTouchCancle() {
        if (gestureControllerView != null) {
            gestureControllerView.onTouchCancle();
        }
        return false;
    }

    private void setStateSelected(boolean isSelected) {
        if (isSelected) {
            mPlayStateBtn.setImageResource(R.mipmap.res_icon_402);
        } else {
            mPlayStateBtn.setImageResource(R.mipmap.res_icon_401);
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.list_video_btn_play) {
            pauseOrResume();
        } else if (i == R.id.video_btn_all) {
            updateFull();
        } else if (i == R.id.iv_controller_back) {
            updateFull();
        } else if (i == R.id.ll_layout_more_container) {
            hide();
            nativeCoverEvent(CoverEventCode.COVER_EVENT_VIDEO_MORE_INFO, null);
        }
    }

    private void updateFull() {
        if (isFullScreen()) {
            coverRequestVerticalScreen();
        } else {
            coverRequestFullScreen();
        }
    }


}
