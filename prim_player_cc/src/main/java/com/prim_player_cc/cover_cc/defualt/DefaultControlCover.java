package com.prim_player_cc.cover_cc.defualt;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prim_player_cc.R;
import com.prim_player_cc.cover_cc.BaseCover;
import com.prim_player_cc.cover_cc.listener.OnCoverGestureListener;
import com.prim_player_cc.decoder_cc.event_code.EventCodeKey;
import com.prim_player_cc.decoder_cc.event_code.PlayerEventCode;
import com.prim_player_cc.log.PrimLog;
import com.prim_player_cc.source.PlayerSource;
import com.prim_player_cc.status.Status;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认控制器视图
 * @time 2018/7/27 - 下午3:04
 */
public class DefaultControlCover extends BaseCover implements OnCoverGestureListener, View.OnClickListener {

    ImageView mPlayStateBtn;

    ProgressBar st_video_progress;

    RelativeLayout rl_cover;

    TextView mTitle;

    public DefaultControlCover(Context context) {
        super(context);
        setCoverLevelHeight(5);
        coverVisibility(View.GONE);
        mPlayStateBtn = findViewById(R.id.list_video_btn_play);
        st_video_progress = findViewById(R.id.st_video_progress);
        mTitle = findViewById(R.id.tv_layout_media_controller_title);
        st_video_progress.setMax(1000);
        setProgress(0);
        rl_cover = findViewById(R.id.rl_cover);
        mPlayStateBtn.setOnClickListener(this);
    }

    private void setProgress(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            st_video_progress.setProgress(progress, true);
        } else {
            st_video_progress.setProgress(progress);
        }
    }

    @Override
    protected View createCoverView(Context context) {
        return View.inflate(context, R.layout.default_cover_control_layout, null);
    }

    @Override
    public void onPlayEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case PlayerEventCode.PRIM_PLAYER_EVENT_DATA_SOURCE:
                //拿到播放资源 设置视频标题 下载信息等
                PlayerSource playerSource = bundle.getParcelable(EventCodeKey.PLAYER_DATA_SOURCE);
                updateUI(playerSource);//设置标题等
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_COMPLETION:
                //播放完成隐藏控制器
                coverVisibility(View.GONE);
                setProgress(0);
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_PREPARED:
            case PlayerEventCode.PRIM_PLAYER_EVENT_START:
                //准备完毕或开始播放 显示控制器
                coverVisibility(View.VISIBLE);
                hide();
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_STATUS_CHANGE:
                //更新播放状态
                if (mediaPlayerControl != null) {
                    setStateSelected(mediaPlayerControl.isPlaying());
                }
                break;

            case PlayerEventCode.PRIM_PLAYER_EVENT_TIMER_UPDATE:
                updateTimer(bundle);
                break;
        }
    }

    private void updateTimer(Bundle bundle) {
        if (bundle != null) {
            long current = bundle.getLong(EventCodeKey.PLAYER_CURRENT);
            long duration = bundle.getLong(EventCodeKey.PLAYER_DURATION);
            long buffer = bundle.getLong(EventCodeKey.PLAYER_BUFFER);
            setProgress((int) (1000L * current / duration));
            st_video_progress.setSecondaryProgress((int) (buffer * 10));
        }
    }

    private void updateUI(PlayerSource playerSource) {
        if (playerSource.getTitle() != null) {
            mTitle.setText(playerSource.getTitle());
        }
    }

    private void control() {
        if (rl_cover.getVisibility() == View.GONE) {
            show(3000);
        } else {
            hide();
        }
    }

    @Override
    public void show(int timeout) {
        rl_cover.setVisibility(View.VISIBLE);
        st_video_progress.setVisibility(View.GONE);
        H.sendEmptyMessageDelayed(100, timeout);
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
                case 100:
                    control();
                    break;
            }
        }
    };

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        control();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (mediaPlayerControl != null) {
            if (mediaPlayerControl.isPlaying()) {
                mediaPlayerControl.pause();
            } else {
                mediaPlayerControl.start();
            }
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY) {
        return false;
    }

    private void setStateSelected(boolean isSelected) {
        if (isSelected) {
            mPlayStateBtn.setImageResource(R.drawable.icon_live_controller_pause);
        } else {
            mPlayStateBtn.setImageResource(R.drawable.icon_live_controller_play);
        }
    }

    private static final String TAG = "DefaultControlCover";

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.list_video_btn_play) {
            if (mediaPlayerControl != null) {
                if (mediaPlayerControl.isPlaying()) {
                    mediaPlayerControl.pause();
                } else {
                    mediaPlayerControl.start();
                }
            }
        }
    }
}
